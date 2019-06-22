package com.senpure.io.support.plugin.intellij.structure;

import com.intellij.ide.util.treeView.smartTree.TreeElement;
import com.senpure.io.support.plugin.intellij.psi.impl.IoFieldImpl;
import org.jetbrains.annotations.NotNull;

/**
 * IoFieldView
 *
 * @author senpure
 * @time 2019-06-18 20:23:25
 */
public class IoFieldView extends IoAbstractView {


    private IoFieldImpl field;

    public IoFieldView(IoFieldImpl element) {
        super(element);
        this.field = element;
    }

    @NotNull
    @Override
    public TreeElement[] getChildren() {
        return EMPTY_ARRAY;
    }
}
