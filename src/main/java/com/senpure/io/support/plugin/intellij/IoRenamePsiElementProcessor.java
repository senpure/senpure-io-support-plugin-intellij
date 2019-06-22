package com.senpure.io.support.plugin.intellij;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;
import com.intellij.refactoring.rename.RenamePsiElementProcessor;
import org.jetbrains.annotations.NotNull;

/**
 * IoRenamePsiElementProcessor
 * 没有使用
 *
 * @author senpure
 * @time 2019-06-17 16:46:03
 */
public class IoRenamePsiElementProcessor extends RenamePsiElementProcessor {

    @Override
    public boolean canProcessElement(@NotNull PsiElement element) {
        return element instanceof PsiNamedElement;
    }
}
