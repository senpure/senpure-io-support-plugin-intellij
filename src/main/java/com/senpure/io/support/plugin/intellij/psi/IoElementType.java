package com.senpure.io.support.plugin.intellij.psi;

import com.intellij.psi.tree.IElementType;
import com.senpure.io.support.plugin.intellij.IoLanguage;
import org.jetbrains.annotations.NotNull;

/**
 * IoElementType
 *
 * @author senpure
 * @time 2019-05-22 15:26:37
 */
public class IoElementType extends IElementType {

    public IoElementType(@NotNull String debugName) {
        super(debugName, IoLanguage.INSTANCE);
    }
}
