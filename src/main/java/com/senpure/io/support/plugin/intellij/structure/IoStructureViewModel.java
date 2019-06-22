package com.senpure.io.support.plugin.intellij.structure;

import com.intellij.ide.structureView.StructureViewModel;
import com.intellij.ide.structureView.StructureViewModelBase;
import com.intellij.ide.structureView.StructureViewTreeElement;
import com.intellij.ide.util.treeView.smartTree.Sorter;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

/**
 * IoStructureViewModel
 *
 * @author senpure
 * @time 2019-06-18 20:04:24
 */
public class IoStructureViewModel extends StructureViewModelBase implements
        StructureViewModel.ElementInfoProvider {
    public IoStructureViewModel(@NotNull PsiFile psiFile) {
        super(psiFile, new IoEntityView(psiFile));
    }

    @NotNull
    public Sorter[] getSorters() {
        return new Sorter[]{Sorter.ALPHA_SORTER};
    }


    @Override
    public boolean isAlwaysShowsPlus(StructureViewTreeElement element) {
        return false;
    }

    @Override
    public boolean isAlwaysLeaf(StructureViewTreeElement element) {
        return false;
    }
}
