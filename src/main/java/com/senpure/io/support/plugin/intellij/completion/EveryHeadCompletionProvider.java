package com.senpure.io.support.plugin.intellij.completion;

import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.util.ProcessingContext;
import com.senpure.io.support.plugin.intellij.psi.IoTypes;
import org.jetbrains.annotations.NotNull;

/**
 * EveryHeadCompletion
 *
 * @author senpure
 * @time 2019-05-31 17:13:38
 */
public class EveryHeadCompletionProvider extends CompletionProvider {

    public static void reg(CompletionContributor contributor) {

        contributor.extend(CompletionType.BASIC, PlatformPatterns.psiElement(IoTypes.T_IDENTIFIER), new EveryHeadCompletionProvider());
        //contributor.extend(CompletionType.BASIC,  PlatformPatterns.psiElement(GeneratedParserUtilBase.DUMMY_BLOCK),new EveryHeadCompletionProvider());
    }

    @Override
    protected void addCompletions(@NotNull CompletionParameters parameters, @NotNull ProcessingContext context, @NotNull CompletionResultSet result) {

//        PsiElement psiElement = parameters.getPosition().getParent().getParent();
//        ASTNode head = psiElement.getNode().findChildByType(IoTypes.HEAD);
//        if (head != null) {
//
//        }

        result.addElement(LookupElementBuilder.create("message"));
        result.addElement(LookupElementBuilder.create("bean"));
        result.addElement(LookupElementBuilder.create("enum"));
        result.addElement(LookupElementBuilder.create("event"));
        result.addElement(LookupElementBuilder.create("namespace"));
        result.addElement(LookupElementBuilder.create("import"));
        result.addElement(LookupElementBuilder.create("javaPack"));
        result.addElement(LookupElementBuilder.create("luaNamespace"));
    }
}
