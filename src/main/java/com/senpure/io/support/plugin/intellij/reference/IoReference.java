package com.senpure.io.support.plugin.intellij.reference;

import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import com.senpure.io.support.plugin.intellij.IoIcons;
import com.senpure.io.support.plugin.intellij.psi.IoBeanName;
import com.senpure.io.support.plugin.intellij.util.IoUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * IoReference
 *
 * @author senpure
 * @time 2019-06-14 14:39:44
 */
public class IoReference extends PsiReferenceBase<PsiElement> implements PsiPolyVariantReference {
    public IoReference(@NotNull PsiElement element, TextRange rangeInElement) {
        super(element, rangeInElement);
    }

    @NotNull
    @Override
    public ResolveResult[] multiResolve(boolean incompleteCode) {
        Project project = myElement.getProject();

        List<IoBeanName> ioBeanNames = IoUtil.findNames(project);
        List<ResolveResult> results = new ArrayList<>();
        for (IoBeanName beanName : ioBeanNames) {
            results.add(new PsiElementResolveResult(beanName));
        }
        return results.toArray(new ResolveResult[results.size()]);
    }



    @Nullable
    @Override
    public PsiElement resolve() {
        ResolveResult[] resolveResults = multiResolve(false);
        return resolveResults.length >= 1 ? resolveResults[0].getElement() : null;
    }

    @NotNull
    @Override
    public Object[] getVariants() {
        Project project = myElement.getProject();
        List<IoBeanName> ioBeanNames = IoUtil.findNames(project);
        List<LookupElement> variants = new ArrayList<>();
        for (IoBeanName beanName : ioBeanNames) {
            variants.add(LookupElementBuilder.create(beanName).
                    withIcon(IoIcons.FILE).
                    withTypeText(beanName.getContainingFile().getName())
            );
        }
        return variants.toArray();
    }
}
