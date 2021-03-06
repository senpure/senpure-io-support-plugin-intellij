package com.senpure.io.support.plugin.intellij.completion;

import com.intellij.codeInsight.actions.ReformatCodeProcessor;
import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.lang.ASTNode;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiErrorElement;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import com.intellij.util.ProcessingContext;
import com.senpure.base.util.StringUtil;
import com.senpure.io.generator.model.Bean;
import com.senpure.io.generator.model.Message;
import com.senpure.io.generator.reader.IoProtocolReader;
import com.senpure.io.generator.reader.IoReader;
import com.senpure.io.support.plugin.intellij.psi.IoTypes;
import com.senpure.io.support.plugin.intellij.util.IoUtil;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * EveryHeadCompletion
 *
 * @author senpure
 * @time 2019-05-31 17:13:38
 */
public class MessageCompletionProvider extends CompletionProvider {

    private Logger logger = LoggerFactory.getLogger(getClass());

    public static void reg(CompletionContributor contributor) {

        contributor.extend(CompletionType.BASIC, PlatformPatterns.psiElement(IoTypes.T_MESSAGE_NAME), new MessageCompletionProvider());

    }

    @Override
    protected void addCompletions(@NotNull CompletionParameters parameters, @NotNull ProcessingContext context, @NotNull CompletionResultSet result) {
        boolean messageType = false;
        boolean messageName = false;
        boolean messageId = false;
        boolean brace = false;
        ASTNode preNode;
        PsiElement parent = parameters.getPosition().getParent();
        if (parent instanceof PsiErrorElement) {
            IElementType preType;
            preNode = IoUtil.getPreEffectiveSibling(parent.getNode());
            if (preNode != null) {
                preType = preNode.getElementType();
                //不判断后面是否有该元素了
//                ASTNode nextNode = IoUtil.getNexEffectiveSibling(parent.getNode());
//                if (nextNode != null && GeneratedParserUtilBase.DUMMY_BLOCK.equals(nextNode.getElementType())) {
//                    nextNode = IoUtil.childEffectiveNode(nextNode);
//
//                }
                if (preType.equals(IoTypes.T_MESSAGE_HEAD)
//                        && (nextNode == null || (!nextNode.getElementType()
//                        .equals(IoTypes.T_MESSAGE_TYPE_CS) && !nextNode.getElementType()
//                        .equals(IoTypes.T_MESSAGE_TYPE_SC)))
                ) {
                    messageType = true;
                } else if (preType.equals(IoTypes.T_MESSAGE_NAME)
                        && parent.getNode().getTreePrev().getElementType().equals(TokenType.WHITE_SPACE)
                    // && (nextNode == null || nextNode.getElementType().equals(IoTypes.T_MESSAGE_ID))
                ) {
                    messageId = true;
                } else if (preType.equals(IoTypes.T_MESSAGE_ID)) {
                    brace = true;
                }
            }
        } else {
            IElementType preType;
            preNode = IoUtil.getPreEffectiveSibling(parameters.getPosition().getNode());
            if (preNode != null) {
                preType = preNode.getElementType();
                // logger.debug("{} {}",preType);
                if (preType.equals(IoTypes.T_MESSAGE_TYPE_SC)
                        || preType.equals(IoTypes.T_MESSAGE_TYPE_CS)) {
                    messageName = true;
                }
            }
        }
        if (messageType) {
            messageType(result);
        } else if (messageName) {
            messageName(parameters, result);
        } else if (messageId) {
            messageId(parameters, result);
        } else if (brace) {
            brace(parameters, result);
        }
        // return;

        // result.addElement(LookupElementBuilder.create("CS"));
        // result.addElement(LookupElementBuilder.create("SC"));
    }

    public void messageType(@NotNull CompletionResultSet result) {
        result.addElement(LookupElementBuilder.create("CS"));
        result.addElement(LookupElementBuilder.create("SC"));
    }

