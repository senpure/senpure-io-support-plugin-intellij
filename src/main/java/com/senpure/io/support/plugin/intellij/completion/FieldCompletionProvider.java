package com.senpure.io.support.plugin.intellij.completion;

import com.intellij.codeInsight.actions.ReformatCodeProcessor;
import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.lang.ASTNode;
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
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * FieldCompletionProvider
 *
 * @author senpure
 * @time 2019-06-12 21:02:04
 */
public class FieldCompletionProvider extends CompletionProvider {

    public static void reg(CompletionContributor contributor) {

        contributor.extend(CompletionType.BASIC, PlatformPatterns.psiElement(IoTypes.T_FIELD_TYPE_QUOTE), new FieldCompletionProvider());
        contributor.extend(CompletionType.BASIC, PlatformPatterns.psiElement(IoTypes.T_FIELD_NAME), new FieldNameCompletionProvider());

    }

    String[] base = new String[]{"int", "long", "sint", "slong", "sfixed32", "sfixed64", "float", "double", "boolean", "String"};

    private boolean isBase(String text) {
        for (String s : base) {
            if (s.equals(text)) {
                return true;
            }
        }
        return false;
    }

    private List<Bean> getBeans() {
        List<Bean> beans = new ArrayList<>();
        for (IoProtocolReader value : IoReader.getInstance().getIoProtocolReaderMap().values()) {

            beans.addAll(value.getBeans());
            beans.addAll(value.getEnums());
        }

        return beans;
    }

    @Override
    protected void addCompletions(@NotNull CompletionParameters parameters, @NotNull ProcessingContext context, @NotNull CompletionResultSet result) {


        IElementType elementType = IoUtil.preEffectiveElementType(parameters.getPosition().getNode());
        if (elementType != null) {
            if (elementType.equals(IoTypes.T_LEFT_BRACE)
                    || elementType.equals(IoTypes.T_SEMICOLON)
                    || elementType.equals(IoTypes.T_FIELD_COMMENT)) {
                for (String s : base) {
                    result.addElement(LookupElementBuilder.create(s));
                }
                List<Bean> beans = getBeans();
//                System.out.println("start");
//                for (Bean bean : beans) {
//                    if (bean.getName().startsWith("Same")) {
//                        System.out.println(bean.getName()+" "+bean.getNamespace());
//                    }
//                }
//
//                System.out.println("end");
                for (Bean bean : beans) {

                    result.addElement(LookupElementBuilder.create(bean,bean.getName())
                            .withTailText(" (" + bean.getNamespace() + ")", true)

                            .withIcon(IoIcons.FILE)
                    );
                }

            }
        }

    }

    static class FieldNameCompletionProvider extends CompletionProvider {


        @Override
        protected void addCompletions(@NotNull CompletionParameters parameters, @NotNull ProcessingContext context, @NotNull CompletionResultSet result) {
            String text = parameters.getPosition().getText().replace("IntellijIdeaRulezzz", "");
            ASTNode pre = IoUtil.preEffectiveNode(parameters.getPosition().getNode());
            boolean base = true;
            if (pre != null) {

                boolean list = false;
                if (pre.getElementType().equals(IoTypes.T_RIGHT_BRACKET)) {
                    list = true;
                    pre = IoUtil.preEffectiveNode(pre, 2);
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
                        for (String name : names) {
                            result.addElement(LookupElementBuilder.create(name));
                        }
                        //   IElementType nextType = IoUtil.nexEffectiveElementType(parameters.getPosition().getNode());

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
            if (StringUtil.isUpperLetter(name.charAt(1))) {
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
}
