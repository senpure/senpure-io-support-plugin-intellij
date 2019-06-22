// This is a generated file. Not intended for manual editing.
package com.senpure.io.support.plugin.intellij.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiReference;

public interface IoBeanName extends IoNamedElement {

  String getName();

  PsiElement setName(String newName);

  PsiElement getNameIdentifier();

  ItemPresentation getPresentation();

  @NotNull
  PsiReference[] getReferences();

}
