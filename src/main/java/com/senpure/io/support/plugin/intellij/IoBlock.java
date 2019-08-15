package com.senpure.io.support.plugin.intellij;

import com.intellij.formatting.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.TokenType;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import com.intellij.psi.formatter.common.AbstractBlock;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import com.senpure.io.support.plugin.intellij.psi.IoEnumField;
import com.senpure.io.support.plugin.intellij.psi.IoField;
import com.senpure.io.support.plugin.intellij.psi.IoFieldIndex;
import com.senpure.io.support.plugin.intellij.psi.IoTypes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * IoBlock
 *
 * @author senpure
 * @time 2019-05-27 10:35:42
 */
public class IoBlock extends AbstractBlock {
    private Logger logger = LoggerFactory.getLogger(IoBlock.class);
    private final static Indent NONE_INDENT = Indent.getNoneIndent();
    private final static Indent DIRECT_NORMAL_INDENT = Indent.getNormalIndent(true);
    private final static Indent SAME_AS_PARENT_INDENT = Indent.getSpaceIndent(0, true);
    private SpacingBuilder spacingBuilder;
    private Indent indent;
    private Indent childIndent;
    private CodeStyleSettings settings;
    private static TokenSet ENTITY = TokenSet.create(IoTypes.MESSAGE, IoTypes.BEAN, IoTypes.ENUM, IoTypes.EVENT);
    private static TokenSet FIELDS = TokenSet.create(IoTypes.FIELD, IoTypes.ENUM_FIELD);
    private static TokenSet EXACT_HEADS = TokenSet.create(IoTypes.IMPORT_HEAD, IoTypes.NAMESPACE_HEAD,
            IoTypes.JAVA_PACKAGE_HEAD, IoTypes.LUA_NAMESPACE_HEAD);
    private static TokenSet HEAD_CONTENT = TokenSet.create(IoTypes.HEAD_CONTENT);
    private static TokenSet EXACT_HEAD_CONTENTS = TokenSet.create(
            IoTypes.IMPORT, IoTypes.NAMESPACE,
            IoTypes.JAVA_PACKAGE, IoTypes.LUA_NAMESPACE);
    protected IoBlock(@NotNull ASTNode node, @Nullable Wrap wrap, CodeStyleSettings settings, SpacingBuilder spacingBuilder) {
        super(node, wrap, computeAlignment(node));
        // this.spacingBuilder = spacingBuilder;
        this.indent = computeIndent(node);
        this.childIndent = computeChildIndent(node);
        this.settings = settings;
        this.spacingBuilder = computeSpacingBuilder();

    }

    private static Alignment fieldAlignment = Alignment.createAlignment(true);
    private static Alignment enumFieldAlignment = Alignment.createAlignment(true);

    @Nullable
    private SpacingBuilder computeSpacingBuilder() {
        SpacingBuilder spacingBuilder = null;

        if (myNode.getElementType().equals(IoTypes.FIELD)) {
            ASTNode entity = myNode.getTreeParent();
            ASTNode[] astNodes = entity.getChildren(field);
            IoField myField = (IoField) myNode.getPsi();
            FieldModel maxModel = new FieldModel();
            FieldModel myModel = null;
            for (ASTNode astNode : astNodes) {
                IoField ioField = (IoField) astNode.getPsi();
                FieldModel tempModel = new FieldModel();
                int fieldTypeLen = ioField.getFieldType().getTextLength();
                tempModel.fieldTypeLen = fieldTypeLen;
                tempModel.fieldTypeAndList = fieldTypeLen;
                if (fieldTypeLen > maxModel.fieldTypeLen) {
                    maxModel.fieldTypeLen = fieldTypeLen;
                }
                boolean list = ioField.getFieldList() != null;
                tempModel.list = list;
                if (list) {
                    maxModel.list = true;
                }
                if (tempModel.fieldTypeAndList > maxModel.fieldTypeAndList) {
                    maxModel.fieldTypeAndList = tempModel.fieldTypeAndList;
                }
                int fieldNameLen = ioField.getFieldName().getTextLength();
                tempModel.fieldNameLen = fieldNameLen;
                if (fieldNameLen > maxModel.fieldNameLen) {
                    maxModel.fieldNameLen = fieldNameLen;
                }
                IoFieldIndex fieldIndex = ioField.getFieldIndex();
                if (fieldIndex != null) {
                    maxModel.index = true;
                    tempModel.index = true;
                    int fieldIndexLen = fieldIndex.getTextLength();
                    tempModel.indexValueLen = fieldIndexLen;
                    if (fieldIndexLen > maxModel.indexValueLen) {
                        maxModel.indexValueLen = fieldIndexLen;
                    }
                }
                if (myField == ioField) {
                    myModel = tempModel;
                }
                //IoField ioField = (IoField) astNode.getElementType();
            }

            spacingBuilder = spacingBuilder(maxModel, myModel);

        } else if (myNode.getElementType().equals(IoTypes.ENUM_FIELD)) {
            spacingBuilder = enumFieldSpacingBuilder();

        } else if (EXACT_HEAD_CONTENTS.contains(myNode.getElementType())) {

           // logger.debug("{}", myNode.getText());
            ASTNode myChild = myNode.getFirstChildNode();
            int myLen = myChild.getText().length();
            ASTNode entity = myNode.getTreeParent().getTreeParent();
            //logger.debug("entity {}", entity.getText());
            ASTNode[] astNodes = entity.getChildren(HEAD_CONTENT);

            int maxLen = 0;
            for (ASTNode astNode : astNodes) {
               // logger.debug("EXACT {}", astNode.getText());
                ASTNode exactHead = astNode.getFirstChildNode().getFirstChildNode();
                int len = exactHead.getText().length();
                maxLen = len > maxLen ? len : maxLen;
            }
            //logger.debug("max {}  my {} ,type {}", maxLen, myLen, myChild.getElementType());
            spacingBuilder = new SpacingBuilder(settings, IoLanguage.INSTANCE);
           spacingBuilder.after(myChild.getElementType()).spacing(maxLen - myLen + 1, maxLen - myLen + 1, 0, false, 0);
           // spacingBuilder.after(IoTypes.NAMESPACE_HEAD).spacing(2, 2, 0, false, 0);
        }

        if (spacingBuilder == null) {
            spacingBuilder = IoFormattingModelBuilder.normalSpacingBuilder;
        }
        return spacingBuilder;
    }

