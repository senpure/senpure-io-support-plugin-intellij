package com.senpure.io.support.plugin.intellij.completion;

import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.project.Project;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.tree.IElementType;
import com.intellij.util.ProcessingContext;
import com.senpure.io.generator.model.Bean;
import com.senpure.io.generator.reader.IoProtocolReader;
import com.senpure.io.generator.reader.IoReader;
import com.senpure.io.generator.util.ProtocolUtil;
import com.senpure.io.support.plugin.intellij.IoIcons;
import com.senpure.io.support.plugin.intellij.psi.IoTypes;
import com.senpure.io.support.plugin.intellij.util.IoUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * FieldTypeCompletionProvider
 *
 * @author senpure
 * @time 2019-11-16 13:33:35
 */
public class FieldTypeCompletionProvider extends CompletionProvider {
    public static void reg(CompletionContributor contributor) {

        contributor.extend(CompletionType.BASIC, PlatformPatterns.psiElement(IoTypes.T_FIELD_TYPE_QUOTE), new FieldTypeCompletionProvider());

    }

    private List<Bean> getBeans(Project project) {
        List<Bean> beans = new ArrayList<>();
        for (IoProtocolReader value : IoReader.getInstance(project.getBasePath()).getIoProtocolReaderMap().values()) {

            beans.addAll(value.getBeans());
            beans.addAll(value.getEnums());
        }

        return beans;
    }
    @Override
    protected void addCompletions(@NotNull CompletionParameters parameters, @NotNull ProcessingContext context, @NotNull CompletionResultSet result) {
        IElementType elementType = IoUtil.getPreEffectiveSiblingElementType(parameters.getPosition().getNode());
        if (elementType != null) {
            if (elementType.equals(IoTypes.T_LEFT_BRACE)
                    || elementType.equals(IoTypes.T_SEMICOLON)
                    || elementType.equals(IoTypes.T_FIELD_COMMENT)) {

                for (String s : ProtocolUtil.baseFields) {
                    result.addElement(LookupElementBuilder.create(s));
                }
                List<Bean> beans = getBeans(parameters.getPosition().getProject());

                for (Bean bean : beans) {
                    result.addElement(LookupElementBuilder.create(bean, bean.getName())
                            .withTailText(" (" + bean.getNamespace() + ")", true)
                            .withIcon(IoIcons.FILE)
                    );
                }

            }
        }
    }
}
