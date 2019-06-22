package com.senpure.io.support.plugin.intellij.completion;

import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.lang.ASTNode;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiErrorElement;
import com.intellij.psi.tree.IElementType;
import com.intellij.util.ProcessingContext;
import com.senpure.io.support.plugin.intellij.psi.IoTypes;
import com.senpure.io.support.plugin.intellij.util.IoUtil;
import org.jetbrains.annotations.NotNull;

/**
 * BeanCompletionProvider
 *
 * @author senpure
 * @time 2019-06-20 10:19:36
 */
public class BeanCompletionProvider extends CompletionProvider {

    public static void reg(CompletionContributor contributor) {

        contributor.extend(CompletionType.BASIC, PlatformPatterns.psiElement(IoTypes.T_BEAN_NAME), new BeanCompletionProvider());
    }

    @Override
    protected void addCompletions(@NotNull CompletionParameters parameters, @NotNull ProcessingContext context, @NotNull CompletionResultSet result) {
        ASTNode preNode;
        PsiElement parent = parameters.getPosition().getParent();
        if (parent instanceof PsiErrorElement) {
            IElementType preType;
            preNode = IoUtil.preEffectiveNode(parent.getNode());
            if (preNode != null) {
                preType = preNode.getElementType();
                if (preType.equals(IoTypes.T_BEAN_NAME)) {
                    LookupElementBuilder builder = LookupElementBuilder.create("{\n}")
                            .withBoldness(true)
                            .withTailText(" (补全括号) ", true)
                            .withInsertHandler((context1, item) -> {
                                parameters.getEditor().getCaretModel().moveToOffset(parameters.getOffset() + 1);
                            });
                    result.addElement(builder);
                }

            }
        }
    }
}

