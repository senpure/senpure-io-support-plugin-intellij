package com.senpure.io.support.plugin.intellij.structure;

import com.intellij.ide.util.treeView.smartTree.TreeElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiTreeUtil;
import com.senpure.io.support.plugin.intellij.psi.IoBean;
import com.senpure.io.support.plugin.intellij.psi.IoEntity;
import com.senpure.io.support.plugin.intellij.psi.impl.IoBeanImpl;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * IoEntityView
 *
 * @author senpure
 * @time 2019-06-18 19:59:29
 */
public class IoEntityView extends IoAbstractView {


    private PsiFile psiFile;

    public IoEntityView(PsiFile element) {

        super(element);
        this.psiFile = element;
    }




    @NotNull
    @Override
    public TreeElement[] getChildren() {
        IoEntity[] ioEntities = PsiTreeUtil.getChildrenOfType(element, IoEntity.class);
        List<TreeElement> treeElements = new ArrayList<>(16);
        for (IoEntity entity : ioEntities) {

            IoBean bean = entity.getBean();
            if (bean != null) {
                treeElements.add(new IoBeanView((IoBeanImpl) bean));
            }
        }
        return treeElements.toArray(new TreeElement[treeElements.size()]);
    }

}
