// This is a generated file. Not intended for manual editing.
package com.senpure.io.support.plugin.intellij.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface IoLuaNamespace extends PsiElement {

  @NotNull
  IoLuaNamespaceHead getLuaNamespaceHead();

  @NotNull
  IoLuaNamespaceValue getLuaNamespaceValue();

  @NotNull
  IoSemicolon getSemicolon();

}
