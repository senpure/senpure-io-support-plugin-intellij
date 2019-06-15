package com.senpure.io.support.plugin.intellij;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.psi.PsiElement;
import com.senpure.io.support.plugin.intellij.psi.IoFieldList;
import org.jetbrains.annotations.NotNull;

/**
 * IoAnnotator
 *
 * @author senpure
 * @time 2019-05-24 17:17:31
 */
public class IoAnnotator implements Annotator {
    @Override
    public void annotate(@NotNull PsiElement element, @NotNull AnnotationHolder holder) {
        if (element instanceof IoFieldList) {
           // holder.createErrorAnnotation(element.getTextRange(), "now always error");
        }
    }
}
