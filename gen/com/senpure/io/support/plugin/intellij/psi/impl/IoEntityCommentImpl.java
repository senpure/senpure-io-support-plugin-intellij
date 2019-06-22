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

public class IoEntityCommentImpl extends ASTWrapperPsiElement implements IoEntityComment {

  public IoEntityCommentImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull IoVisitor visitor) {
    visitor.visitEntityComment(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof IoVisitor) accept((IoVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public PsiElement getTCodeComment() {
    return findChildByType(T_CODE_COMMENT);
  }

  @Override
  @Nullable
  public PsiElement getTLineComment() {
    return findChildByType(T_LINE_COMMENT);
  }

}
