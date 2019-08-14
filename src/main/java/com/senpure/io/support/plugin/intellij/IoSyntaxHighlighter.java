package com.senpure.io.support.plugin.intellij;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.tree.IElementType;
import com.senpure.io.support.plugin.intellij.psi.IoTokenType;
import com.senpure.io.support.plugin.intellij.psi.IoTypes;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey;

/**
 * IoSyntaxHighlighter
 *
 * @author senpure
 * @time 2019-05-23 13:40:25
 */
public class IoSyntaxHighlighter extends SyntaxHighlighterBase {


    public static final TextAttributesKey BEAN_HEAD = createTextAttributesKey("IO_BEAN_HEAD");
    public static final TextAttributesKey BEAN_NAME = createTextAttributesKey("IO_BEAN_NAME");
    public static final TextAttributesKey DIGIT = createTextAttributesKey("IO_DIGIT");
    public static final TextAttributesKey ENUM_HEAD = createTextAttributesKey("IO_ENUM_HEAD");
    public static final TextAttributesKey ENUM_NAME = createTextAttributesKey("IO_ENUM_NAME");
    public static final TextAttributesKey EQUAL = createTextAttributesKey("IO_EQUAL");
    public static final TextAttributesKey EVENT_HEAD = createTextAttributesKey("IO_EVENT_HEAD");
    public static final TextAttributesKey EVENT_ID = createTextAttributesKey("IO_EVENT_ID");
    public static final TextAttributesKey EVENT_NAME = createTextAttributesKey("IO_EVENT_NAME");
    public static final TextAttributesKey FIELD_COMMENT = createTextAttributesKey("IO_FIELD_COMMENT");
    public static final TextAttributesKey FIELD_INDEX = createTextAttributesKey("IO_FIELD_INDEX");
    public static final TextAttributesKey FIELD_NAME = createTextAttributesKey("IO_FIELD_NAME");
    public static final TextAttributesKey FIELD_TYPE_BASE = createTextAttributesKey("IO_FIELD_TYPE_BASE");
    public static final TextAttributesKey FIELD_TYPE_QUOTE = createTextAttributesKey("IO_FIELD_TYPE_QUOTE");
    public static final TextAttributesKey IDENTIFIER = createTextAttributesKey("IO_IDENTIFIER");
    public static final TextAttributesKey IMPORT_HEAD = createTextAttributesKey("IO_IMPORT_HEAD");
    public static final TextAttributesKey IMPORT_VALUE = createTextAttributesKey("IO_IMPORT_VALUE");
    public static final TextAttributesKey JAVA_PACK_HEAD = createTextAttributesKey("IO_JAVA_PACK_HEAD");
    public static final TextAttributesKey JAVA_PACK_VALUE = createTextAttributesKey("IO_JAVA_PACK_VALUE");
    public static final TextAttributesKey LUA_NAMESPACE_HEAD = createTextAttributesKey("IO_LUA_NAMESPACE_HEAD");
    public static final TextAttributesKey LUA_NAMESPACE_VALUE = createTextAttributesKey("IO_LUA_NAMESPACE_VALUE");

