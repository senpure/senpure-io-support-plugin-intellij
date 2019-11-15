package com.senpure.io.support.plugin.intellij.completion;

import com.intellij.codeInsight.actions.ReformatCodeProcessor;
import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.lang.ASTNode;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.tree.IElementType;
import com.intellij.util.ProcessingContext;
import com.senpure.base.util.Inflector;
import com.senpure.base.util.StringUtil;
import com.senpure.io.generator.model.Bean;
import com.senpure.io.generator.reader.IoProtocolReader;
import com.senpure.io.generator.reader.IoReader;
import com.senpure.io.support.plugin.intellij.IoIcons;
import com.senpure.io.support.plugin.intellij.psi.IoTypes;
import com.senpure.io.support.plugin.intellij.util.IoUtil;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * FieldCompletionProvider
 *
 * @author senpure
 * @time 2019-06-12 21:02:04
 */
public class FieldCompletionProvider extends CompletionProvider {

    private static Logger logger = LoggerFactory.getLogger(FieldCompletionProvider.class);

    public static void reg(CompletionContributor contributor) {

        contributor.extend(CompletionType.BASIC, PlatformPatterns.psiElement(IoTypes.T_FIELD_TYPE_QUOTE), new FieldCompletionProvider());
        contributor.extend(CompletionType.BASIC, PlatformPatterns.psiElement(IoTypes.T_FIELD_NAME), new FieldNameCompletionProvider());

    }

    private static String[] base = new String[]{"int", "long", "sint", "slong", "fixed32", "fixed64", "float", "double", "boolean", "String"};

    private static boolean isBase(String text) {
        for (String s : base) {
            if (s.equals(text)) {
                return true;
            }
        }
        return false;
    }

    private List<Bean> getBeans(Project project) {
        List<Bean> beans = new ArrayList<>();
        for (IoProtocolReader value : IoReader.getInstance(project.getBasePath()).getIoProtocolReaderMap().values()) {

            beans.addAll(value.getBeans());
            beans.addAll(value.getEnums());
        }

        return beans;
    }

    @Override
    protected void addCompletions(@NotNull CompletionParameters parameters, @NotNull ProcessingContext context, @NotNull CompletionResultSet result) {

        IElementType elementType = IoUtil.getPreEffectiveSiblingElementType(parameters.getPosition().getNode());
        if (elementType != null) {
            if (elementType.equals(IoTypes.T_LEFT_BRACE)
                    || elementType.equals(IoTypes.T_SEMICOLON)
                    || elementType.equals(IoTypes.T_FIELD_COMMENT)) {
                for (String s : base) {
                    result.addElement(LookupElementBuilder.create(s));
                }
                List<Bean> beans = getBeans(parameters.getPosition().getProject());

                for (Bean bean : beans) {
                    result.addElement(LookupElementBuilder.create(bean, bean.getName())
                            .withTailText(" (" + bean.getNamespace() + ")", true)
                            .withIcon(IoIcons.FILE)
                    );
                }

            }
        }

    }

    static class FieldNameCompletionProvider extends CompletionProvider {

        protected void addCompletions(@NotNull CompletionParameters parameters, @NotNull ProcessingContext context, @NotNull CompletionResultSet result) {
            String text = parameters.getPosition().getText().replace("IntellijIdeaRulezzz", "");
            int start = parameters.getPosition().getTextOffset() + text.length();
            int end = parameters.getEditor().getDocument().getTextLength();
            String nextText = "";
            while (start < end) {
                nextText = parameters.getEditor().getDocument().getText(new TextRange(start, ++start));
                if (!nextText.equals(" ")) {
                    break;
                }
            }
            ASTNode pre;
            boolean base;
            boolean list = false;
            pre = IoUtil.getPreEffectiveSibling(parameters.getPosition().getNode());
            if (pre != null) {
                logger.debug("pre {}  preText [{}] text [{}]", pre, pre.getText(), text);

                if (pre.getElementType().equals(IoTypes.T_RIGHT_BRACKET)) {
                    list = true;
                    pre = IoUtil.getPreEffectiveSibling(pre, 2);
                    logger.debug(" list  is true pre {}", pre);
                }


            } else {
                logger.debug("pre  is null  text [{}]", text);
                pre = IoUtil.getPreEffectiveSibling(parameters.getPosition().getParent().getNode());
                logger.debug("pre {}  preText [{}] text [{}]", pre, pre.getText(), text);
                if (pre.getElementType().equals(IoTypes.T_FIELD_NAME)) {
                    return;
                }
                if (pre.getElementType().equals(IoTypes.FIELD_LIST)) {
                    list = true;
                    pre = IoUtil.getPreEffectiveSibling(pre, 1);
                    logger.debug(" list  is true pre {}", pre);
                }
            }
            List<String> names = new ArrayList<>(16);
            //list后缀补全
            List<String> namesList = new ArrayList<>(16);
            base = isBase(pre.getText());
            if (text.length() > 0) {
                if (base) {
                    names.add(text);
                    if (!list) {
                        addListName(pre.getText(), text, names, namesList);
                    }
                } else {
                    getName(pre.getText(), text, list, names, namesList);
                    if (!list) {
                        addListName(pre.getText(), text, names, namesList);
                    }
                }

            } else {
                if (base) {
                    names.add(pre.getText());
                    if (!list) {
                        addListName(pre.getText(), pre.getText(), names, namesList);
                    }
                } else {
                    getName(pre.getText(), text, list, names, namesList);
                    if (!list) {
                        addListName(pre.getText(), text, names, namesList);
                    }
                }
            }
            int maxLen = 0;
            for (String name : names) {
                int len = name.length();
                maxLen = len > maxLen ? len : maxLen;
            }  for (String name : namesList) {
                int len = name.length();
                maxLen = len > maxLen ? len : maxLen;
            }
            maxLen += 9;
            completionList(nextText, names, parameters, result);
            completionList(nextText, namesList, parameters, result);
            completionList(names,maxLen, parameters, result);
            completionList(namesList,maxLen, parameters, result);
        }

