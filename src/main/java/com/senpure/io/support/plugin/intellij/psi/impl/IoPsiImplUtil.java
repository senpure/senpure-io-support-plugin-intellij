package com.senpure.io.support.plugin.intellij.psi.impl;

import com.intellij.icons.AllIcons;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceService;
import com.intellij.psi.impl.source.resolve.reference.ReferenceProvidersRegistry;
import com.intellij.psi.util.PsiTreeUtil;
import com.senpure.io.support.plugin.intellij.psi.*;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;

/**
 * IoPsiImplUtil
 *
 * @author senpure
 * @time 2019-05-23 15:58:26
 */
public class IoPsiImplUtil {

    private static Logger logger = LoggerFactory.getLogger(IoPsiImplUtil.class);

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


    public static String getName(IoFieldType element) {

        return element.getText();
    }

    public static PsiElement setName(IoBeanName element, String newName) {
        IoBean bean = IoElementFactory.createBean(element.getProject(), newName);
        element.replace(bean.getBeanName());
        return element;
    }

    public static PsiElement setName(IoFieldType element, String newName) {

        IoField ioField = IoElementFactory.createField(element.getProject(), newName);
        element.replace(ioField.getFieldType());
        return element;
    }


    public static PsiElement setName(IoEventName element, String newName) {


        IoEvent event = IoElementFactory.createEvent(element.getProject(), newName);
        element.replace(event.getEventName());
        return element;
    }

    public static PsiElement setName(IoMessageName element, String newName) {

        IoMessage message = IoElementFactory.createMessage(element.getProject(), newName);
        element.replace(message.getMessageName());
        return element;
    }

    public static PsiElement setName(IoEnumName element, String newName) {

        IoEnum ioEnum = IoElementFactory.createEnum(element.getProject(), newName);
        element.replace(ioEnum.getEnumName());
        return element;
    }

    public static PsiElement getNameIdentifier(IoBeanName element) {

        return element;
    }


    public static PsiElement getNameIdentifier(IoFieldType element) {

        return element;
    }

    public static PsiElement getNameIdentifier(IoEnumName element) {

        return element;
    }


    public static PsiElement getNameIdentifier(IoMessageName element) {

        return element;
    }

    public static PsiElement getNameIdentifier(IoEventName element) {

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
                return AllIcons.FileTypes.Css;
            }
        };
    }

    public static ItemPresentation getPresentation(final IoMessageName element) {

        logger.debug("getPresentation(final IoMessageName element) {}", element.getText());
        return new ItemPresentation() {

            @Nullable
            @Override
            public String getPresentableText() {

                PsiElement type = PsiTreeUtil.getPrevSiblingOfType(element, IoMessageType.class);
                logger.debug("type {}", type);
                PsiElement id = PsiTreeUtil.getNextSiblingOfType(element, IoMessageId.class);
                StringBuilder sb = new StringBuilder();
                if (type != null) {
                    sb.append(type.getText()).append(" ");
                }
                sb.append(element.getText());
                if (id != null) {
                    sb.append(" ").append(id.getText());
                }
                return sb.toString();
            }

            @Nullable
            @Override
            public String getLocationString() {
                logger.debug("getLocationString {} {}",element.getContainingFile().getName(), element.getContainingFile().getVirtualFile().getPath());
                return element.getContainingFile().getName();
            }

            @Nullable
            @Override
            public Icon getIcon(boolean unused) {
                logger.debug("getIcon {}", AllIcons.FileTypes.Css);
                return AllIcons.FileTypes.Css;
            }
        };
    }

    public static ItemPresentation getPresentation(final IoEventName element) {
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
                return AllIcons.FileTypes.Css;
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
                return AllIcons.FileTypes.Css;
            }
        };
    }

    public static ItemPresentation getPresentation(final IoEnumName element) {
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
                return AllIcons.FileTypes.Css;
            }
        };
    }

    public static PsiReference[] getReferences(PsiElement element) {
        //SharedPsiElementImplUtil.getReferences(element);
        return ReferenceProvidersRegistry.getReferencesFromProviders(element, PsiReferenceService.Hints.NO_HINTS);
    }

    public static PsiReference[] getReferences(IoBeanName element) {
        //SharedPsiElementImplUtil.getReferences(element);
        return ReferenceProvidersRegistry.getReferencesFromProviders(element, PsiReferenceService.Hints.NO_HINTS);
    }

    public static PsiReference[] getReferences(IoEnumName element) {
        //SharedPsiElementImplUtil.getReferences(element);
        return ReferenceProvidersRegistry.getReferencesFromProviders(element, PsiReferenceService.Hints.NO_HINTS);
    }


    //暂时没有用处
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
