// This is a generated file. Not intended for manual editing.
package com.senpure.io.support.plugin.intellij.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface IoField extends PsiElement {

  @Nullable
  IoEqual getEqual();

  @Nullable
  IoFieldComment getFieldComment();

  @Nullable
  IoFieldIndex getFieldIndex();

  @Nullable
  IoFieldList getFieldList();

  @NotNull
  IoFieldName getFieldName();

  @NotNull
  IoFieldType getFieldType();

  @NotNull
  IoSemicolon getSemicolon();

}
