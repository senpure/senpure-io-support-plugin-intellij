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

public class IoLuaNamespaceValueImpl extends ASTWrapperPsiElement implements IoLuaNamespaceValue {

  public IoLuaNamespaceValueImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull IoVisitor visitor) {
    visitor.visitLuaNamespaceValue(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof IoVisitor) accept((IoVisitor)visitor);
    else super.accept(visitor);
  }

}