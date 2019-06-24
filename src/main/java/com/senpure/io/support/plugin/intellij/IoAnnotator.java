package com.senpure.io.support.plugin.intellij;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.psi.PsiElement;
import com.senpure.io.generator.util.ProtocolUtil;
import com.senpure.io.support.plugin.intellij.psi.*;
import com.senpure.io.support.plugin.intellij.util.IoUtil;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

/**
 * IoAnnotator
 *
 * @author senpure
 * @time 2019-05-24 17:17:31
 */
public class IoAnnotator implements Annotator {
    @Override
    public void annotate(@NotNull PsiElement element, @NotNull AnnotationHolder holder) {

        if (element instanceof IoBean) {
            IoBean bean = (IoBean) element;
            if (bean.getFieldList().size() == 0) {
                holder.createErrorAnnotation(bean.getBeanName(), "没有定义字段");
            }
            checkField(bean.getFieldList(), holder);
        } else if (element instanceof IoMessage) {
            IoMessage bean = (IoMessage) element;
            checkField(bean.getFieldList(), holder);
        } else if (element instanceof IoEvent) {
            IoEvent bean = (IoEvent) element;
            checkField(bean.getFieldList(), holder);
        } else if (element instanceof IoEnum) {
            IoEnum bean = (IoEnum) element;
            if (bean.getEnumFieldList().size() < 2) {
                holder.createErrorAnnotation(bean.getEnumName(), "枚举至少需要两个字段");
            }
            checkEnum(bean.getEnumFieldList(), holder);
        }
    }


    private void checkEnum(List<IoEnumField> fields, @NotNull AnnotationHolder holder) {

        for (int i = 0; i < fields.size() - 1; i++) {
            IoEnumField a = fields.get(i);
            for (int j = i + 1; j < fields.size(); j++) {
                IoEnumField b = fields.get(j);
                if (Objects.equals(a.getFieldName().getText(), b.getFieldName().getText())) {
                    holder.createErrorAnnotation(a.getFieldName(), "相同字段名");
                    holder.createErrorAnnotation(b.getFieldName(), "相同字段名");
                }
            }
        }


        int index = 1;
        int[] indexes = new int[fields.size()];

        for (int i = 0; i < indexes.length; i++) {
            IoFieldIndex fieldIndex = fields.get(i).getFieldIndex();
            if (fieldIndex == null) {
                indexes[i] = index++;
            } else {
                indexes[i] = Integer.parseInt(fieldIndex.getText());
            }
        }
        if (indexes.length > 1) {
            if (indexes[0] != 1) {
                IoEnumField a = fields.get(0);
                if (a.getFieldIndex() == null) {
                    holder.createErrorAnnotation(a.getFieldName(), "枚举第一个字段index必须为一,作为默认值");
                } else {
                    holder.createErrorAnnotation(a.getFieldIndex(), "枚举第一个字段index必须为一,作为默认值");
                }

            }
        }
        for (int i = 0; i < indexes.length - 1; i++) {
            for (int j = i + 1; j < indexes.length; j++) {
                if (indexes[i] == indexes[j]) {
                    IoEnumField a = fields.get(i);
                    IoEnumField b = fields.get(j);
                    if (a.getFieldIndex() == null) {
                        holder.createErrorAnnotation(a.getFieldName(), "相同index "+indexes[i]);
                    } else {
                        holder.createErrorAnnotation(a.getFieldIndex(), "相同index "+indexes[i]);
                    }
                    if (b.getFieldIndex() == null) {
                        holder.createErrorAnnotation(b.getFieldName(), "相同index "+indexes[i]);
                    } else {
                        holder.createErrorAnnotation(b.getFieldIndex(), "相同index "+indexes[i]);
                    }


                }
            }
        }
    }

    private void checkField(List<IoField> fields, @NotNull AnnotationHolder holder) {

        for (int i = 0; i < fields.size() - 1; i++) {
            IoField a = fields.get(i);
            for (int j = i + 1; j < fields.size(); j++) {
                IoField b = fields.get(j);
                if (Objects.equals(a.getFieldName().getText(), b.getFieldName().getText())) {
                    holder.createErrorAnnotation(a.getFieldName(), "相同字段名");
                    holder.createErrorAnnotation(b.getFieldName(), "相同字段名");
                }
            }
        }


        for (IoField field : fields) {
            String type = field.getFieldType().getText();
            if (!ProtocolUtil.isBaseField(type)) {
                if (IoUtil.findBeansOrEnums(field.getProject(), type).size() == 0) {
                    holder.createErrorAnnotation(field.getFieldType(), "没有找到定义");
                }
            }
        }
        int index = 1;
        int[] indexes = new int[fields.size()];

        for (int i = 0; i < indexes.length; i++) {
            IoFieldIndex fieldIndex = fields.get(i).getFieldIndex();
            if (fieldIndex == null) {
                indexes[i] = index++;
            } else {
                indexes[i] = Integer.parseInt(fieldIndex.getText());
            }
        }

        for (int i = 0; i < indexes.length - 1; i++) {
            for (int j = i + 1; j < indexes.length; j++) {
                if (indexes[i] == indexes[j]) {
                    IoField a = fields.get(i);
                    IoField b = fields.get(j);
                    if (a.getFieldIndex() == null) {
                        holder.createErrorAnnotation(a.getFieldName(), "相同index "+indexes[i]);
                    } else {
                        holder.createErrorAnnotation(a.getFieldIndex(), "相同index "+indexes[i]);
                    }
                    if (b.getFieldIndex() == null) {
                        holder.createErrorAnnotation(b.getFieldName(), "相同index "+indexes[i]);
                    } else {
                        holder.createErrorAnnotation(b.getFieldIndex(), "相同index "+indexes[i]);
                    }


                }
            }
        }

    }
}
