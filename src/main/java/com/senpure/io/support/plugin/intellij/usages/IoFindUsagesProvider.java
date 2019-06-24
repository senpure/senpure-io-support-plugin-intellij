package com.senpure.io.support.plugin.intellij.usages;

import com.intellij.lang.cacheBuilder.DefaultWordsScanner;
import com.intellij.lang.cacheBuilder.WordsScanner;
import com.intellij.lang.findUsages.FindUsagesProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.TokenSet;
import com.senpure.io.support.plugin.intellij.IoLexerAdapter;
import com.senpure.io.support.plugin.intellij.psi.IoBean;
import com.senpure.io.support.plugin.intellij.psi.IoEnum;
import com.senpure.io.support.plugin.intellij.psi.IoFieldType;
import com.senpure.io.support.plugin.intellij.psi.IoTypes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * IoFindUsagesProvider
 *
 * @author senpure
 * @time 2019-06-15 10:01:22
 */
public class IoFindUsagesProvider implements FindUsagesProvider {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Nullable
    @Override
    public WordsScanner getWordsScanner() {
        return new DefaultWordsScanner(new IoLexerAdapter(),
                // TokenSet.create(IoTypes.T_BEAN_NAME),
                TokenSet.create(IoTypes.BEAN_NAME),
                TokenSet.create(IoTypes.T_CODE_COMMENT),
                TokenSet.EMPTY);
    }

    @Override
    public boolean canFindUsagesFor(@NotNull PsiElement psiElement) {
        //  logger.debug("canFindUsagesFor: {} -> {}", psiElement, psiElement.getText());
        if (psiElement instanceof IoEnum) {
            return true;
        } else if (psiElement instanceof IoBean) {
            return true;
        }
        return false;
    }

    @Nullable
    @Override
    public String getHelpId(@NotNull PsiElement psiElement) {
        return null;
    }

    @NotNull
    @Override
    public String getType(@NotNull PsiElement element) {
        if (element instanceof IoEnum) {
            return "enum";
        } else if (element instanceof IoBean) {
            return "bean";
        } else if (element instanceof IoFieldType) {
            return "fieldType";
        } else {
            return element.getText();
        }

    }

    @NotNull
    @Override
    public String getDescriptiveName(@NotNull PsiElement element) {
        if (element instanceof IoFieldType) {
            IoFieldType fieldType = (IoFieldType) element;
            return "fieldType:" + fieldType.getText();
        } else {
            return element.getText() + "_A";
        }
    }


    @NotNull
    @Override
    public String getNodeText(@NotNull PsiElement element, boolean useFullName) {
        if (element instanceof IoFieldType) {
            IoFieldType fieldType = (IoFieldType) element;
            return fieldType.getText() + "feldtype";
        } else {
            return element.getText() + "_B";
        }
    }
}
