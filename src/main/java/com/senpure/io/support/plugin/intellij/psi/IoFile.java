package com.senpure.io.support.plugin.intellij.psi;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import com.senpure.io.support.plugin.intellij.IoFileType;
import com.senpure.io.support.plugin.intellij.IoLanguage;
import org.jetbrains.annotations.NotNull;

/**
 * IoFile
 *
 * @author senpure
 * @time 2019-05-23 11:17:18
 */
public class IoFile extends PsiFileBase {
    public IoFile(@NotNull FileViewProvider viewProvider) {
        super(viewProvider, IoLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public FileType getFileType() {

        return IoFileType.INSTANCE;
    }

    @Override
    public String toString() {
        return "Io File";
    }


}
