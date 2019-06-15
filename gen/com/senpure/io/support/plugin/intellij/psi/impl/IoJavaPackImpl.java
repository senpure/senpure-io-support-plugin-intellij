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

public class IoJavaPackImpl extends ASTWrapperPsiElement implements IoJavaPack {

  public IoJavaPackImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull IoVisitor visitor) {
    visitor.visitJavaPack(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof IoVisitor) accept((IoVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public IoJavaPackHead getJavaPackHead() {
    return findNotNullChildByClass(IoJavaPackHead.class);
  }

  @Override
  @NotNull
  public IoJavaPackValue getJavaPackValue() {
    return findNotNullChildByClass(IoJavaPackValue.class);
  }

  @Override
  @NotNull
  public IoSemicolon getSemicolon() {
    return findNotNullChildByClass(IoSemicolon.class);
  }

}
