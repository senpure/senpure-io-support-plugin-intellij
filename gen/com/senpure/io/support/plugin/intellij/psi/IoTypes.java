// This is a generated file. Not intended for manual editing.
package com.senpure.io.support.plugin.intellij.psi;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import com.senpure.io.support.plugin.intellij.psi.impl.*;

public interface IoTypes {

  IElementType BEAN = new IoElementType("BEAN");
  IElementType BEAN_HEAD = new IoElementType("BEAN_HEAD");
  IElementType BEAN_NAME = new IoElementType("BEAN_NAME");
  IElementType ENTITY = new IoElementType("ENTITY");
  IElementType ENTITY_COMMENT = new IoElementType("ENTITY_COMMENT");
  IElementType ENUM = new IoElementType("ENUM");
  IElementType ENUM_FIELD = new IoElementType("ENUM_FIELD");
  IElementType ENUM_HEAD = new IoElementType("ENUM_HEAD");
  IElementType ENUM_NAME = new IoElementType("ENUM_NAME");
  IElementType EQUAL = new IoElementType("EQUAL");
  IElementType EVENT = new IoElementType("EVENT");
  IElementType EVENT_HEAD = new IoElementType("EVENT_HEAD");
  IElementType EVENT_ID = new IoElementType("EVENT_ID");
  IElementType EVENT_NAME = new IoElementType("EVENT_NAME");
  IElementType FIELD = new IoElementType("FIELD");
  IElementType FIELD_COMMENT = new IoElementType("FIELD_COMMENT");
  IElementType FIELD_INDEX = new IoElementType("FIELD_INDEX");
  IElementType FIELD_LIST = new IoElementType("FIELD_LIST");
  IElementType FIELD_NAME = new IoElementType("FIELD_NAME");
  IElementType FIELD_TYPE = new IoElementType("FIELD_TYPE");
  IElementType HEAD = new IoElementType("HEAD");
  IElementType HEAD_CONTENT = new IoElementType("HEAD_CONTENT");
  IElementType IMPORT = new IoElementType("IMPORT");
  IElementType IMPORT_HEAD = new IoElementType("IMPORT_HEAD");
  IElementType IMPORT_VALUE = new IoElementType("IMPORT_VALUE");
  IElementType JAVA_PACKAGE = new IoElementType("JAVA_PACKAGE");
  IElementType JAVA_PACKAGE_HEAD = new IoElementType("JAVA_PACKAGE_HEAD");
  IElementType JAVA_PACKAGE_VALUE = new IoElementType("JAVA_PACKAGE_VALUE");
  IElementType JS_NAMESPACE = new IoElementType("JS_NAMESPACE");
  IElementType JS_NAMESPACE_HEAD = new IoElementType("JS_NAMESPACE_HEAD");
  IElementType JS_NAMESPACE_VALUE = new IoElementType("JS_NAMESPACE_VALUE");
  IElementType LEFT_BRACE = new IoElementType("LEFT_BRACE");
  IElementType LUA_NAMESPACE = new IoElementType("LUA_NAMESPACE");
  IElementType LUA_NAMESPACE_HEAD = new IoElementType("LUA_NAMESPACE_HEAD");
  IElementType LUA_NAMESPACE_VALUE = new IoElementType("LUA_NAMESPACE_VALUE");
  IElementType MESSAGE = new IoElementType("MESSAGE");
  IElementType MESSAGE_HEAD = new IoElementType("MESSAGE_HEAD");
  IElementType MESSAGE_ID = new IoElementType("MESSAGE_ID");
  IElementType MESSAGE_NAME = new IoElementType("MESSAGE_NAME");
  IElementType MESSAGE_TYPE = new IoElementType("MESSAGE_TYPE");
  IElementType NAMESPACE = new IoElementType("NAMESPACE");
  IElementType NAMESPACE_HEAD = new IoElementType("NAMESPACE_HEAD");
  IElementType NAMESPACE_VALUE = new IoElementType("NAMESPACE_VALUE");
  IElementType RIGHT_BRACE = new IoElementType("RIGHT_BRACE");
  IElementType SEMICOLON = new IoElementType("SEMICOLON");

