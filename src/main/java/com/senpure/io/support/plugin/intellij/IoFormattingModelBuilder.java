package com.senpure.io.support.plugin.intellij;

import com.intellij.formatting.*;
import com.intellij.lang.ASTNode;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import com.senpure.io.support.plugin.intellij.psi.IoTypes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * IoFormattingModelBuilder
 *
 * @author senpure
 * @time 2019-05-27 10:29:08
 */
public class IoFormattingModelBuilder implements FormattingModelBuilder {
    @NotNull
    @Override
    public FormattingModel createModel(PsiElement element, CodeStyleSettings settings) {

        normalSpacingBuilder = createSpaceBuilder(settings);
        return FormattingModelProvider
                .createFormattingModelForPsiFile(element.getContainingFile(),
                        new IoBlock(element.getNode(),
                                Wrap.createWrap(WrapType.NONE, false), settings, createSpaceBuilder(settings)
                        ),
                        settings);

    }

    private static SpacingBuilder createSpaceBuilder2(CodeStyleSettings settings) {
        return new SpacingBuilder(settings, IoLanguage.INSTANCE)
                //.aroundInside()
                // .around(IoTypes.SEMICOLON)
                //.spaceIf(settings.getCommonSettings(IoLanguage.INSTANCE.getID()).SPACE_AROUND_ASSIGNMENT_OPERATORS)
                // .before(IoTypes.FIELD_COMMENT)
                // .none()
                ;
    }

    public static SpacingBuilder normalSpacingBuilder;

    public static SpacingBuilder createSpaceBuilder(CodeStyleSettings settings) {
        return new SpacingBuilder(settings, IoLanguage.INSTANCE)

                .after(IoTypes.NAMESPACE_HEAD).spacing(1, 1, 0, false, 0)
                .after(IoTypes.IMPORT_HEAD).spacing(4, 4, 0, false, 0)
                .after(IoTypes.JAVA_PACK_HEAD).spacing(2, 2, 0, false, 0)
                .after(IoTypes.JAVA_PACK_VALUE).lineBreakOrForceSpace(false, false)
                .after(IoTypes.IMPORT_VALUE).lineBreakOrForceSpace(false, false)
                .after(IoTypes.NAMESPACE_VALUE).lineBreakOrForceSpace(false, false)
                .after(IoTypes.HEAD_CONTENT).spacing(0, 0, 0, true, 0)
                // .aroundInside(IoTypes.HEAD, IoParserDefinition.FILE).spacing(0, 0, 3, true, 0)
                //.before(IoTypes.HEAD).spacing(0, 0, 2, true, 0)
                .after(IoTypes.HEAD).spacing(0, 0, 2, true, 0)
                .after(IoTypes.T_LINE_COMMENT).lineBreakOrForceSpace(true, true)
                .after(IoTypes.MESSAGE_HEAD).lineBreakOrForceSpace(false, true)
                .after(IoTypes.MESSAGE_TYPE).lineBreakOrForceSpace(false, true)
                .after(IoTypes.MESSAGE_NAME).lineBreakOrForceSpace(false, true)
                .after(IoTypes.MESSAGE_ID).lineBreakOrForceSpace(false, true)
                .after(IoTypes.BEAN_HEAD).lineBreakOrForceSpace(false, true)
                .after(IoTypes.BEAN_NAME).lineBreakOrForceSpace(false, true)
                .after(IoTypes.EVENT_HEAD).lineBreakOrForceSpace(false, true)
                .after(IoTypes.EVENT_NAME).lineBreakOrForceSpace(false, true)
                .after(IoTypes.EVENT_ID).lineBreakOrForceSpace(false, true)
                .after(IoTypes.ENUM_HEAD).lineBreakOrForceSpace(false, true)
                .after(IoTypes.ENUM_NAME).lineBreakOrForceSpace(false, true)
                .after(IoTypes.LEFT_BRACE).spacing(0, 0, 0, true, 0)
                .after(IoTypes.FIELD).spacing(0, 0, 0, true, 0)
                .after(IoTypes.ENUM_FIELD).spacing(0, 0, 0, true, 0)
//                .before(IoTypes.FIELD_NAME).lineBreakOrForceSpace(false, true)
//                .before(IoTypes.FIELD_LIST).lineBreakOrForceSpace(false, false)
//                .after(IoTypes.FIELD_LIST).lineBreakOrForceSpace(false, true)
//                .after(IoTypes.T_LEFT_BRACKET).lineBreakOrForceSpace(false, false)
//                .around(IoTypes.EQUAL).lineBreakOrForceSpace(false, true)
//                .before(IoTypes.SEMICOLON).lineBreakOrForceSpace(false, false)
//                .before(IoTypes.FIELD_COMMENT).lineBreakOrForceSpace(false, true)
                .after(IoTypes.RIGHT_BRACE).lineBreakOrForceSpace(true, false)
                //.before(IoTypes.ENTITY).spacing(0, 0, 2, true, 0)
                .after(IoTypes.ENTITY).spacing(0, 0, 2, true, 0)
                ;
    }

    @Nullable
    @Override
    public TextRange getRangeAffectingIndent(PsiFile file, int offset, ASTNode elementAtOffset) {
        return null;
    }
}
