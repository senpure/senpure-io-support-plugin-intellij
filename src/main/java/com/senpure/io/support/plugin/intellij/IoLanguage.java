package com.senpure.io.support.plugin.intellij;

import com.intellij.lang.Language;

/**
 * IoLanguage
 *
 * @author senpure
 * @time 2019-05-21 19:55:44
 */
public class IoLanguage extends Language {


    public static final IoLanguage INSTANCE = new IoLanguage();

    private  IoLanguage() {
        super("Io");
    }
}
