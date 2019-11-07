package com.senpure.io.support.plugin.intellij.completion;

import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.util.ProcessingContext;
import com.senpure.io.generator.reader.IoProtocolReader;
import com.senpure.io.generator.reader.IoReader;
import com.senpure.io.support.plugin.intellij.IoIcons;
import com.senpure.io.support.plugin.intellij.psi.IoTypes;
import com.senpure.io.support.plugin.intellij.util.IoUtil;
import org.jetbrains.annotations.NotNull;

/**
 * ImportCompletionProvider
 *
 * @author senpure
 * @time 2019-11-04 14:34:52
 */
public class ImportCompletionProvider extends CompletionProvider {
    public static void reg(CompletionContributor contributor) {

        // contributor.extend(CompletionType.BASIC, PlatformPatterns.psiElement(TokenType.BAD_CHARACTER), new ImportCompletionProvider());
        contributor.extend(CompletionType.BASIC, PlatformPatterns.psiElement(IoTypes.T_IMPORT_VALUE), new ImportCompletionProvider());
    }

    @Override
    protected void addCompletions(@NotNull CompletionParameters parameters, @NotNull ProcessingContext context, @NotNull CompletionResultSet result) {
        String path = IoUtil.getFilePath(parameters.getPosition());
        for (IoProtocolReader value : IoReader.getInstance().getIoProtocolReaderMap().values()) {
//            if (value.getBeans().size() + value.getEnums().size() == 0) {
//                continue;
//            }
            if (value.getFilePath().length() > 0 && !value.getFilePath().equals(path)) {
                String str = IoUtil.getRelativePath(path, value.getFilePath());
                if (str.length() > 0) {
                    result.addElement(LookupElementBuilder.create(parameters.getPosition(), str)
                            .withIcon(IoIcons.FILE)
                            .withInsertHandler((context1, item) -> {
                                if (IoUtil.getNexEffectiveSibling(parameters.getPosition().getParent().getNode()) == null) {
                                    // parameters.getEditor().
                                    int offset = parameters.getPosition().getTextOffset() + str.length();
                                    parameters.getEditor().getDocument().insertString(offset, ";");
                                    parameters.getEditor().getCaretModel().moveToOffset(offset + 1);
                                }

                            })
                    );
                }

            }
        }
//        ASTNode pre = IoUtil.getPreEffectiveSibling(parameters.getPosition().getParent().getNode(), 2);
//      //  System.out.println(pre);
//        if (pre != null) {
//            if (pre.getElementType().equals(IoTypes.HEAD)||IoTypes.T_IMPORT_HEAD.equals(pre.getElementType())) {
//
//            }
//        }

    }


}