        private void addListName(String type, String text, List<String> names, List<String> namesList) {
            String name = Inflector.getInstance().pluralize(type);
            name = nameRule(name);
            names.add("[] " + name);
            String name2 = nameRule(type);
            namesList.add("[] " + name2 + "List");
            if (text.length() > 0) {
                String t = text.toLowerCase();
                String t2 = name2.toLowerCase();
                if (!t2.startsWith(t) && !t.equals(t2)&&!t.startsWith(t2)) {
                    names.add("[] " + text + name);
                    namesList.add("[] " + text + name2 + "List");
                }
            }
        }

        private void getName(String quote, String text, boolean list, List<String> names, List<String> namesList) {
            if (list) {
                String name = Inflector.getInstance().pluralize(quote);
                name = nameRule(name);
                names.add(name);
                String name2 = nameRule(quote);
                namesList.add(name2 + "List");
                if (text.length() > 0) {
                    String t = text.toLowerCase();
                    String t2 = name2.toLowerCase();
                    if (!t2.startsWith(t) && !t.equals(t2)&&!t.startsWith(t2)) {
                        names.add(text + name);
                        namesList.add(text + name2 + "List");
                    }
                }

            } else {
                String name = nameRule(quote);
                names.add(nameRule(name));
                if (text.length() > 0) {
                    String t = text.toLowerCase();
                    String t2 = quote.toLowerCase();
                    if (!t2.startsWith(t) && !t.equals(t2)&&!t.startsWith(t2)) {
                        names.add(text);
                        names.add(text + quote);
                    }

                }
            }

        }

        private void completionList(String finalNextText, List<String> names, CompletionParameters parameters, CompletionResultSet result) {
            for (int i = 0; i < names.size(); i++) {
                String name = names.get(i);
                result.addElement(LookupElementBuilder.create(name)
                        .withInsertHandler((context1, item) -> insertSemicolonHandler(finalNextText, parameters, name))
                );
            }
        }

        private void insertSemicolonHandler(String finalNextText, CompletionParameters parameters, String text) {
            boolean insertSemicolon = false;
            if (finalNextText.length() == 0 || finalNextText.equals(" ") || finalNextText.equals("\n")) {
                insertSemicolon = true;
            }
            if (insertSemicolon) {
                int offset = parameters.getPosition().getTextOffset() + text.length();
                parameters.getEditor().getDocument().insertString(offset, ";");
                parameters.getEditor().getCaretModel().moveToOffset(offset + 1);
            }
            parameters.getEditor().getSelectionModel().selectLineAtCaret();
            ReformatCodeProcessor processor = new ReformatCodeProcessor(parameters.getOriginalFile(), parameters.getEditor().getSelectionModel());
            processor.runWithoutProgress();
            parameters.getEditor().getSelectionModel().removeSelection();

        }

        private void completionList(List<String> names,int maxLen, CompletionParameters parameters, CompletionResultSet result) {

            for (String name : names) {
                String finalName = name + ";//";
                result.addElement(LookupElementBuilder.create(finalName)
                        .withBoldness(true)
                        .withTailText(StringUtils.leftPad(" (快捷添加注释)", maxLen - name.length()), true)
                        .withInsertHandler((context1, item) -> insertCommentHandler(parameters, finalName))
                );
            }
        }

        private void insertCommentHandler(CompletionParameters parameters, String text) {
            parameters.getEditor().getCaretModel().moveToOffset(parameters.getPosition().getTextOffset() + text.length());
            parameters.getEditor().getSelectionModel().selectLineAtCaret();
            ReformatCodeProcessor processor = new ReformatCodeProcessor(parameters.getOriginalFile(), parameters.getEditor().getSelectionModel());
            processor.runWithoutProgress();
            parameters.getEditor().getSelectionModel().removeSelection();

        }

