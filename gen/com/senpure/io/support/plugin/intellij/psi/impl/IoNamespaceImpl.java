// This is a generated file. Not intended for manual editing.
package com.senpure.io.support.plugin.intellij.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static com.senpure.io.support.plugin.intellij.psi.IoTypes.*;
import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.senpure.io.support.plugin.intellij.psi.*;

public class IoNamespaceImpl extends ASTWrapperPsiElement implements IoNamespace {

  public IoNamespaceImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull IoVisitor visitor) {
    visitor.visitNamespace(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof IoVisitor) accept((IoVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public IoNamespaceHead getNamespaceHead() {
    return findNotNullChildByClass(IoNamespaceHead.class);
  }

  @Override
  @NotNull
  public IoNamespaceValue getNamespaceValue() {
    return findNotNullChildByClass(IoNamespaceValue.class);
  }

  @Override
  @NotNull
  public IoSemicolon getSemicolon() {
    return findNotNullChildByClass(IoSemicolon.class);
  }

}
