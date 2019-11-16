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

public class IoJsNamespaceImpl extends ASTWrapperPsiElement implements IoJsNamespace {

  public IoJsNamespaceImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull IoVisitor visitor) {
    visitor.visitJsNamespace(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof IoVisitor) accept((IoVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public IoJsNamespaceHead getJsNamespaceHead() {
    return findNotNullChildByClass(IoJsNamespaceHead.class);
  }

  @Override
  @NotNull
  public IoJsNamespaceValue getJsNamespaceValue() {
    return findNotNullChildByClass(IoJsNamespaceValue.class);
  }

  @Override
  @NotNull
  public IoSemicolon getSemicolon() {
    return findNotNullChildByClass(IoSemicolon.class);
  }

}
