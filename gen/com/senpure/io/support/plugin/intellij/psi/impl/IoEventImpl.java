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

public class IoEventImpl extends ASTWrapperPsiElement implements IoEvent {

  public IoEventImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull IoVisitor visitor) {
    visitor.visitEvent(this);
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
  public IoEventHead getEventHead() {
    return findNotNullChildByClass(IoEventHead.class);
  }

  @Override
  @NotNull
  public IoEventId getEventId() {
    return findNotNullChildByClass(IoEventId.class);
  }

  @Override
  @NotNull
  public IoEventName getEventName() {
    return findNotNullChildByClass(IoEventName.class);
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
  public IoRightBrace getRightBrace() {
    return findNotNullChildByClass(IoRightBrace.class);
  }

}
