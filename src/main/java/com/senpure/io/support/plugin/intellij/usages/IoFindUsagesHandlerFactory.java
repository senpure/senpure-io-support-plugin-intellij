package com.senpure.io.support.plugin.intellij.usages;

import com.intellij.find.findUsages.FindUsagesHandler;
import com.intellij.find.findUsages.FindUsagesHandlerFactory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * IoFindUsagesHandlerFactory
 *
 * @author senpure
 * @time 2019-06-15 14:59:39
 */
public class IoFindUsagesHandlerFactory extends FindUsagesHandlerFactory {


    @Override
    public boolean canFindUsages(@NotNull PsiElement element) {
        return element instanceof PsiNamedElement;
    }

    @Nullable
    @Override
    public FindUsagesHandler createFindUsagesHandler(@NotNull PsiElement element, boolean forHighlightUsages) {
        return new IoFindUsagesHandler(element);
    }


    @Override
    public FindUsagesHandler createFindUsagesHandler(@NotNull PsiElement element, @NotNull OperationMode operationMode) {
        return super.createFindUsagesHandler(element, operationMode);
    }
}
