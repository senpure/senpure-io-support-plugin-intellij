// This is a generated file. Not intended for manual editing.
package com.senpure.io.support.plugin.intellij.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface IoEvent extends PsiElement {

  @NotNull
  List<IoEntityComment> getEntityCommentList();

  @NotNull
  IoEventHead getEventHead();

  @NotNull
  IoEventId getEventId();

  @NotNull
  IoEventName getEventName();

  @NotNull
  List<IoField> getFieldList();

  @NotNull
  IoLeftBrace getLeftBrace();

  @NotNull
  IoRightBrace getRightBrace();

}