    public static final TextAttributesKey LEFT_BRACE = createTextAttributesKey("IO_LEFT_BRACE");
    public static final TextAttributesKey LEFT_BRACKET = createTextAttributesKey("IO_LEFT_BRACKET");
    public static final TextAttributesKey LINE_COMMENT = createTextAttributesKey("IO_LINE_COMMENT");
    public static final TextAttributesKey CODE_COMMENT = createTextAttributesKey("IO_CODE_COMMENT");
    public static final TextAttributesKey MESSAGE_HEAD = createTextAttributesKey("IO_MESSAGE_HEAD");
    public static final TextAttributesKey MESSAGE_ID = createTextAttributesKey("IO_MESSAGE_ID");
    public static final TextAttributesKey MESSAGE_NAME = createTextAttributesKey("IO_MESSAGE_NAME");
    public static final TextAttributesKey MESSAGE_TYPE_CS = createTextAttributesKey("IO_MESSAGE_TYPE_CS");
    public static final TextAttributesKey MESSAGE_TYPE_SC = createTextAttributesKey("IO_MESSAGE_TYPE_SC");
    public static final TextAttributesKey NAMESPACE_HEAD = createTextAttributesKey("IO_NAMESPACE_HEAD");
    public static final TextAttributesKey NAMESPACE_VALUE = createTextAttributesKey("IO_NAMESPACE_VALUE");
    public static final TextAttributesKey RIGHT_BRACE = createTextAttributesKey("IO_RIGHT_BRACE");
    public static final TextAttributesKey RIGHT_BRACKET = createTextAttributesKey("IO_RIGHT_BRACKET");
    public static final TextAttributesKey SEMICOLON = createTextAttributesKey("IO_SEMICOLON");
    private static final TextAttributesKey[] BEAN_HEAD_KEYS = new TextAttributesKey[]{ BEAN_HEAD };
    private static final TextAttributesKey[] BEAN_NAME_KEYS = new TextAttributesKey[]{ BEAN_NAME };
    private static final TextAttributesKey[] DIGIT_KEYS = new TextAttributesKey[]{ DIGIT };
    private static final TextAttributesKey[] ENUM_HEAD_KEYS = new TextAttributesKey[]{ ENUM_HEAD };
    private static final TextAttributesKey[] ENUM_NAME_KEYS = new TextAttributesKey[]{ ENUM_NAME };
    private static final TextAttributesKey[] EQUAL_KEYS = new TextAttributesKey[]{ EQUAL };
    private static final TextAttributesKey[] EVENT_HEAD_KEYS = new TextAttributesKey[]{ EVENT_HEAD };
    private static final TextAttributesKey[] EVENT_ID_KEYS = new TextAttributesKey[]{ EVENT_ID };
    private static final TextAttributesKey[] EVENT_NAME_KEYS = new TextAttributesKey[]{ EVENT_NAME };
    private static final TextAttributesKey[] FIELD_COMMENT_KEYS = new TextAttributesKey[]{ FIELD_COMMENT };
    private static final TextAttributesKey[] FIELD_INDEX_KEYS = new TextAttributesKey[]{ FIELD_INDEX };
    private static final TextAttributesKey[] FIELD_NAME_KEYS = new TextAttributesKey[]{ FIELD_NAME };
    private static final TextAttributesKey[] FIELD_TYPE_BASE_KEYS = new TextAttributesKey[]{ FIELD_TYPE_BASE };
    private static final TextAttributesKey[] FIELD_TYPE_QUOTE_KEYS = new TextAttributesKey[]{ FIELD_TYPE_QUOTE };
    private static final TextAttributesKey[] IDENTIFIER_KEYS = new TextAttributesKey[]{ IDENTIFIER };
    private static final TextAttributesKey[] IMPORT_HEAD_KEYS = new TextAttributesKey[]{ IMPORT_HEAD };
    private static final TextAttributesKey[] IMPORT_VALUE_KEYS = new TextAttributesKey[]{ IMPORT_VALUE };
    private static final TextAttributesKey[] JAVA_PACK_HEAD_KEYS = new TextAttributesKey[]{ JAVA_PACK_HEAD };
    private static final TextAttributesKey[] JAVA_PACK_VALUE_KEYS = new TextAttributesKey[]{ JAVA_PACK_VALUE };
    private static final TextAttributesKey[] LUA_NAMESPACE_HEAD_KEYS = new TextAttributesKey[]{LUA_NAMESPACE_HEAD};
    private static final TextAttributesKey[] LUA_NAMESPACE_VALUE_KEYS = new TextAttributesKey[]{ LUA_NAMESPACE_VALUE};
    private static final TextAttributesKey[] LEFT_BRACE_KEYS = new TextAttributesKey[]{ LEFT_BRACE };
    private static final TextAttributesKey[] LEFT_BRACKET_KEYS = new TextAttributesKey[]{ LEFT_BRACKET };
    private static final TextAttributesKey[] LINE_COMMENT_KEYS = new TextAttributesKey[]{ LINE_COMMENT };
    private static final TextAttributesKey[] CODE_COMMENT_KEYS = new TextAttributesKey[]{ CODE_COMMENT };
    private static final TextAttributesKey[] MESSAGE_HEAD_KEYS = new TextAttributesKey[]{ MESSAGE_HEAD };
    private static final TextAttributesKey[] MESSAGE_ID_KEYS = new TextAttributesKey[]{ MESSAGE_ID };
    private static final TextAttributesKey[] MESSAGE_NAME_KEYS = new TextAttributesKey[]{ MESSAGE_NAME };
    private static final TextAttributesKey[] MESSAGE_TYPE_CS_KEYS = new TextAttributesKey[]{ MESSAGE_TYPE_CS };
    private static final TextAttributesKey[] MESSAGE_TYPE_SC_KEYS = new TextAttributesKey[]{ MESSAGE_TYPE_SC };
    private static final TextAttributesKey[] NAMESPACE_HEAD_KEYS = new TextAttributesKey[]{ NAMESPACE_HEAD };
    private static final TextAttributesKey[] NAMESPACE_VALUE_KEYS = new TextAttributesKey[]{ NAMESPACE_VALUE };
    private static final TextAttributesKey[] RIGHT_BRACE_KEYS = new TextAttributesKey[]{ RIGHT_BRACE };
    private static final TextAttributesKey[] RIGHT_BRACKET_KEYS = new TextAttributesKey[]{ RIGHT_BRACKET };
    private static final TextAttributesKey[] SEMICOLON_KEYS = new TextAttributesKey[]{ SEMICOLON };
    //--
    private static final TextAttributesKey[] EMPTY_KEYS = new TextAttributesKey[0];

