package com.senpure.io.support.plugin.intellij.reference;

import com.intellij.openapi.util.TextRange;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.*;
import com.intellij.util.ProcessingContext;
import com.senpure.io.support.plugin.intellij.psi.IoFieldType;
import org.jetbrains.annotations.NotNull;

/**
 * IoReferenceContributor
 *
 * @author senpure
 * @time 2019-06-14 15:09:16
 */
public class IoReferenceContributor extends PsiReferenceContributor {
   // Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void registerReferenceProviders(@NotNull PsiReferenceRegistrar registrar) {

       // logger.debug("register {}", registrar.getClass());

        registrar.registerReferenceProvider(PlatformPatterns.psiElement(IoFieldType.class),
                new PsiReferenceProvider() {
                    @NotNull
                    @Override
                    public PsiReference[] getReferencesByElement(@NotNull PsiElement element,
                                                                 @NotNull ProcessingContext context) {
                        //logger.debug("{}  ->  {}", element, element.getText());
//                        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
//                        for (StackTraceElement traceElement : stackTraceElements) {
//
//                            //logger.debug(traceElement.toString());
//                        }
                        //logger.debug("-----------------");
//                        if (element instanceof PsiLiteralExpression) {
//                            PsiLiteralExpression literalExpression = (PsiLiteralExpression) element;
//                            String value = literalExpression.getValue() instanceof String ?
//                                    (String) literalExpression.getValue() : null;
//                            if (value != null && value.startsWith("io" + ":")) {
//                                return new PsiReference[]{
//                                        new IoReference(element, new TextRange(4, value.length() + 1))};
//                            }
//                        }
                            IoFieldType ioFieldType = (IoFieldType) element;
                            if (ioFieldType.getTFieldTypeBase() == null && ioFieldType.getTextLength() > 0) {
                                return new PsiReference[]{
                                        new IoReference(element, new TextRange(0, ioFieldType.getTextLength()))};
                            }

                        return PsiReference.EMPTY_ARRAY;
                    }
                });
    }
}

