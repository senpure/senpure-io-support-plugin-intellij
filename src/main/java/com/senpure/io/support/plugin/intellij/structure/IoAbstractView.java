package com.senpure.io.support.plugin.intellij.structure;

import com.intellij.ide.projectView.PresentationData;
import com.intellij.ide.structureView.StructureViewTreeElement;
import com.intellij.ide.util.treeView.smartTree.SortableTreeElement;
import com.intellij.navigation.ItemPresentation;
import com.intellij.psi.NavigatablePsiElement;
import org.jetbrains.annotations.NotNull;

/**
 * IoAbStructureViewElement
 *
 * @author senpure
 * @time 2019-06-18 20:24:32
 */
public abstract class IoAbstractView implements StructureViewTreeElement, SortableTreeElement {
    protected NavigatablePsiElement element;

    public IoAbstractView(NavigatablePsiElement element) {
        this.element = element;
    }

    @Override
    public Object getValue() {
        return element;
    }


    @NotNull
    @Override
    public String getAlphaSortKey() {

        String name = element.getName();
        return name != null ? name : "";
    }

    @NotNull
    @Override
    public ItemPresentation getPresentation() {
        ItemPresentation presentation = element.getPresentation();
        return presentation != null ? presentation : new PresentationData();
    }


    @Override
    public void navigate(boolean requestFocus) {

        element.navigate(requestFocus);
    }

    @Override
    public boolean canNavigate() {
        return element.canNavigate();
    }

    @Override
    public boolean canNavigateToSource() {
        return element.canNavigateToSource();
    }
}