    @NotNull
    @Override
    public Lexer getHighlightingLexer() {
        return new IoLexerAdapter(1);
    }

    @NotNull
    @Override
    public TextAttributesKey[] getTokenHighlights(IElementType tokenType) {
        // System.out.println(tokenType.toString()+"  ---------TokenHighlights");

        if (tokenType.equals(IoTypes.T_LINE_COMMENT)) {
            return LINE_COMMENT_KEYS;

        } else if (tokenType.equals(IoTypes.T_BEAN_HEAD)) {
            return BEAN_HEAD_KEYS;
        }
        else if (tokenType.equals(IoTypes.T_BEAN_NAME)) {
            return BEAN_NAME_KEYS;
        }
        else if (tokenType.equals(IoTypes.T_DIGIT)) {
            return DIGIT_KEYS;
        }
        else if (tokenType.equals(IoTypes.T_ENUM_HEAD)) {
            return ENUM_HEAD_KEYS;
        }
        else if (tokenType.equals(IoTypes.T_ENUM_NAME)) {
            return ENUM_NAME_KEYS;
        }
        else if (tokenType.equals(IoTypes.T_EQUAL)) {
            return EQUAL_KEYS;
        }
        else if (tokenType.equals(IoTypes.T_EVENT_HEAD)) {
            return EVENT_HEAD_KEYS;
        }
        else if (tokenType.equals(IoTypes.T_EVENT_ID)) {
            return EVENT_ID_KEYS;
        }
        else if (tokenType.equals(IoTypes.T_EVENT_NAME)) {
            return EVENT_NAME_KEYS;
        }
        else if (tokenType.equals(IoTypes.T_FIELD_COMMENT)) {
            return FIELD_COMMENT_KEYS;
        }
        else if (tokenType.equals(IoTypes.T_FIELD_INDEX)) {
            return FIELD_INDEX_KEYS;
        }
        else if (tokenType.equals(IoTypes.T_FIELD_NAME)) {
            return FIELD_NAME_KEYS;
        }
        else if (tokenType.equals(IoTypes.T_FIELD_TYPE_BASE)) {
            return FIELD_TYPE_BASE_KEYS;
        }
        else if (tokenType.equals(IoTypes.T_FIELD_TYPE_QUOTE)) {
            return FIELD_TYPE_QUOTE_KEYS;
        }
        else if (tokenType.equals(IoTypes.T_IDENTIFIER)) {
            return IDENTIFIER_KEYS;
        }
        else if (tokenType.equals(IoTypes.T_IMPORT_HEAD)) {
            return IMPORT_HEAD_KEYS;
        }
        else if (tokenType.equals(IoTypes.T_IMPORT_VALUE)) {
            return IMPORT_VALUE_KEYS;
        }
        else if (tokenType.equals(IoTypes.T_JAVA_PACK_HEAD)) {
            return JAVA_PACK_HEAD_KEYS;
        }
        else if (tokenType.equals(IoTypes.T_JAVA_PACK_VALUE)) {
            return JAVA_PACK_VALUE_KEYS;
        }
        else if (tokenType.equals(IoTypes.T_LUA_NAMESPACE_HEAD)) {
            return LUA_NAMESPACE_HEAD_KEYS;
        }
        else if (tokenType.equals(IoTypes.T_LUA_NAMESPACE_VALUE)) {
            return LUA_NAMESPACE_VALUE_KEYS;
        }

        else if (tokenType.equals(IoTypes.T_LEFT_BRACE)) {
            return LEFT_BRACE_KEYS;
        }
        else if (tokenType.equals(IoTypes.T_LEFT_BRACKET)) {
            return LEFT_BRACKET_KEYS;
        }
        else if (tokenType.equals(IoTypes.T_LINE_COMMENT)) {
            return LINE_COMMENT_KEYS;
        }
        else if (tokenType.equals(IoTypes.T_CODE_COMMENT)) {
            return CODE_COMMENT_KEYS;
        }
        else if (tokenType.equals(IoTypes.T_MESSAGE_HEAD)) {
            return MESSAGE_HEAD_KEYS;
        }
        else if (tokenType.equals(IoTypes.T_MESSAGE_ID)) {
            return MESSAGE_ID_KEYS;
        }
        else if (tokenType.equals(IoTypes.T_MESSAGE_NAME)) {
            return MESSAGE_NAME_KEYS;
        }
        else if (tokenType.equals(IoTypes.T_MESSAGE_TYPE_CS)) {
            return MESSAGE_TYPE_CS_KEYS;
        }
        else if (tokenType.equals(IoTypes.T_MESSAGE_TYPE_SC)) {
            return MESSAGE_TYPE_SC_KEYS;
        }
        else if (tokenType.equals(IoTypes.T_NAMESPACE_HEAD)) {
            return NAMESPACE_HEAD_KEYS;
        }
        else if (tokenType.equals(IoTypes.T_NAMESPACE_VALUE)) {
            return NAMESPACE_VALUE_KEYS;
        }
        else if (tokenType.equals(IoTypes.T_RIGHT_BRACE)) {
            return RIGHT_BRACE_KEYS;
        }
        else if (tokenType.equals(IoTypes.T_RIGHT_BRACKET)) {
            return RIGHT_BRACKET_KEYS;
        }
        else if (tokenType.equals(IoTypes.T_SEMICOLON)) {
            return SEMICOLON_KEYS;
        } else {
            return EMPTY_KEYS;
        }

    }

