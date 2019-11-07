package com.senpure.io.support.plugin.intellij;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.psi.PsiElement;
import com.intellij.util.ProcessingContext;
import com.senpure.io.support.plugin.intellij.completion.*;
import com.senpure.io.support.plugin.intellij.psi.IoField;
import com.senpure.io.support.plugin.intellij.psi.IoFieldType;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * IoCompletionContributor
 *
 * @author senpure
 * @time 2019-05-24 17:46:12
 */
public class IoCompletionContributor extends CompletionContributor {
    private Logger logger = LoggerFactory.getLogger(getClass());

    //token 有效
    public IoCompletionContributor() {
        EveryHeadCompletionProvider.reg(this);
        MessageCompletionProvider.reg(this);
        FieldCompletionProvider.reg(this);
        BeanCompletionProvider.reg(this);
        EnumCompletionProvider.reg(this);
        EventCompletionProvider.reg(this);
        ImportCompletionProvider.reg(this);
    }


    @Override
    public void fillCompletionVariants(@NotNull CompletionParameters parameters, @NotNull CompletionResultSet result) {
        logger.debug("position {} parent {}", parameters.getPosition(), parameters.getPosition().getParent());
        super.fillCompletionVariants(parameters, result);
    }


    class FieldTypeCompletionProvider2 extends CompletionProvider {
        String[] base = new String[]{"int", "long", "sint", "slong", "sfixed32", "sfixed64", "float", "double", "boolean", "String"};

        @Override
        protected void addCompletions(@NotNull CompletionParameters parameters, @NotNull ProcessingContext context, @NotNull CompletionResultSet result) {
            for (String s : base) {
                result.addElement(LookupElementBuilder.create(s));
            }

        }
    }

    class FieldNameCompletionProvider extends CompletionProvider {

        @Override
        protected void addCompletions(@NotNull CompletionParameters parameters, @NotNull ProcessingContext context, @NotNull CompletionResultSet result) {
            System.out.println(parameters.getPosition() + "  " + parameters.getPosition().getParent());
            PsiElement psiElement = parameters.getPosition().getParent();
            if (psiElement instanceof IoField) {
                IoField ioField = (IoField) psiElement;
                IoFieldType fieldType = ioField.getFieldType();

                if (ioField.getFieldType().getTFieldTypeBase() == null) {
                    if (ioField.getFieldList() != null) {
                        result.addElement(LookupElementBuilder.create(fieldType.getText() + "s"));
                    } else {
                        result.addElement(LookupElementBuilder.create(fieldType.getText()));
                    }
                }

            }

        }
    }

    class MessageTypeCompletionProvider extends CompletionProvider {

        @Override
        protected void addCompletions(@NotNull CompletionParameters parameters, @NotNull ProcessingContext context, @NotNull CompletionResultSet result) {
            result.addElement(LookupElementBuilder.create("cs"));
            result.addElement(LookupElementBuilder.create("sc"));
        }
    }

    class MessageIdCompletionProvider extends CompletionProvider {

        @Override
        protected void addCompletions(@NotNull CompletionParameters parameters, @NotNull ProcessingContext context, @NotNull CompletionResultSet result) {
            String text = parameters.getPosition().getText().replace("IntellijIdeaRulezzz", "");

            result.addElement(LookupElementBuilder.create(text + " {\n}"));
        }
    }

    class MessageNameCompletionProvider extends CompletionProvider {

        @Override
        protected void addCompletions(@NotNull CompletionParameters parameters, @NotNull ProcessingContext context, @NotNull CompletionResultSet result) {

            String text = parameters.getPosition().getText().replace("IntellijIdeaRulezzz", "");

            result.addElement(LookupElementBuilder.create(text + " "));
        }
    }

    public static void main(String[] args) {

    }
}
