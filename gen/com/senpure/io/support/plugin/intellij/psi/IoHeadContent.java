// This is a generated file. Not intended for manual editing.
package com.senpure.io.support.plugin.intellij.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface IoHeadContent extends PsiElement {

  @Nullable
  IoImport getImport();

  @Nullable
  IoJavaPackage getJavaPackage();

  @Nullable
  IoJsNamespace getJsNamespace();

  @Nullable
  IoLuaNamespace getLuaNamespace();

  @Nullable
  IoNamespace getNamespace();

}