        protected void addCompletions2(@NotNull CompletionParameters parameters, @NotNull ProcessingContext context, @NotNull CompletionResultSet result) {
            String text = parameters.getPosition().getText().replace("IntellijIdeaRulezzz", "");
            ASTNode pre = IoUtil.getPreEffectiveSibling(parameters.getPosition().getNode());

            boolean base = true;
            if (pre != null) {
                boolean list = false;
                if (pre.getElementType().equals(IoTypes.T_RIGHT_BRACKET)) {
                    list = true;
                    pre = IoUtil.getPreEffectiveSibling(pre, 2);
                }
                if (pre != null) {
                    if (pre.getElementType().equals(IoTypes.T_FIELD_TYPE_QUOTE)) {
                        base = false;
                        List<String> names = new ArrayList<>(16);

                        if (list) {
                            String name = Inflector.getInstance().pluralize(pre.getText());
                            name = nameRule(name);
                            names.add(name);
                            names.add(nameRule(pre.getText() + "List"));
                            if (text.length() > 0) {
                                String t = text.toLowerCase();
                                String t2 = pre.getText().toLowerCase();
                                if (!t2.startsWith(t)) {
                                    names.add(text + pre.getText());
                                    names.add(text + pre.getText() + "List");
                                }
                            }
                            // names.add(nameRule(pre.getText() + "Array"));
                        } else {
                            String name = nameRule(pre.getText());
                            names.add(nameRule(name));
                        }

                        //   IElementType nextType = IoUtil.nexEffectiveElementType(parameters.getPosition().getNode());
                        for (String name : names) {
                            result.addElement(LookupElementBuilder.create(name)

                            );
                        }

                        for (String name : names) {
                            result.addElement(LookupElementBuilder.create(name + ";")
                                    .withBoldness(true)
                                    .withTailText(" (补全分号) ", true)
                                    .withInsertHandler((context1, item) -> {
                                        parameters.getEditor().getCaretModel().moveToOffset(parameters.getOffset() + 1 + name.length() - text.length());
                                        parameters.getEditor().getSelectionModel().selectLineAtCaret();
                                        ReformatCodeProcessor processor = new ReformatCodeProcessor(parameters.getOriginalFile(), parameters.getEditor().getSelectionModel());
                                        processor.runWithoutProgress();
                                        parameters.getEditor().getSelectionModel().removeSelection();
                                    })
                            );

                            result.addElement(LookupElementBuilder.create(name + ";//")
                                    .withBoldness(true)
                                    .withTailText(" (补全分号,添加注释) ", true)
                                    .withInsertHandler((context1, item) -> {
                                        parameters.getEditor().getCaretModel().moveToOffset(parameters.getOffset() + 3 + name.length() - text.length());
                                        parameters.getEditor().getSelectionModel().selectLineAtCaret();
                                        ReformatCodeProcessor processor = new ReformatCodeProcessor(parameters.getOriginalFile(), parameters.getEditor().getSelectionModel());
                                        processor.runWithoutProgress();
                                        parameters.getEditor().getSelectionModel().removeSelection();
                                    })
                            );
                        }

                    }
                }

            }
            if (base || text.length() == 0) {
                LookupElementBuilder builder = LookupElementBuilder.create(text + ";")
                        .withBoldness(true)
                        .withTailText(" (补全分号) ", true)
                        .withInsertHandler((context1, item) -> {
                            parameters.getEditor().getCaretModel().moveToOffset(parameters.getOffset() + 1);
                            parameters.getEditor().getSelectionModel().selectLineAtCaret();
                            ReformatCodeProcessor processor = new ReformatCodeProcessor(parameters.getOriginalFile(), parameters.getEditor().getSelectionModel());
                            processor.runWithoutProgress();
                            parameters.getEditor().getSelectionModel().removeSelection();

                        });

                result.addElement(builder);
                result.addElement(LookupElementBuilder.create(text + ";//")
                        .withBoldness(true)
                        .withTailText(" (补全分号,添加注释) ", true)
                        .withInsertHandler((context1, item) -> {
                            parameters.getEditor().getCaretModel().moveToOffset(parameters.getOffset() + 3);
                            parameters.getEditor().getSelectionModel().selectLineAtCaret();
                            ReformatCodeProcessor processor = new ReformatCodeProcessor(parameters.getOriginalFile(), parameters.getEditor().getSelectionModel());
                            processor.runWithoutProgress();
                            parameters.getEditor().getSelectionModel().removeSelection();
                        })
                );
            }


        }

        public static String nameRule(String name) {
            if (name.length() > 2 && StringUtil.isUpperLetter(name.charAt(1))) {
                int len = name.length() - 1;
                int index = 0;
                for (int i = 1; i < len; i++) {
                    if (!StringUtil.isUpperLetter(name.charAt(i + 1))) {
                        index = i - 1;
                        break;
                    }
                }
                if (index > 0) {
                    for (int i = 0; i <= index; i++) {
                        name = StringUtil.toLowerLetter(name, i);
                    }
                }
                return name;

            }
            return StringUtil.toLowerLetter(name, 0);
        }
    }

    public static void main(String[] args) {
        String str = "abc";
        System.out.println(StringUtils.leftPad(str, 9));
    }
}
