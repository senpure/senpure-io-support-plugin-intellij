package com.senpure.io.support.plugin.intellij.structure;

import com.intellij.ide.util.treeView.smartTree.TreeElement;
import com.senpure.io.support.plugin.intellij.psi.impl.IoBeanImpl;
import org.jetbrains.annotations.NotNull;

/**
 * IoBeanView
 *
 * @author senpure
 * @time 2019-06-18 20:37:26
 */
public class IoBeanView extends IoAbstractView {

private  IoBeanImpl bean;
    public IoBeanView(IoBeanImpl element) {
        super(element);

    }



    @NotNull
    @Override
    public TreeElement[] getChildren() {

        return EMPTY_ARRAY;
    }



}
