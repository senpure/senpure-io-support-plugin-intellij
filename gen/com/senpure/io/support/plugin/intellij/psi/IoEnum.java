// This is a generated file. Not intended for manual editing.
package com.senpure.io.support.plugin.intellij.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface IoEnum extends PsiElement {

  @NotNull
  List<IoEntityComment> getEntityCommentList();

  @NotNull
  List<IoEnumField> getEnumFieldList();

  @NotNull
  IoEnumHead getEnumHead();

  @NotNull
  IoEnumName getEnumName();

  @NotNull
  IoLeftBrace getLeftBrace();

  @NotNull
  IoRightBrace getRightBrace();

}
