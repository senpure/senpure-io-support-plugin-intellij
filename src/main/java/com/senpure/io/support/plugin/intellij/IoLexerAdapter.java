package com.senpure.io.support.plugin.intellij;

import com.intellij.lexer.FlexAdapter;
import com.senpure.io.support.plugin.intellij.lexer._IoSyntaxHighlighterLexer;

/**
 * IoLexerAdapter
 *
 * @author senpure
 * @time 2019-05-23 11:14:36
 */
public class IoLexerAdapter extends FlexAdapter {
    public IoLexerAdapter() {
        //super(new _IoLexer(null));
        this(1);
    }
    public IoLexerAdapter(int type) {
        super(new _IoSyntaxHighlighterLexer(null));
    }
}
