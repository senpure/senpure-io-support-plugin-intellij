package com.senpure.io.support.plugin.intellij;

import com.intellij.openapi.fileTypes.LanguageFileType;
import com.intellij.openapi.util.IconLoader;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * IoFileType
 *
 * @author senpure
 * @time 2019-05-21 19:57:12
 */
public class IoFileType extends LanguageFileType {
    public static final Icon FILE = IconLoader.getIcon("/icon.png");
    public static final IoFileType INSTANCE = new IoFileType();
    private IoFileType() {
        super(IoLanguage.INSTANCE);

    }

    @NotNull
    @Override
    public String getName() {
        return "Io file";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "Io language file ";
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return "io";
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return FILE;
    }

    public static void main(String[] args) {

        System.out.println(FILE);
    }
}