    @Nullable
    private static Alignment computeAlignment(@NotNull ASTNode node) {
        IElementType type = node.getElementType();
        if (type.equals(IoTypes.FIELD)) {
            return fieldAlignment;
        } else if (type.equals(IoTypes.ENUM_FIELD)) {
            return enumFieldAlignment;
        }
        return null;
    }

    private static Indent computeChildIndent(ASTNode node) {
        IElementType type = node.getElementType();
        //  IElementType parent = node.getElementType();
        if (ENTITY.contains(type)) {
            return DIRECT_NORMAL_INDENT;
        } else if (FIELDS.contains(type)) {
            return DIRECT_NORMAL_INDENT;
        }
        return SAME_AS_PARENT_INDENT;
    }


    private static Indent computeIndent(ASTNode node) {
        IElementType type = node.getElementType();
        // IElementType parent = node.getElementType();
        if (ENTITY.contains(type)) {
            return NONE_INDENT;
        } else if (FIELDS.contains(type)) {
            return DIRECT_NORMAL_INDENT;
        }
        return SAME_AS_PARENT_INDENT;
    }

    @Override
    protected List<Block> buildChildren() {
        List<Block> blocks = new ArrayList<>();
        ASTNode child = myNode.getFirstChildNode();
        // int count = 1;
        while (child != null) {
            //System.out.println(count++ + "  block " + child.getText() + "  " + child.toString() + " parent " + child.getTreeParent());
            if (child.getElementType() != TokenType.WHITE_SPACE) {
                Block block;
                block = new IoBlock(child, Wrap.createWrap(WrapType.NONE, false), settings, spacingBuilder);

                blocks.add(block);
            }
            child = child.getTreeNext();
        }
        return blocks;
    }

    private static TokenSet field = TokenSet.create(IoTypes.FIELD);
    private static TokenSet enumField = TokenSet.create(IoTypes.ENUM_FIELD);
    private static TokenSet headContent = TokenSet.create(IoTypes.HEAD_CONTENT);

    @Nullable
    @Override
    public Spacing getSpacing(@Nullable Block child1, @NotNull Block child2) {
        Spacing spacing = spacingBuilder.getSpacing(this, child1, child2);
        return spacing;
    }

