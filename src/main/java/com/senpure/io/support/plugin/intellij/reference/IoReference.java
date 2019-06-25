package com.senpure.io.support.plugin.intellij.reference;

import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import com.intellij.util.IncorrectOperationException;
import com.senpure.io.support.plugin.intellij.IoIcons;
import com.senpure.io.support.plugin.intellij.psi.*;
import com.senpure.io.support.plugin.intellij.util.IoUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * IoReference
 *
 * @author senpure
 * @time 2019-06-14 14:39:44
 */
public class IoReference extends PsiReferenceBase<PsiElement> implements PsiPolyVariantReference {
    private String name;

    private String namespace;

    public IoReference(@NotNull PsiElement element, TextRange rangeInElement) {
        super(element, rangeInElement);
        name = element.getText().substring(rangeInElement.getStartOffset(), rangeInElement.getEndOffset());
        namespace = IoUtil.getFileNamespace(element.getContainingFile().getVirtualFile().getPath());
    }

    @NotNull
    @Override
    public ResolveResult[] multiResolve(boolean incompleteCode) {
        Project project = myElement.getProject();
        List<IoNamedElement> namedElements = IoUtil.findBeansOrEnums(project, namespace,name);
        List<ResolveResult> results = new ArrayList<>();

        for (IoNamedElement namedElement : namedElements) {
            results.add(new PsiElementResolveResult(namedElement));
        }

        return results.toArray(new ResolveResult[results.size()]);
    }


    @Nullable
    @Override
    public PsiElement resolve() {
        ResolveResult[] resolveResults = multiResolve(false);
        return resolveResults.length >= 1 ? resolveResults[0].getElement() : null;
    }

    @NotNull
    @Override
    public Object[] getVariants() {
        Project project = myElement.getProject();
        List<LookupElement> variants = new ArrayList<>();
        List<IoEntity> entities = IoUtil.findEntities(project);

        for (IoEntity entity : entities) {
            IoBean bean = entity.getBean();
            if (bean != null) {
                IoBeanName beanName = bean.getBeanName();
                if (beanName != null) {
                    variants.add(LookupElementBuilder.create(beanName).
                            withIcon(IoIcons.FILE).
                            withTypeText(beanName.getContainingFile().getName())
                    );
                }
            }
            IoEnum ioEnum = entity.getEnum();
            if (ioEnum == null) {
                IoEnumName enumName = ioEnum.getEnumName();
                if (enumName != null) {
                    variants.add(LookupElementBuilder.create(enumName).
                            withIcon(IoIcons.FILE).
                            withTypeText(enumName.getContainingFile().getName())
                    );
                }
            }
        }
        return variants.toArray();
    }

    @Override
    public PsiElement handleElementRename(@NotNull String newElementName) throws IncorrectOperationException {

        if (myElement instanceof PsiNamedElement) {
            ((PsiNamedElement) myElement).setName(newElementName);
        }
        return myElement;
    }
}
