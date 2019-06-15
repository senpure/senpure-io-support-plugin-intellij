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

public class IoEntityImpl extends ASTWrapperPsiElement implements IoEntity {

  public IoEntityImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull IoVisitor visitor) {
    visitor.visitEntity(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof IoVisitor) accept((IoVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public IoBean getBean() {
    return findChildByClass(IoBean.class);
  }

  @Override
  @Nullable
  public IoEnum getEnum() {
    return findChildByClass(IoEnum.class);
  }

  @Override
  @Nullable
  public IoEvent getEvent() {
    return findChildByClass(IoEvent.class);
  }

  @Override
  @Nullable
  public IoMessage getMessage() {
    return findChildByClass(IoMessage.class);
  }

}
