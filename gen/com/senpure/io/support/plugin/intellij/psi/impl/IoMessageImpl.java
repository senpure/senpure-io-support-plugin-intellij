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

public class IoMessageImpl extends ASTWrapperPsiElement implements IoMessage {

  public IoMessageImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull IoVisitor visitor) {
    visitor.visitMessage(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof IoVisitor) accept((IoVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<IoEntityComment> getEntityCommentList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, IoEntityComment.class);
  }

  @Override
  @NotNull
  public List<IoField> getFieldList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, IoField.class);
  }

  @Override
  @NotNull
  public IoLeftBrace getLeftBrace() {
    return findNotNullChildByClass(IoLeftBrace.class);
  }

  @Override
  @NotNull
  public IoMessageHead getMessageHead() {
    return findNotNullChildByClass(IoMessageHead.class);
  }

  @Override
  @NotNull
  public IoMessageId getMessageId() {
    return findNotNullChildByClass(IoMessageId.class);
  }

  @Override
  @NotNull
  public IoMessageName getMessageName() {
    return findNotNullChildByClass(IoMessageName.class);
  }

  @Override
  @NotNull
  public IoMessageType getMessageType() {
    return findNotNullChildByClass(IoMessageType.class);
  }

  @Override
  @NotNull
  public IoRightBrace getRightBrace() {
    return findNotNullChildByClass(IoRightBrace.class);
  }

}