    private SpacingBuilder enumFieldSpacingBuilder() {
        ASTNode entity = myNode.getTreeParent();
        ASTNode[] astNodes = entity.getChildren(enumField);
        IoEnumField myField = (IoEnumField) myNode.getPsi();
        FieldModel maxModel = new FieldModel();
        FieldModel myModel = null;
        for (ASTNode astNode : astNodes) {
            IoEnumField ioEnumField = (IoEnumField) astNode.getPsi();
            FieldModel tempModel = new FieldModel();
            int fieldNameLen = ioEnumField.getFieldName().getTextLength();
            tempModel.fieldNameLen = fieldNameLen;
            if (fieldNameLen > maxModel.fieldNameLen) {
                maxModel.fieldNameLen = fieldNameLen;
            }
            IoFieldIndex fieldIndex = ioEnumField.getFieldIndex();
            if (fieldIndex != null) {
                tempModel.index = true;
                int fieldIndexLen = fieldIndex.getTextLength();
                maxModel.index = true;
                tempModel.indexValueLen = fieldIndexLen;
                if (fieldIndexLen > maxModel.indexValueLen) {
                    maxModel.indexValueLen = fieldIndexLen;
                }
            }
            if (myField == ioEnumField) {
                myModel = tempModel;
            }
        }

        //抵消一个空格
        maxModel.fieldTypeLen = -1;
        myModel.fieldTypeLen = -1;
        return spacingBuilder(maxModel, myModel);
    }


    // int       [] type = 1;            //注释
    // FieldType    j    = 23;           //8888
    // sint         k;                   //8888
    // sint         k22;                 //8888
    private SpacingBuilder spacingBuilder(FieldModel maxModel, FieldModel myModel) {
        SpacingBuilder spacingBuilder = new SpacingBuilder(settings, IoLanguage.INSTANCE);
        int len;
        len = maxModel.fieldTypeLen - myModel.fieldTypeLen + 1;
        if (maxModel.list) {
            if (!myModel.list) {
                //补齐 "[] "
                len += 3;
            }
        }
        spacingBuilder.after(IoTypes.FIELD_TYPE).spacing(len, len, 0, false, 0);
        spacingBuilder
                .after(IoTypes.FIELD_LIST).lineBreakOrForceSpace(false, true)
                .after(IoTypes.T_LEFT_BRACKET).lineBreakOrForceSpace(false, false)
        ;
        //"fieldName = 1;"
        if (maxModel.index) {
            if (myModel.index) {
                len = maxModel.fieldNameLen - myModel.fieldNameLen + 1;
                spacingBuilder.after(IoTypes.FIELD_NAME).spacing(len, len, 0, false, 0);
                spacingBuilder.after(IoTypes.EQUAL).spacing(1, 1, 0, false, 0);
                spacingBuilder.after(IoTypes.FIELD_INDEX).spacing(0, 0, 0, false, 0);
            } else {
                spacingBuilder.after(IoTypes.FIELD_NAME).spacing(0, 0, 0, false, 0);
            }
        }
        //"type fieldName;"
        //"type fieldName2;"
        else {
            spacingBuilder.after(IoTypes.FIELD_NAME).spacing(0, 0, 0, false, 0);
        }
        // type [] name
        int num = 36;
        if (maxModel.index) {
            int maxLen = maxModel.fieldTypeLen + 1 + (maxModel.list ? 3 : 0) + maxModel.fieldNameLen;
            int maxHead = maxLen;
            maxLen += 3 + maxModel.indexValueLen + 1;
            if (maxLen >= num) {
                num = maxLen + 1;
            }
            //" = "+"index;"
            if (myModel.index) {
                int myLen = maxHead + 3 + myModel.indexValueLen + 1;
                len = num - myLen;
                spacingBuilder.after(IoTypes.SEMICOLON).spacing(len, len, 0, false, 0);
            } else {
                len = num - maxLen + maxModel.fieldNameLen - myModel.fieldNameLen + 3 + maxModel.indexValueLen;
                spacingBuilder.after(IoTypes.SEMICOLON).spacing(len, len, 0, false, 0);
            }

        } else {
            int maxLen = maxModel.fieldTypeLen + 1 + (maxModel.list ? 3 : 0);
            int myLen = maxLen + myModel.fieldNameLen + 1;
            maxLen += maxModel.fieldNameLen + 1;
            if (maxLen >= num) {
                num = maxLen + 1;
            }
            len = num - myLen;
            spacingBuilder.after(IoTypes.SEMICOLON).spacing(len, len, 0, false, 0);
        }

        return spacingBuilder;
    }


    @Override
    public Indent getIndent() {
        return indent;
    }

    @Nullable
    @Override
    protected Indent getChildIndent() {
        return childIndent;
    }

    @Override
    public boolean isLeaf() {
        return myNode.getFirstChildNode() == null;
    }

    @Override
    public boolean isIncomplete() {
//        IElementType psiElement =
//                myNode.getElementType();
//        if (psiElement.equals(GeneratedParserUtilBase.DUMMY_BLOCK)) {
//
//            // System.out.println("---------dummy");
//            // return false;
//        }
        return super.isIncomplete();
    }

    class FieldModel {
        int fieldTypeLen = 0;
        int fieldTypeAndList = 0;
        boolean list;
        int fieldNameLen = 0;
        boolean index;
        int indexValueLen = 0;


    }
}