    public void messageName(@NotNull CompletionParameters parameters, @NotNull CompletionResultSet result) {
        String text = parameters.getPosition().getText().replace("IntellijIdeaRulezzz", "");
        boolean extra = false;
        if (text.length() > 0) {
            extra = true;
            text = StringUtil.toUpperFirstLetter(text);
        }
        IoProtocolReader reader = IoReader.getInstance(parameters.getPosition().getProject().getBasePath()).
                getIoProtocolReaderMap().get(parameters.
                getOriginalFile()
                .getVirtualFile()
                .getPath());
        if (reader == null) {
            if (extra) {
                result.addElement(LookupElementBuilder.create(text));
            }
            return;
        }

        List<Message> messages = reader.getMessages();
        List<Message> singles = new ArrayList<>();
        for (int i = 0; i < messages.size(); i++) {
            Message message = messages.get(i);
            boolean single = true;
            for (int j = 0; j < messages.size(); j++) {
                if (i == j) {
                    continue;
                }
                Message temp = messages.get(j);
                if (Objects.equals(message.getName(), temp.getName())) {
                    single = false;
                    break;
                }
            }
            logger.debug("{} single {}", message.getName(), single);
            if (single) {
                singles.add(message);
            }
        }
        PsiElement parent = parameters.getPosition();
        ASTNode typeNode = IoUtil.getPreEffectiveSibling(parent.getNode());
        String type = "CS";
        if (typeNode != null) {
            type = typeNode.getText();
        }
        boolean add = true;
        for (Message single : singles) {
            if (!single.getType().equalsIgnoreCase(type)) {
                if (add && extra) {
                    if (single.getName().startsWith(text)) {
                        add = false;
                    }
                }
                result.addElement(LookupElementBuilder.create(single.getName()));
            }
        }
//        if (singles.size() == 0) {
//        }

        for (Bean bean : reader.getBeans()) {
            boolean not = true;
            for (Message message : messages) {
                if (Objects.equals(bean.getName(), message.getName())) {
                    not = false;
                    break;
                }
            }
            if (not) {
                if (add && extra) {
                    if (bean.getName().startsWith(text)) {
                        add = false;
                    }
                }
                result.addElement(LookupElementBuilder.create(bean.getName()));
            }
        }
        if (add && extra) {
            result.addElement(LookupElementBuilder.create(text));
        }

    }

    public void messageId(@NotNull CompletionParameters parameters, @NotNull CompletionResultSet result) {
        PsiElement position = parameters.getPosition();
        String text = position.getText().replace("IntellijIdeaRulezzz", "");
        int start = position.getTextOffset() + text.length();
        String nextText = IoUtil.getNextText(start, parameters.getEditor().getDocument());
        PsiElement parent = parameters.getPosition().getParent();
        ASTNode preNode = IoUtil.getPreEffectiveSibling(parent.getNode());
        ASTNode typeNode = IoUtil.getPreEffectiveSibling(preNode);
        String type = "CS";
        if (typeNode != null) {
            type = typeNode.getText();
        }

        Integer messageId = IoUtil.getAutoMessageId(
                parameters.getPosition().getProject().getBasePath(),
                IoUtil.getFileNamespace(parameters.
                        getOriginalFile()
                        .getVirtualFile()
                        .getPath()), type, preNode.getText());
        result.addElement(LookupElementBuilder.create(messageId)
                .withInsertHandler((context, item) -> {

                    boolean insert = false;
                    if (nextText.length() == 0 || nextText.equals(" ") || nextText.equals("\n")) {
                        insert = true;
                    }
                    if (insert) {
                        int offset = parameters.getPosition().getTextOffset() + (messageId+"").length();
                        parameters.getEditor().getDocument().insertString(offset, "{\n}");
                        parameters.getEditor().getCaretModel().moveToOffset(offset + 1);
                        parameters.getEditor().getSelectionModel().selectLineAtCaret();
                        ReformatCodeProcessor processor = new ReformatCodeProcessor(parameters.getOriginalFile(), parameters.getEditor().getSelectionModel());
                        processor.runWithoutProgress();
                        parameters.getEditor().getSelectionModel().removeSelection();
                    }
                })
        );

//        result.addElement(LookupElementBuilder.create(messageId + " {\n}")
//                .withBoldness(true)
//                .withTailText(" (补全括号) ", true)
//                .withInsertHandler((context, item) -> {
//                    parameters.getEditor().getCaretModel().moveToOffset((messageId + "").length() + parameters.getOffset() + 2);
//                    parameters.getEditor().getSelectionModel().selectLineAtCaret();
//                    ReformatCodeProcessor processor = new ReformatCodeProcessor(parameters.getOriginalFile(), parameters.getEditor().getSelectionModel());
//                    processor.runWithoutProgress();
//                    parameters.getEditor().getSelectionModel().removeSelection();
//                })
//        );

    }

    public void brace(CompletionParameters parameters, @NotNull CompletionResultSet result) {
        LookupElementBuilder builder = LookupElementBuilder.create(" {\n\n}")
                .withBoldness(true)
                .withTailText(" (补全括号) ", true)
                .withInsertHandler((context1, item) -> {
                    parameters.getEditor().getCaretModel().moveToOffset(parameters.getOffset() + 2);
                    parameters.getEditor().getSelectionModel().selectLineAtCaret();
                    ReformatCodeProcessor processor = new ReformatCodeProcessor(parameters.getOriginalFile(), parameters.getEditor().getSelectionModel());
                    processor.runWithoutProgress();
                    parameters.getEditor().getSelectionModel().removeSelection();

                });

        result.addElement(builder);
    }
}
