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

public class IoHeadContentImpl extends ASTWrapperPsiElement implements IoHeadContent {

  public IoHeadContentImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull IoVisitor visitor) {
    visitor.visitHeadContent(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof IoVisitor) accept((IoVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public IoImport getImport() {
    return findChildByClass(IoImport.class);
  }

  @Override
  @Nullable
  public IoJavaPackage getJavaPackage() {
    return findChildByClass(IoJavaPackage.class);
  }

  @Override
  @Nullable
  public IoJsNamespace getJsNamespace() {
    return findChildByClass(IoJsNamespace.class);
  }

  @Override
  @Nullable
  public IoLuaNamespace getLuaNamespace() {
    return findChildByClass(IoLuaNamespace.class);
  }

  @Override
  @Nullable
  public IoNamespace getNamespace() {
    return findChildByClass(IoNamespace.class);
  }

}
