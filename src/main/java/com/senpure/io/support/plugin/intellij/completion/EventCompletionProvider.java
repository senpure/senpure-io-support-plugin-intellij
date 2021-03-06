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
import com.senpure.io.generator.model.Event;
import com.senpure.io.generator.reader.IoProtocolReader;
import com.senpure.io.generator.reader.IoReader;
import com.senpure.io.support.plugin.intellij.psi.IoTypes;
import com.senpure.io.support.plugin.intellij.util.IoUtil;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;


/**
 * EveryHeadCompletion
 *
 * @author senpure
 * @time 2019-05-31 17:13:38
 */
public class EventCompletionProvider extends CompletionProvider {

    private Logger logger = LoggerFactory.getLogger(getClass());

    public static void reg(CompletionContributor contributor) {

        contributor.extend(CompletionType.BASIC, PlatformPatterns.psiElement(IoTypes.T_EVENT_NAME), new EventCompletionProvider());

    }

    @Override
    protected void addCompletions(@NotNull CompletionParameters parameters, @NotNull ProcessingContext context, @NotNull CompletionResultSet result) {

        boolean eventName = false;
        boolean eventId = false;
        boolean brace = false;
        ASTNode preNode;
        PsiElement parent = parameters.getPosition().getParent();
        if (parent instanceof PsiErrorElement) {
            IElementType preType;
            preNode = IoUtil.getPreEffectiveSibling(parent.getNode());
            if (preNode != null) {
                preType = preNode.getElementType();

                if (preType.equals(IoTypes.T_EVENT_NAME)
                        && parent.getNode().getTreePrev().getElementType().equals(TokenType.WHITE_SPACE)
                    // && (nextNode == null || nextNode.getElementType().equals(IoTypes.T_MESSAGE_ID))
                ) {
                    eventId = true;
                } else if (preType.equals(IoTypes.T_EVENT_ID)) {
                    brace = true;
                }
            }
        } else {
            IElementType preType;
            preNode = IoUtil.getPreEffectiveSibling(parameters.getPosition().getNode());
            if (preNode != null) {
                preType = preNode.getElementType();
                if (preType.equals(IoTypes.T_ENUM_HEAD)) {
                    eventName = true;
                }
            }
        }
        if (eventName) {
            eventName(parameters, result);
        } else if (eventId) {
            eventId(parameters, result);
        } else if (brace) {
            brace(parameters, result);
        }


    }

    public void messageType(@NotNull CompletionResultSet result) {
        result.addElement(LookupElementBuilder.create("CS"));
        result.addElement(LookupElementBuilder.create("SC"));
    }

    public void eventName(@NotNull CompletionParameters parameters, @NotNull CompletionResultSet result) {
        String text = parameters.getPosition().getText().replace("IntellijIdeaRulezzz", "");
        boolean extra = false;
        if (text.length() > 0) {

            text = StringUtil.toUpperFirstLetter(text);
            extra = true;
        }

        IoProtocolReader reader = IoReader.getInstance(parameters.getPosition().getProject().getBasePath())
                .getIoProtocolReaderMap().get(parameters.
                getOriginalFile()
                .getVirtualFile()
                .getPath());
        boolean add = true;
        List<Event> events = reader.getEvents();
        for (Bean bean : reader.getBeans()) {
            boolean addName = true;
            for (Event event : events) {
                if (Objects.equals(bean.getName(), event.getName())) {
                    addName = false;
                    break;
                }
            }
            if (addName) {
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

    public void eventId(@NotNull CompletionParameters parameters, @NotNull CompletionResultSet result) {
        PsiElement position = parameters.getPosition();
        String text = position.getText().replace("IntellijIdeaRulezzz", "");
        int start = position.getTextOffset() + text.length();
        String nextText = IoUtil.getNextText(start, parameters.getEditor().getDocument());
        PsiElement parent = parameters.getPosition().getParent();
        ASTNode preNode = IoUtil.getPreEffectiveSibling(parent.getNode());

        Integer eventId = IoUtil.getAutoEventId(
                parameters.getPosition().getProject().getBasePath(),
                IoUtil.getFileNamespace(parameters.
                getOriginalFile()
                .getVirtualFile()
                .getPath()), preNode.getText());
        result.addElement(LookupElementBuilder.create(eventId)
                .withInsertHandler((context, item) -> {

                    boolean insert = false;
                    if (nextText.length() == 0 || nextText.equals(" ") || nextText.equals("\n")) {
                        insert = true;
                    }
                    if (insert) {
                        int offset = parameters.getPosition().getTextOffset() + (eventId+"").length();
                        parameters.getEditor().getDocument().insertString(offset, "{\n}");
                        parameters.getEditor().getCaretModel().moveToOffset(offset + 1);
                        parameters.getEditor().getSelectionModel().selectLineAtCaret();
                        ReformatCodeProcessor processor = new ReformatCodeProcessor(parameters.getOriginalFile(), parameters.getEditor().getSelectionModel());
                        processor.runWithoutProgress();
                        parameters.getEditor().getSelectionModel().removeSelection();
                    }
                })
        );
//        result.addElement(LookupElementBuilder.create(eventId + " {\n}")
//                .withBoldness(true)
//                .withTailText(" (补全括号) ", true)
//                .withInsertHandler((context, item) -> {
//                    parameters.getEditor().getCaretModel().moveToOffset((eventId + "").length() + parameters.getOffset() + 2);
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
