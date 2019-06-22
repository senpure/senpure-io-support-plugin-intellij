package com.senpure.io.support.plugin.intellij;

import com.intellij.lang.ASTNode;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiParser;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;
import com.senpure.io.support.plugin.intellij.parser.IoParser;
import com.senpure.io.support.plugin.intellij.psi.IoFile;
import com.senpure.io.support.plugin.intellij.psi.IoTypes;
import org.jetbrains.annotations.NotNull;

/**
 * IoParserDefinition
 *
 * @author senpure
 * @time 2019-05-23 11:27:07
 */
public class IoParserDefinition implements ParserDefinition {
    public static final IFileElementType FILE = new IFileElementType(IoLanguage.INSTANCE);
    @NotNull
    @Override
    public Lexer createLexer(Project project) {
        return new IoLexerAdapter();
    }

    @Override
    public PsiParser createParser(Project project) {
        return new IoParser();
    }

    @Override
    public IFileElementType getFileNodeType() {
        return FILE;
    }

    @NotNull
    @Override
    public TokenSet getCommentTokens() {
        return TokenSet.create(IoTypes.T_CODE_COMMENT);
    }

    @NotNull
    @Override
    public TokenSet getStringLiteralElements() {
        return TokenSet.EMPTY;
    }

    @NotNull
    @Override
    public PsiElement createElement(ASTNode node) {
        return IoTypes.Factory.createElement(node);
    }

    @Override
    public PsiFile createFile(FileViewProvider viewProvider) {
        return new IoFile(viewProvider);
    }
}
