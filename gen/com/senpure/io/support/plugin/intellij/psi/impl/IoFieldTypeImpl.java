// This is a generated file. Not intended for manual editing.
package com.senpure.io.support.plugin.intellij.psi.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static com.senpure.io.support.plugin.intellij.psi.IoTypes.*;
import com.senpure.io.support.plugin.intellij.psi.*;
import com.intellij.navigation.ItemPresentation;

public class IoFieldTypeImpl extends IoNamedElementImpl implements IoFieldType {

  public IoFieldTypeImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull IoVisitor visitor) {
    visitor.visitFieldType(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof IoVisitor) accept((IoVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public PsiElement getTFieldTypeBase() {
    return findChildByType(T_FIELD_TYPE_BASE);
  }

  @Override
  public String getName() {
    return IoPsiImplUtil.getName(this);
  }

  @Override
  public PsiElement setName(String newName) {
    return IoPsiImplUtil.setName(this, newName);
  }

  @Override
  public PsiElement getNameIdentifier() {
    return IoPsiImplUtil.getNameIdentifier(this);
  }

  @Override
  public ItemPresentation getPresentation() {
    return IoPsiImplUtil.getPresentation(this);
  }

}
