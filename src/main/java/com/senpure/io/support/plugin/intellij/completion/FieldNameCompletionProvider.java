package com.senpure.io.support.plugin.intellij.completion;

import com.intellij.codeInsight.actions.ReformatCodeProcessor;
import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.lang.ASTNode;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiElement;
import com.intellij.util.ProcessingContext;
import com.senpure.base.util.Inflector;
import com.senpure.base.util.StringUtil;
import com.senpure.io.generator.util.ProtocolUtil;
import com.senpure.io.support.plugin.intellij.psi.IoTypes;
import com.senpure.io.support.plugin.intellij.util.IoUtil;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * FieldNameCompletionProvider
 *
 * @author senpure
 * @time 2019-11-16 13:38:48
 */
public class FieldNameCompletionProvider extends CompletionProvider {
    private Logger logger = LoggerFactory.getLogger(getClass());

    public static void reg(CompletionContributor contributor) {

        contributor.extend(CompletionType.BASIC, PlatformPatterns.psiElement(IoTypes.T_FIELD_NAME), new FieldNameCompletionProvider());

    }

    @Override
    protected void addCompletions(@NotNull CompletionParameters parameters, @NotNull ProcessingContext context, @NotNull CompletionResultSet result) {

        PsiElement position = parameters.getPosition();
        String text = position.getText().replace("IntellijIdeaRulezzz", "");
        int start = position.getTextOffset() + text.length();
        String nextText = IoUtil.getNextText(start, parameters.getEditor().getDocument());
        ASTNode pre;
        pre = IoUtil.getPreEffectiveSibling(parameters.getPosition().getNode());
        boolean list = false;
        if (pre != null) {
            logger.debug("pre {}  preText [{}] text [{}]", pre, pre.getText(), text);
            if (pre.getElementType().equals(IoTypes.T_RIGHT_BRACKET)) {
                list = true;
                pre = IoUtil.getPreEffectiveSibling(pre, 2);
                logger.debug(" list  is true pre {}", pre);
            }
            boolean base = ProtocolUtil.isBaseField(pre.getText());
            String quote = pre.getText();
            List<String> names = new ArrayList<>();
            if (text.length() > 0) {
                //加入原始字符串
                names.add(text);
                if (base) {
                    names.add(quote + "Value");
                } else {
                    getName(quote, text, list, names);
                }
            } else {
                if (base) {
                    names.add(quote + "Value");
                } else {
                    getName(quote, text, list, names);
                }
            }
            int nextIndex = 0;
            String identity = IoUtil.findBeanIdentity(pre);
            String filePath = null;
            logger.debug("identity {}", identity);
            if (identity != null) {
                filePath = IoUtil.getFilePath(parameters.getPosition());
                nextIndex = IoUtil.getBeanNextIndex(filePath, identity);
                logger.debug("nextIndex {}", nextIndex);
            }
            for (String name : names) {
                result.addElement(LookupElementBuilder.create(name));

            }

            int finalNextIndex = nextIndex;
            String finalFilePath = filePath;
            if (nextIndex > 0) {
                int maxLen = 0;
                List<String> autoNames = new ArrayList<>();
                for (String name : names) {
                    autoNames.add(name + " = " + nextIndex);

                }
                for (String name : autoNames) {
                    int len = name.length();
                    maxLen = Math.max(len, maxLen);
                }
                maxLen += 11;
                for (String name : autoNames) {
                    result.addElement(LookupElementBuilder.create(name)
                            .withTailText(StringUtils.leftPad(" (autoCode)", maxLen - name.length()), true)
                            .withInsertHandler((context1, item) -> insertSemicolonHandler(parameters, name, nextText,
                                    finalFilePath, identity, finalNextIndex))
                    );
                }
            }

        }
    }

    private void insertSemicolonHandler(CompletionParameters parameters, String text,
                                        String nextText, String filePath, String identity, int nextIndex) {

        boolean insertSemicolon = false;
        IoUtil.markLastIndex(filePath, identity, nextIndex);
        if (nextText.length() == 0 || nextText.equals(" ") || nextText.equals("\n")) {
            insertSemicolon = true;
        }
        if (insertSemicolon) {
            int offset = parameters.getPosition().getTextOffset() + text.length();
            String insertStr = ";//";
            parameters.getEditor().getDocument().insertString(offset, insertStr);
            parameters.getEditor().getCaretModel().moveToOffset(offset + insertStr.length());
        }
        parameters.getEditor().getSelectionModel().selectLineAtCaret();
        ReformatCodeProcessor processor = new ReformatCodeProcessor(parameters.getOriginalFile(), parameters.getEditor().getSelectionModel());
        processor.runWithoutProgress();
        parameters.getEditor().getSelectionModel().removeSelection();

    }

    private void getName(String quote, String text, boolean list, List<String> names) {
        if (list) {
            String name = Inflector.getInstance().pluralize(quote);
            name = nameRule(name);
            names.add(name);
            //List 后缀
            String nameQuote = nameRule(quote);
            String nameList = nameQuote + "List";
            names.add(nameList);
            if (text.length() > 0) {
                //增强 abcQuote 情况
                String x = text.toLowerCase();
                String y = nameQuote.toLowerCase();
                if (!x.startsWith(y) && !y.startsWith(x)) {
                    names.add(text + quote);
                    names.add(text + quote + "List");
                }
            }
        } else {
            String name = nameRule(quote);
            names.add(name);
            if (text.length() > 0) {
                String x = text.toLowerCase();
                String y = quote.toLowerCase();
                if (!x.startsWith(y) && !y.startsWith(x)) {
                    names.add(text + quote);
                }
            }
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

