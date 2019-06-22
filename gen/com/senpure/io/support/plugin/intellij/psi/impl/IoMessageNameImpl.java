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
import com.intellij.psi.PsiReference;

public class IoMessageNameImpl extends IoNamedElementImpl implements IoMessageName {

  public IoMessageNameImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull IoVisitor visitor) {
    visitor.visitMessageName(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof IoVisitor) accept((IoVisitor)visitor);
    else super.accept(visitor);
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

  @Override
  public PsiReference[] getReferences() {
    return IoPsiImplUtil.getReferences(this);
  }

}
