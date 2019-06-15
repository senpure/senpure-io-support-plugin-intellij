package com.senpure.io.support.plugin.intellij.psi;

import com.intellij.psi.tree.IElementType;
import com.senpure.io.support.plugin.intellij.IoLanguage;
import org.jetbrains.annotations.NotNull;

/**
 * IoTokenType
 *
 * @author senpure
 * @time 2019-05-22 17:44:39
 */
public class IoTokenType extends IElementType {
    public IoTokenType(@NotNull String debugName) {
        super(debugName, IoLanguage.INSTANCE);
    }
    @Override
    public String toString() {
        return "IoTokenType." + super.toString();
    }
}
