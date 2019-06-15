package com.senpure.io.support.plugin.intellij.usages;

import com.intellij.find.findUsages.FindUsagesHandler;
import com.intellij.find.findUsages.FindUsagesOptions;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.search.SearchScope;
import com.intellij.usageView.UsageInfo;
import com.intellij.util.Processor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

/**
 * IoFindUsagesHandler
 *
 * @author senpure
 * @time 2019-06-15 15:04:31
 */

public class IoFindUsagesHandler extends FindUsagesHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    protected IoFindUsagesHandler(@NotNull PsiElement psiElement) {
        super(psiElement);
    }

    @NotNull
    @Override
    public Collection<PsiReference> findReferencesToHighlight(@NotNull PsiElement target, @NotNull SearchScope searchScope) {

        Collection<PsiReference> collections = super.findReferencesToHighlight(target, searchScope);

        logger.debug("IoFindUsagesHandler Colletcions {}  {} ", target, collections);


      //  List<IoBeanName> ioBeanNames = IoUtil.findNames(target.getProject());

     //  for (IoBeanName ioBeanName : ioBeanNames) {
           // collections.add(new IoReference(ioBeanName, ioBeanName.getTextRange()));
     //   }
        //logger.debug("IoFindUsagesHandler Colletcions {}  {} ", target, collections);
        return collections;
    }

    @NotNull
    @Override
    public PsiElement[] getPrimaryElements() {
        return super.getPrimaryElements();
    }

    @Override
    public boolean processElementUsages(@NotNull PsiElement element, @NotNull Processor<UsageInfo> processor, @NotNull FindUsagesOptions options) {
        return super.processElementUsages(element, processor, options);
    }
}
