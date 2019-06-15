package com.senpure.io.support.plugin.intellij.completion;

import com.intellij.codeInsight.actions.ReformatCodeProcessor;
import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiErrorElement;
import com.intellij.psi.tree.IElementType;
import com.intellij.util.ProcessingContext;
import com.senpure.io.support.plugin.intellij.IoCompletionContributor;
import com.senpure.io.support.plugin.intellij.psi.IoTypes;
import org.jetbrains.annotations.NotNull;

/**
 * EveryHeadCompletion
 *
 * @author senpure
 * @time 2019-05-31 17:13:38
 */
public class MessageCompletionProvider extends CompletionProvider {

    public static void reg(CompletionContributor contributor) {

        contributor.extend(CompletionType.BASIC, PlatformPatterns.psiElement(IoTypes.T_MESSAGE_NAME), new MessageCompletionProvider());

    }

    @Override
    protected void addCompletions(@NotNull CompletionParameters parameters, @NotNull ProcessingContext context, @NotNull CompletionResultSet result) {


        PsiElement parent = parameters.getPosition().getParent();

        // System.out.println(parameters.getPosition().getNavigationElement());
        if (parameters.getPosition().getParent() instanceof PsiErrorElement) {
            PsiErrorElement psiErrorElement = (PsiErrorElement) parent;

            IElementType elementType = IoCompletionContributor.preElementType(psiErrorElement.getNode());
            if (elementType != null) {

                if (elementType.equals(IoTypes.T_MESSAGE_HEAD)) {
                    result.addElement(LookupElementBuilder.create("CS"));
                    result.addElement(LookupElementBuilder.create("SC"));
                } else if (elementType.equals(IoTypes.T_MESSAGE_ID)) {



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

                return;
            }


            // return;
        }


        // result.addElement(LookupElementBuilder.create("CS"));
        // result.addElement(LookupElementBuilder.create("SC"));
    }
}
