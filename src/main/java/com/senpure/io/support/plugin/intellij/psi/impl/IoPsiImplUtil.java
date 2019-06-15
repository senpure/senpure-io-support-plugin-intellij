package com.senpure.io.support.plugin.intellij.psi.impl;

import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiElement;
import com.senpure.io.support.plugin.intellij.IoIcons;
import com.senpure.io.support.plugin.intellij.psi.*;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * IoPsiImplUtil
 *
 * @author senpure
 * @time 2019-05-23 15:58:26
 */
public class IoPsiImplUtil {


    public static String getName(IoBeanName element) {

        return element.getText();
    }

    public static String getName(IoEnumName element) {

        return element.getText();
    }

    public static String getName(IoMessageName element) {

        return element.getText();
    }

    public static String getName(IoEventName element) {

        return element.getText();
    }

    public static String getName(IoFieldName element) {

        return element.getText();
    }

    public static PsiElement setName(IoBeanName element, String newName) {
        IoBean bean = IoElementFactory.createBean(element.getProject(), newName);
        element.replace(bean.getBeanName());
        return element;
    }

    public static PsiElement getNameIdentifier(IoBeanName element) {

        return element;
    }

    public static String getName(IoFieldType element) {

        return element.getText();
    }

    public static PsiElement setName(IoFieldType element, String newName) {

        IoField ioField = IoElementFactory.createField(element.getProject(), newName);
        element.replace(ioField.getFieldType());
        return element;
    }

    public static PsiElement getNameIdentifier(IoFieldType element) {

        return element;
    }


    public static ItemPresentation getPresentation(final IoBeanName element) {
        return new ItemPresentation() {

            @Nullable
            @Override
            public String getPresentableText() {
                return element.getText();
            }

            @Nullable
            @Override
            public String getLocationString() {
                return element.getContainingFile().getName();
            }

            @Nullable
            @Override
            public Icon getIcon(boolean unused) {
                return IoIcons.FILE;
            }
        };
    }

    public static ItemPresentation getPresentation(final IoFieldType element) {
        return new ItemPresentation() {

            @Nullable
            @Override
            public String getPresentableText() {
                return element.getText();
            }

            @Nullable
            @Override
            public String getLocationString() {
                return element.getContainingFile().getName();
            }

            @Nullable
            @Override
            public Icon getIcon(boolean unused) {
                return IoIcons.FILE;
            }
        };
    }


    public static String getName(IoEntity element) {

        if (element.getBean() != null) {
            return getName(element.getBean());
        } else if (element.getEnum() != null) {
            return getName(element.getEnum());
        } else if (element.getMessage() != null) {
            return getName(element.getMessage());
        } else if (element.getEvent() != null) {
            return getName(element.getEvent());
        }
        return null;
    }

    public static PsiElement setName(IoEntity element, String newName) {

        return element;
    }

    public static PsiElement getNameIdentifier(IoEntity element) {

        return null;

    }

    public static PsiElement setName(IoBean bean, String newName) {

        //  bean.getBeanName().replace()

        //   bean.getBeanName().getManager().
        //  PsiFileFactory.getInstance(bean.getProject()).
        return bean;
    }

    public static String getName(IoBean bean) {
        IoBeanName beanName = bean.getBeanName();
        if (beanName != null) {
            return beanName.getText();
        }
        return null;

    }

    public static String getName(IoEnum bean) {
        IoEnumName beanName = bean.getEnumName();
        if (beanName != null) {
            return beanName.getText();
        }
        return null;

    }

    public static String getName(IoMessage bean) {
        IoMessageName beanName = bean.getMessageName();
        if (beanName != null) {
            return beanName.getText();
        }
        return null;

    }

    public static String getName(IoEvent bean) {
        IoEventName beanName = bean.getEventName();
        if (beanName != null) {
            return beanName.getText();
        }
        return null;

    }
}