  IElementType T_BEAN_HEAD = new IoTokenType("T_BEAN_HEAD");
  IElementType T_BEAN_NAME = new IoTokenType("T_BEAN_NAME");
  IElementType T_CODE_COMMENT = new IoTokenType("T_CODE_COMMENT");
  IElementType T_DIGIT = new IoTokenType("T_DIGIT");
  IElementType T_ENUM_HEAD = new IoTokenType("T_ENUM_HEAD");
  IElementType T_ENUM_NAME = new IoTokenType("T_ENUM_NAME");
  IElementType T_EQUAL = new IoTokenType("=");
  IElementType T_EVENT_HEAD = new IoTokenType("T_EVENT_HEAD");
  IElementType T_EVENT_ID = new IoTokenType("T_EVENT_ID");
  IElementType T_EVENT_NAME = new IoTokenType("T_EVENT_NAME");
  IElementType T_FIELD_COMMENT = new IoTokenType("T_FIELD_COMMENT");
  IElementType T_FIELD_INDEX = new IoTokenType("T_FIELD_INDEX");
  IElementType T_FIELD_NAME = new IoTokenType("T_FIELD_NAME");
  IElementType T_FIELD_TYPE_BASE = new IoTokenType("T_FIELD_TYPE_BASE");
  IElementType T_FIELD_TYPE_QUOTE = new IoTokenType("T_FIELD_TYPE_QUOTE");
  IElementType T_IDENTIFIER = new IoTokenType("T_IDENTIFIER");
  IElementType T_IMPORT_HEAD = new IoTokenType("T_IMPORT_HEAD");
  IElementType T_IMPORT_VALUE = new IoTokenType("T_IMPORT_VALUE");
  IElementType T_JAVA_PACKAGE_HEAD = new IoTokenType("T_JAVA_PACKAGE_HEAD");
  IElementType T_JAVA_PACKAGE_VALUE = new IoTokenType("T_JAVA_PACKAGE_VALUE");
  IElementType T_JS_NAMESPACE_HEAD = new IoTokenType("T_JS_NAMESPACE_HEAD");
  IElementType T_JS_NAMESPACE_VALUE = new IoTokenType("T_JS_NAMESPACE_VALUE");
  IElementType T_LEFT_BRACE = new IoTokenType("{");
  IElementType T_LEFT_BRACKET = new IoTokenType("[");
  IElementType T_LINE_COMMENT = new IoTokenType("T_LINE_COMMENT");
  IElementType T_LUA_NAMESPACE_HEAD = new IoTokenType("T_LUA_NAMESPACE_HEAD");
  IElementType T_LUA_NAMESPACE_VALUE = new IoTokenType("T_LUA_NAMESPACE_VALUE");
  IElementType T_MESSAGE_HEAD = new IoTokenType("T_MESSAGE_HEAD");
  IElementType T_MESSAGE_ID = new IoTokenType("T_MESSAGE_ID");
  IElementType T_MESSAGE_NAME = new IoTokenType("T_MESSAGE_NAME");
  IElementType T_MESSAGE_TYPE_CS = new IoTokenType("T_MESSAGE_TYPE_CS");
  IElementType T_MESSAGE_TYPE_SC = new IoTokenType("T_MESSAGE_TYPE_SC");
  IElementType T_NAMESPACE_HEAD = new IoTokenType("T_NAMESPACE_HEAD");
  IElementType T_NAMESPACE_VALUE = new IoTokenType("T_NAMESPACE_VALUE");
  IElementType T_RIGHT_BRACE = new IoTokenType("}");
  IElementType T_RIGHT_BRACKET = new IoTokenType("]");
  IElementType T_SEMICOLON = new IoTokenType(";");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
      if (type == BEAN) {
        return new IoBeanImpl(node);
      }
      else if (type == BEAN_HEAD) {
        return new IoBeanHeadImpl(node);
      }
      else if (type == BEAN_NAME) {
        return new IoBeanNameImpl(node);
      }
      else if (type == ENTITY) {
        return new IoEntityImpl(node);
      }
      else if (type == ENTITY_COMMENT) {
        return new IoEntityCommentImpl(node);
      }
      else if (type == ENUM) {
        return new IoEnumImpl(node);
      }
      else if (type == ENUM_FIELD) {
        return new IoEnumFieldImpl(node);
      }
      else if (type == ENUM_HEAD) {
        return new IoEnumHeadImpl(node);
      }
      else if (type == ENUM_NAME) {
        return new IoEnumNameImpl(node);
      }
      else if (type == EQUAL) {
        return new IoEqualImpl(node);
      }
      else if (type == EVENT) {
        return new IoEventImpl(node);
      }
      else if (type == EVENT_HEAD) {
        return new IoEventHeadImpl(node);
      }
      else if (type == EVENT_ID) {
        return new IoEventIdImpl(node);
      }
      else if (type == EVENT_NAME) {
        return new IoEventNameImpl(node);
      }
      else if (type == FIELD) {
        return new IoFieldImpl(node);
      }
      else if (type == FIELD_COMMENT) {
        return new IoFieldCommentImpl(node);
      }
      else if (type == FIELD_INDEX) {
        return new IoFieldIndexImpl(node);
      }
      else if (type == FIELD_LIST) {
        return new IoFieldListImpl(node);
      }
      else if (type == FIELD_NAME) {
        return new IoFieldNameImpl(node);
      }
      else if (type == FIELD_TYPE) {
        return new IoFieldTypeImpl(node);
      }
      else if (type == HEAD) {
        return new IoHeadImpl(node);
      }
      else if (type == HEAD_CONTENT) {
        return new IoHeadContentImpl(node);
      }
      else if (type == IMPORT) {
        return new IoImportImpl(node);
      }
      else if (type == IMPORT_HEAD) {
        return new IoImportHeadImpl(node);
      }
      else if (type == IMPORT_VALUE) {
        return new IoImportValueImpl(node);
      }
      else if (type == JAVA_PACKAGE) {
        return new IoJavaPackageImpl(node);
      }
      else if (type == JAVA_PACKAGE_HEAD) {
        return new IoJavaPackageHeadImpl(node);
      }
      else if (type == JAVA_PACKAGE_VALUE) {
        return new IoJavaPackageValueImpl(node);
      }
      else if (type == JS_NAMESPACE) {
        return new IoJsNamespaceImpl(node);
      }
      else if (type == JS_NAMESPACE_HEAD) {
        return new IoJsNamespaceHeadImpl(node);
      }
      else if (type == JS_NAMESPACE_VALUE) {
        return new IoJsNamespaceValueImpl(node);
      }
      else if (type == LEFT_BRACE) {
        return new IoLeftBraceImpl(node);
      }
      else if (type == LUA_NAMESPACE) {
        return new IoLuaNamespaceImpl(node);
      }
      else if (type == LUA_NAMESPACE_HEAD) {
        return new IoLuaNamespaceHeadImpl(node);
      }
      else if (type == LUA_NAMESPACE_VALUE) {
        return new IoLuaNamespaceValueImpl(node);
      }
      else if (type == MESSAGE) {
        return new IoMessageImpl(node);
      }
      else if (type == MESSAGE_HEAD) {
        return new IoMessageHeadImpl(node);
      }
      else if (type == MESSAGE_ID) {
        return new IoMessageIdImpl(node);
      }
      else if (type == MESSAGE_NAME) {
        return new IoMessageNameImpl(node);
      }
      else if (type == MESSAGE_TYPE) {
        return new IoMessageTypeImpl(node);
      }
      else if (type == NAMESPACE) {
        return new IoNamespaceImpl(node);
      }
      else if (type == NAMESPACE_HEAD) {
        return new IoNamespaceHeadImpl(node);
      }
      else if (type == NAMESPACE_VALUE) {
        return new IoNamespaceValueImpl(node);
      }
      else if (type == RIGHT_BRACE) {
        return new IoRightBraceImpl(node);
      }
      else if (type == SEMICOLON) {
        return new IoSemicolonImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}
