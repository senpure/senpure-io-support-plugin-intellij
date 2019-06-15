package com.senpure.io.support.plugin.intellij.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.senpure.io.support.plugin.intellij.psi.IoNamedElement;
import org.jetbrains.annotations.NotNull;

/**
 * IoNamed
 *
 * @author senpure
 * @time 2019-06-14 10:44:59
 */
public abstract class IoNamedElementImpl extends ASTWrapperPsiElement implements IoNamedElement {
    public IoNamedElementImpl(@NotNull ASTNode node) {
        super(node);
    }
}
