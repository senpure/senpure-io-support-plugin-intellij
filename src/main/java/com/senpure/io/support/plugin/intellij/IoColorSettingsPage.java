package com.senpure.io.support.plugin.intellij;

import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.options.colors.AttributesDescriptor;
import com.intellij.openapi.options.colors.ColorDescriptor;
import com.intellij.openapi.options.colors.ColorSettingsPage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Map;

/**
 * IoColorSettingsPage
 *
 * @author senpure
 * @time 2019-05-23 14:48:19
 */
public class IoColorSettingsPage implements ColorSettingsPage {

    private static final AttributesDescriptor[] DESCRIPTORS = new AttributesDescriptor[]{
            new AttributesDescriptor("IO_BEAN_HEAD", IoSyntaxHighlighter.BEAN_HEAD),
            new AttributesDescriptor("IO_BEAN_NAME", IoSyntaxHighlighter.BEAN_NAME),
            new AttributesDescriptor("IO_DIGIT", IoSyntaxHighlighter.DIGIT),
            new AttributesDescriptor("IO_ENUM_HEAD", IoSyntaxHighlighter.ENUM_HEAD),
            new AttributesDescriptor("IO_ENUM_NAME", IoSyntaxHighlighter.ENUM_NAME),
            new AttributesDescriptor("IO_EQUAL", IoSyntaxHighlighter.EQUAL),
            new AttributesDescriptor("IO_EVENT_HEAD", IoSyntaxHighlighter.EVENT_HEAD),
            new AttributesDescriptor("IO_EVENT_ID", IoSyntaxHighlighter.EVENT_ID),
            new AttributesDescriptor("IO_EVENT_NAME", IoSyntaxHighlighter.EVENT_NAME),
            new AttributesDescriptor("IO_FIELD_COMMENT", IoSyntaxHighlighter.FIELD_COMMENT),
            new AttributesDescriptor("IO_FIELD_INDEX", IoSyntaxHighlighter.FIELD_INDEX),
            new AttributesDescriptor("IO_FIELD_NAME", IoSyntaxHighlighter.FIELD_NAME),
            new AttributesDescriptor("IO_FIELD_TYPE_BASE", IoSyntaxHighlighter.FIELD_TYPE_BASE),
            new AttributesDescriptor("IO_FIELD_TYPE_QUOTE", IoSyntaxHighlighter.FIELD_TYPE_QUOTE),
            new AttributesDescriptor("IO_IDENTIFIER", IoSyntaxHighlighter.IDENTIFIER),
            new AttributesDescriptor("IO_IMPORT_HEAD", IoSyntaxHighlighter.IMPORT_HEAD),
            new AttributesDescriptor("IO_IMPORT_VALUE", IoSyntaxHighlighter.IMPORT_VALUE),
            new AttributesDescriptor("IO_JAVA_PACK_HEAD", IoSyntaxHighlighter.JAVA_PACK_HEAD),
            new AttributesDescriptor("IO_JAVA_PACK_VALUE", IoSyntaxHighlighter.JAVA_PACK_VALUE),
            new AttributesDescriptor("IO_LEFT_BRACE", IoSyntaxHighlighter.LEFT_BRACE),
            new AttributesDescriptor("IO_LEFT_BRACKET", IoSyntaxHighlighter.LEFT_BRACKET),
            new AttributesDescriptor("IO_LINE_COMMENT", IoSyntaxHighlighter.LINE_COMMENT),
            new AttributesDescriptor("IO_MESSAGE_HEAD", IoSyntaxHighlighter.MESSAGE_HEAD),
            new AttributesDescriptor("IO_MESSAGE_ID", IoSyntaxHighlighter.MESSAGE_ID),
            new AttributesDescriptor("IO_MESSAGE_NAME", IoSyntaxHighlighter.MESSAGE_NAME),
            new AttributesDescriptor("IO_MESSAGE_TYPE_CS", IoSyntaxHighlighter.MESSAGE_TYPE_CS),
            new AttributesDescriptor("IO_MESSAGE_TYPE_SC", IoSyntaxHighlighter.MESSAGE_TYPE_SC),
            new AttributesDescriptor("IO_NAMESPACE_HEAD", IoSyntaxHighlighter.NAMESPACE_HEAD),
            new AttributesDescriptor("IO_NAMESPACE_VALUE", IoSyntaxHighlighter.NAMESPACE_VALUE),
            new AttributesDescriptor("IO_RIGHT_BRACE", IoSyntaxHighlighter.RIGHT_BRACE),
            new AttributesDescriptor("IO_RIGHT_BRACKET", IoSyntaxHighlighter.RIGHT_BRACKET),
            new AttributesDescriptor("IO_SEMICOLON", IoSyntaxHighlighter.SEMICOLON),

    };

    @Nullable
    @Override
    public Icon getIcon() {
        return IoFileType.FILE;
    }

    @NotNull
    @Override
    public SyntaxHighlighter getHighlighter() {
        return new IoSyntaxHighlighter();
    }

    @NotNull
    @Override
    public String getDemoText() {
        return "message   CS  Clazz 1001 {\n" +
                "    int   na ;//汉子\n" +
                "    int age  ;//age\n" +
                "    Sexy sexy;\n" +
                "    Student[] students;\n" +
                "}\n" +
                "bean Student{\n" +
                " int age  ;//age\n" +
                " String name;\n" +
                " Sexy X;\n" +
                "}\n" +
                "enum Sexy{\n" +
                "    X;\n" +
                "    Y;\n" +
                "}\n" +
                "event   Hi 1001 {\n" +
                "    int   dk ;//comment\n" +
                "    int age  ;//age\n" +
                "}"
                ;
    }

    @Nullable
    @Override
    public Map<String, TextAttributesKey> getAdditionalHighlightingTagToDescriptorMap() {
        return null;
    }

    @NotNull
    @Override
    public AttributesDescriptor[] getAttributeDescriptors() {
        //  return new AttributesDescriptor[0] ;
        return DESCRIPTORS;
    }

    @NotNull
    @Override
    public ColorDescriptor[] getColorDescriptors() {
        return ColorDescriptor.EMPTY_ARRAY;
    }

    @NotNull
    @Override
    public String getDisplayName() {
        return "Io";
    }
}
