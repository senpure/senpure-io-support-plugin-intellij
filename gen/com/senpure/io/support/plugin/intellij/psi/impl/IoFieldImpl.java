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

public class IoFieldImpl extends ASTWrapperPsiElement implements IoField {

  public IoFieldImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull IoVisitor visitor) {
    visitor.visitField(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof IoVisitor) accept((IoVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public IoEqual getEqual() {
    return findChildByClass(IoEqual.class);
  }

  @Override
  @Nullable
  public IoFieldComment getFieldComment() {
    return findChildByClass(IoFieldComment.class);
  }

  @Override
  @Nullable
  public IoFieldIndex getFieldIndex() {
    return findChildByClass(IoFieldIndex.class);
  }

  @Override
  @Nullable
  public IoFieldList getFieldList() {
    return findChildByClass(IoFieldList.class);
  }

  @Override
  @NotNull
  public IoFieldName getFieldName() {
    return findNotNullChildByClass(IoFieldName.class);
  }

  @Override
  @NotNull
  public IoFieldType getFieldType() {
    return findNotNullChildByClass(IoFieldType.class);
  }

  @Override
  @NotNull
  public IoSemicolon getSemicolon() {
    return findNotNullChildByClass(IoSemicolon.class);
  }

}