    public static void main(String[] args) {

       // def();
       // keys();
       // colors();
        colorsSet();
    }

    private static String getReadName(Field field) {
        return field.getName().substring(2);
    }

    private static void colorsSet() {
        List<Field> fields = getTokenFields();
        StringBuilder sb = new StringBuilder();

        for (Field field : fields) {
            sb.append(" new AttributesDescriptor(");
            sb.append("\"");
            sb.append("IO_");
            sb.append(getReadName(field));
            sb.append("\"");
            sb.append(",");
            sb.append("IoSyntaxHighlighter.");
            sb.append(getReadName(field));
            sb.append("),\n");

        }
        System.out.println(sb.toString());

    }
    private static void colors() {
        Random random = new Random();

        List<Field> fields = getTokenFields();
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version='1.0'?>\n");
        sb.append("<list>\n");
        for (Field field : fields) {
            sb.append("    <option name=\"");
            sb.append("IO_");
            sb.append(getReadName(field));

            sb.append("\"");
            sb.append(">\n");
            sb.append("      <value>\n");
            sb.append("             <option name=\"FOREGROUND\" value=\"");

            sb.append("#");
            for (int i = 0; i < 3; i++) {
                int value = random.nextInt(256) + 1;
                sb.append(Integer.toHexString(value));
            }
            sb.append("\"/>\n");

            sb.append("      </value>\n");
            sb.append("    </option>\n");
        }
        sb.append("</list>");
        System.out.println(sb);
    }

    private static void def() {
        List<Field> fields = getTokenFields();
        StringBuilder sb = new StringBuilder();

        for (Field field : fields) {
            sb.append(" public static final TextAttributesKey ");
            sb.append(getReadName(field));
            sb.append(" = ").append("createTextAttributesKey(");
            sb.append("\"").append("IO_").append(getReadName(field));
            sb.append("\");\n");
        }

        for (Field field : fields) {
            sb.append("  private static final TextAttributesKey[] ");
            sb.append(getReadName(field)).append("_KEYS");
            sb.append(" = ").append("new TextAttributesKey[]{");
            sb.append(" ").append(getReadName(field));
            sb.append(" };\n");
        }

        System.out.println(sb.toString());
    }

    private static void keys() {
        List<Field> fields = getTokenFields();
        StringBuilder sb = new StringBuilder();
        for (Field field : fields) {
            sb.append("else if (tokenType.equals(");
            sb.append("IoTypes.");
            sb.append(field.getName());
            sb.append(")) { \n");
            sb.append("     return ");
            sb.append(getReadName(field)).append("_KEYS;\n}\n");
        }

        System.out.println(sb);
    }


    private static List<Field> getTokenFields() {
        IoTypes ioTypes = new IoTypes() {
            @Override
            public int hashCode() {
                return super.hashCode();
            }
        };

        List<Field> fields = new ArrayList<>();
        for (Field field : IoTypes.class.getDeclaredFields()) {
            field.setAccessible(true);
            try {
                Object object = field.get(ioTypes);
                if (object instanceof IoTokenType) {
                    fields.add(field);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }

        return fields;
    }
}
