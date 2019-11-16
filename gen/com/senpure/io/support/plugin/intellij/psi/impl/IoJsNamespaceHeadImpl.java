// This is a generated file. Not intended for manual editing.
package com.senpure.io.support.plugin.intellij.psi.impl;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.senpure.io.support.plugin.intellij.psi.IoJsNamespaceHead;
import com.senpure.io.support.plugin.intellij.psi.IoVisitor;
import org.jetbrains.annotations.NotNull;

public class IoJsNamespaceHeadImpl extends ASTWrapperPsiElement implements IoJsNamespaceHead {

  public IoJsNamespaceHeadImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull IoVisitor visitor) {
    visitor.visitJsNamespaceHead(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof IoVisitor) accept((IoVisitor)visitor);
    else super.accept(visitor);
  }

}
