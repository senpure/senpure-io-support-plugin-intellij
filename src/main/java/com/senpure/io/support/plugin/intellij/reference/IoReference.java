package com.senpure.io.support.plugin.intellij.reference;

import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.util.IncorrectOperationException;
import com.senpure.io.support.plugin.intellij.IoIcons;
import com.senpure.io.support.plugin.intellij.psi.*;
import com.senpure.io.support.plugin.intellij.util.IoUtil;
import com.senpure.template.FileUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * IoReference
 *
 * @author senpure
 * @time 2019-06-14 14:39:44
 */
public class IoReference extends PsiReferenceBase<PsiElement> implements PsiPolyVariantReference {
    private String name;

    private String namespace;
    private Module module;

    private Logger logger = LoggerFactory.getLogger(getClass());
    public IoReference(@NotNull PsiElement element, TextRange rangeInElement) {
        super(element, rangeInElement);
        name = element.getText().substring(rangeInElement.getStartOffset(), rangeInElement.getEndOffset());
        namespace = IoUtil.getFileNamespace(IoUtil.getFilePath(element));
        module = IoUtil.getModule(element);

    }

    @NotNull
    @Override
    public ResolveResult[] multiResolve(boolean incompleteCode) {
        Project project = myElement.getProject();
        List<IoNamedElement> namedElements = IoUtil.findBeansOrEnums(project,
                module, name);
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
        if (resolveResults.length == 0) {
            return null;
        } else if (resolveResults.length == 1) {
            return resolveResults[0].getElement();
        }
        IoFile ioFile = (IoFile) myElement.getContainingFile();
        IoHead ioHead = ioFile.findChildByClass(IoHead.class);
        List<String> imports = new ArrayList<>(16);
        String filePath = IoUtil.getFilePath(myElement);
        imports.add(filePath);
        if (ioHead != null) {
            List<IoHeadContent> headContents = ioHead.getHeadContentList();
            for (IoHeadContent headContent : headContents) {
                if (headContent.getImport() != null) {
                    String parent = LocalFileSystem.getInstance().findFileByPath(filePath).getParent().getPath();
                    File importFile = FileUtil.file(headContent.getImport().
                            getImportValue().getText(), parent);
                    if (importFile.exists()) {
                        VirtualFile file = LocalFileSystem.getInstance().findFileByPath(importFile.getAbsolutePath());
                        //统一用虚拟文件系统避免文件分隔符不相同的情况
                        imports.add(file.getPath());
                    }
                    else {
                        logger.debug("{} 不存在 {}",importFile);
                    }

                }

            }
        }


       // logger.debug("import start--------");
//        for (String anImport : imports) {
//            logger.debug(anImport);
//        }
        for (ResolveResult resolveResult : resolveResults) {
            String path = IoUtil.getFilePath(resolveResult.getElement());
           // logger.debug("path {}",path);
            for (String anImport : imports) {
                if (Objects.equals(anImport, path)) {
                    return resolveResult.getElement();
                }
            }
        }
        return resolveResults.length >= 1 ? resolveResults[0].getElement() : null;
    }

    @NotNull
    @Override
    public Object[] getVariants() {
        Project project = myElement.getProject();
        List<LookupElement> variants = new ArrayList<>();
        List<IoEntity> entities = IoUtil.findEntities(project, module);

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
            if (ioEnum != null) {
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
