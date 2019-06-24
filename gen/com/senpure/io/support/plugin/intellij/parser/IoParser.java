// This is a generated file. Not intended for manual editing.
package com.senpure.io.support.plugin.intellij.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import static com.senpure.io.support.plugin.intellij.psi.IoTypes.*;
import static com.senpure.io.support.plugin.intellij.psi.impl.IoParserUtil.*;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.lang.ASTNode;
import com.intellij.psi.tree.TokenSet;
import com.intellij.lang.PsiParser;
import com.intellij.lang.LightPsiParser;

@SuppressWarnings({"SimplifiableIfStatement", "UnusedAssignment"})
public class IoParser implements PsiParser, LightPsiParser {

  public ASTNode parse(IElementType t, PsiBuilder b) {
    parseLight(t, b);
    return b.getTreeBuilt();
  }

  public void parseLight(IElementType t, PsiBuilder b) {
    boolean r;
    b = adapt_builder_(t, b, this, null);
    Marker m = enter_section_(b, 0, _COLLAPSE_, null);
    if (t instanceof IFileElementType) {
      r = parse_root_(t, b, 0);
    }
    else {
      r = false;
    }
    exit_section_(b, 0, m, t, r, true, TRUE_CONDITION);
  }

  protected boolean parse_root_(IElementType t, PsiBuilder b, int l) {
    return protocol(b, l + 1);
  }

  /* ********************************************************** */
  // entityComment*
  //           beanHead beanName  leftBrace
  //           field*
  //           rightBrace
  public static boolean bean(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bean")) return false;
    if (!nextTokenIs(b, "<bean>", T_BEAN_HEAD, T_LINE_COMMENT)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, BEAN, "<bean>");
    r = bean_0(b, l + 1);
    r = r && beanHead(b, l + 1);
    r = r && beanName(b, l + 1);
    r = r && leftBrace(b, l + 1);
    r = r && bean_4(b, l + 1);
    r = r && rightBrace(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // entityComment*
  private static boolean bean_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bean_0")) return false;
    while (true) {
      int c = current_position_(b);
      if (!entityComment(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "bean_0", c)) break;
    }
    return true;
  }

  // field*
  private static boolean bean_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "bean_4")) return false;
    while (true) {
      int c = current_position_(b);
      if (!field(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "bean_4", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // T_BEAN_HEAD
  public static boolean beanHead(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "beanHead")) return false;
    if (!nextTokenIs(b, T_BEAN_HEAD)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, T_BEAN_HEAD);
    exit_section_(b, m, BEAN_HEAD, r);
    return r;
  }

  /* ********************************************************** */
  // T_BEAN_NAME
  public static boolean beanName(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "beanName")) return false;
    if (!nextTokenIs(b, T_BEAN_NAME)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, T_BEAN_NAME);
    exit_section_(b, m, BEAN_NAME, r);
    return r;
  }

  /* ********************************************************** */
  // message|bean|event|enum
  public static boolean entity(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "entity")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, ENTITY, "<entity>");
    r = message(b, l + 1);
    if (!r) r = bean(b, l + 1);
    if (!r) r = event(b, l + 1);
    if (!r) r = enum_$(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // T_LINE_COMMENT
  public static boolean entityComment(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "entityComment")) return false;
    if (!nextTokenIs(b, T_LINE_COMMENT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, T_LINE_COMMENT);
    exit_section_(b, m, ENTITY_COMMENT, r);
    return r;
  }

  /* ********************************************************** */
  // entityComment*
  //           enumHead enumName leftBrace
  //           enumField *
  //           rightBrace
  public static boolean enum_$(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "enum_$")) return false;
    if (!nextTokenIs(b, "<enum $>", T_ENUM_HEAD, T_LINE_COMMENT)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, ENUM, "<enum $>");
    r = enum_0(b, l + 1);
    r = r && enumHead(b, l + 1);
    r = r && enumName(b, l + 1);
    r = r && leftBrace(b, l + 1);
    r = r && enum_4(b, l + 1);
    r = r && rightBrace(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // entityComment*
  private static boolean enum_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "enum_0")) return false;
    while (true) {
      int c = current_position_(b);
      if (!entityComment(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "enum_0", c)) break;
    }
    return true;
  }

  // enumField *
  private static boolean enum_4(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "enum_4")) return false;
    while (true) {
      int c = current_position_(b);
      if (!enumField(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "enum_4", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // fieldName(equal fieldIndex)? semicolon fieldComment?
  public static boolean enumField(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "enumField")) return false;
    if (!nextTokenIs(b, T_FIELD_NAME)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = fieldName(b, l + 1);
    r = r && enumField_1(b, l + 1);
    r = r && semicolon(b, l + 1);
    r = r && enumField_3(b, l + 1);
    exit_section_(b, m, ENUM_FIELD, r);
    return r;
  }

  // (equal fieldIndex)?
  private static boolean enumField_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "enumField_1")) return false;
    enumField_1_0(b, l + 1);
    return true;
  }

  // equal fieldIndex
  private static boolean enumField_1_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "enumField_1_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = equal(b, l + 1);
    r = r && fieldIndex(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // fieldComment?
  private static boolean enumField_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "enumField_3")) return false;
    fieldComment(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // T_ENUM_HEAD
  public static boolean enumHead(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "enumHead")) return false;
    if (!nextTokenIs(b, T_ENUM_HEAD)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, T_ENUM_HEAD);
    exit_section_(b, m, ENUM_HEAD, r);
    return r;
  }

  /* ********************************************************** */
  // T_ENUM_NAME
  public static boolean enumName(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "enumName")) return false;
    if (!nextTokenIs(b, T_ENUM_NAME)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, T_ENUM_NAME);
    exit_section_(b, m, ENUM_NAME, r);
    return r;
  }

  /* ********************************************************** */
  // T_EQUAL
  public static boolean equal(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "equal")) return false;
    if (!nextTokenIs(b, T_EQUAL)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, T_EQUAL);
    exit_section_(b, m, EQUAL, r);
    return r;
  }

  /* ********************************************************** */
  // entityComment*
  //           eventHead eventName eventId leftBrace
  //           field*
  //           rightBrace
  public static boolean event(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "event")) return false;
    if (!nextTokenIs(b, "<event>", T_EVENT_HEAD, T_LINE_COMMENT)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, EVENT, "<event>");
    r = event_0(b, l + 1);
    r = r && eventHead(b, l + 1);
    r = r && eventName(b, l + 1);
    r = r && eventId(b, l + 1);
    r = r && leftBrace(b, l + 1);
    r = r && event_5(b, l + 1);
    r = r && rightBrace(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // entityComment*
  private static boolean event_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "event_0")) return false;
    while (true) {
      int c = current_position_(b);
      if (!entityComment(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "event_0", c)) break;
    }
    return true;
  }

  // field*
  private static boolean event_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "event_5")) return false;
    while (true) {
      int c = current_position_(b);
      if (!field(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "event_5", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // T_EVENT_HEAD
  public static boolean eventHead(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "eventHead")) return false;
    if (!nextTokenIs(b, T_EVENT_HEAD)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, T_EVENT_HEAD);
    exit_section_(b, m, EVENT_HEAD, r);
    return r;
  }

  /* ********************************************************** */
  // T_EVENT_ID
  public static boolean eventId(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "eventId")) return false;
    if (!nextTokenIs(b, T_EVENT_ID)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, T_EVENT_ID);
    exit_section_(b, m, EVENT_ID, r);
    return r;
  }

  /* ********************************************************** */
  // T_EVENT_NAME
  public static boolean eventName(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "eventName")) return false;
    if (!nextTokenIs(b, T_EVENT_NAME)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, T_EVENT_NAME);
    exit_section_(b, m, EVENT_NAME, r);
    return r;
  }

  /* ********************************************************** */
  // fieldType fieldList? fieldName(equal fieldIndex)?semicolon fieldComment?
  public static boolean field(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "field")) return false;
    if (!nextTokenIs(b, "<field>", T_FIELD_TYPE_BASE, T_FIELD_TYPE_QUOTE)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, FIELD, "<field>");
    r = fieldType(b, l + 1);
    r = r && field_1(b, l + 1);
    r = r && fieldName(b, l + 1);
    r = r && field_3(b, l + 1);
    r = r && semicolon(b, l + 1);
    r = r && field_5(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // fieldList?
  private static boolean field_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "field_1")) return false;
    fieldList(b, l + 1);
    return true;
  }

  // (equal fieldIndex)?
  private static boolean field_3(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "field_3")) return false;
    field_3_0(b, l + 1);
    return true;
  }

  // equal fieldIndex
  private static boolean field_3_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "field_3_0")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = equal(b, l + 1);
    r = r && fieldIndex(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // fieldComment?
  private static boolean field_5(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "field_5")) return false;
    fieldComment(b, l + 1);
    return true;
  }

  /* ********************************************************** */
  // T_FIELD_COMMENT
  public static boolean fieldComment(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "fieldComment")) return false;
    if (!nextTokenIs(b, T_FIELD_COMMENT)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, T_FIELD_COMMENT);
    exit_section_(b, m, FIELD_COMMENT, r);
    return r;
  }

  /* ********************************************************** */
  // T_FIELD_INDEX
  public static boolean fieldIndex(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "fieldIndex")) return false;
    if (!nextTokenIs(b, T_FIELD_INDEX)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, T_FIELD_INDEX);
    exit_section_(b, m, FIELD_INDEX, r);
    return r;
  }

  /* ********************************************************** */
  // T_LEFT_BRACKET T_RIGHT_BRACKET
  public static boolean fieldList(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "fieldList")) return false;
    if (!nextTokenIs(b, T_LEFT_BRACKET)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeTokens(b, 0, T_LEFT_BRACKET, T_RIGHT_BRACKET);
    exit_section_(b, m, FIELD_LIST, r);
    return r;
  }

  /* ********************************************************** */
  // T_FIELD_NAME
  public static boolean fieldName(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "fieldName")) return false;
    if (!nextTokenIs(b, T_FIELD_NAME)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, T_FIELD_NAME);
    exit_section_(b, m, FIELD_NAME, r);
    return r;
  }

  /* ********************************************************** */
  // T_FIELD_TYPE_BASE|T_FIELD_TYPE_QUOTE
  public static boolean fieldType(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "fieldType")) return false;
    if (!nextTokenIs(b, "<field type>", T_FIELD_TYPE_BASE, T_FIELD_TYPE_QUOTE)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, FIELD_TYPE, "<field type>");
    r = consumeToken(b, T_FIELD_TYPE_BASE);
    if (!r) r = consumeToken(b, T_FIELD_TYPE_QUOTE);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // headContent*
  public static boolean head(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "head")) return false;
    Marker m = enter_section_(b, l, _NONE_, HEAD, "<head>");
    while (true) {
      int c = current_position_(b);
      if (!headContent(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "head", c)) break;
    }
    exit_section_(b, l, m, true, false, null);
    return true;
  }

  /* ********************************************************** */
  // import|javaPack|namespace
  public static boolean headContent(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "headContent")) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, HEAD_CONTENT, "<head content>");
    r = import_$(b, l + 1);
    if (!r) r = javaPack(b, l + 1);
    if (!r) r = namespace(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // importHead importValue semicolon
  public static boolean import_$(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "import_$")) return false;
    if (!nextTokenIs(b, T_IMPORT_HEAD)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = importHead(b, l + 1);
    r = r && importValue(b, l + 1);
    r = r && semicolon(b, l + 1);
    exit_section_(b, m, IMPORT, r);
    return r;
  }

  /* ********************************************************** */
  // T_IMPORT_HEAD
  public static boolean importHead(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "importHead")) return false;
    if (!nextTokenIs(b, T_IMPORT_HEAD)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, T_IMPORT_HEAD);
    exit_section_(b, m, IMPORT_HEAD, r);
    return r;
  }

  /* ********************************************************** */
  // T_IMPORT_VALUE
  public static boolean importValue(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "importValue")) return false;
    if (!nextTokenIs(b, T_IMPORT_VALUE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, T_IMPORT_VALUE);
    exit_section_(b, m, IMPORT_VALUE, r);
    return r;
  }

  /* ********************************************************** */
  // javaPackHead javaPackValue semicolon
  public static boolean javaPack(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "javaPack")) return false;
    if (!nextTokenIs(b, T_JAVA_PACK_HEAD)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = javaPackHead(b, l + 1);
    r = r && javaPackValue(b, l + 1);
    r = r && semicolon(b, l + 1);
    exit_section_(b, m, JAVA_PACK, r);
    return r;
  }

  /* ********************************************************** */
  // T_JAVA_PACK_HEAD
  public static boolean javaPackHead(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "javaPackHead")) return false;
    if (!nextTokenIs(b, T_JAVA_PACK_HEAD)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, T_JAVA_PACK_HEAD);
    exit_section_(b, m, JAVA_PACK_HEAD, r);
    return r;
  }

  /* ********************************************************** */
  // T_JAVA_PACK_VALUE
  public static boolean javaPackValue(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "javaPackValue")) return false;
    if (!nextTokenIs(b, T_JAVA_PACK_VALUE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, T_JAVA_PACK_VALUE);
    exit_section_(b, m, JAVA_PACK_VALUE, r);
    return r;
  }

  /* ********************************************************** */
  // T_LEFT_BRACE
  public static boolean leftBrace(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "leftBrace")) return false;
    if (!nextTokenIs(b, T_LEFT_BRACE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, T_LEFT_BRACE);
    exit_section_(b, m, LEFT_BRACE, r);
    return r;
  }

  /* ********************************************************** */
  // entityComment*
  //            messageHead messageType messageName messageId leftBrace
  //           field*
  //           rightBrace
  public static boolean message(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "message")) return false;
    if (!nextTokenIs(b, "<message>", T_LINE_COMMENT, T_MESSAGE_HEAD)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, MESSAGE, "<message>");
    r = message_0(b, l + 1);
    r = r && messageHead(b, l + 1);
    r = r && messageType(b, l + 1);
    r = r && messageName(b, l + 1);
    r = r && messageId(b, l + 1);
    r = r && leftBrace(b, l + 1);
    r = r && message_6(b, l + 1);
    r = r && rightBrace(b, l + 1);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  // entityComment*
  private static boolean message_0(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "message_0")) return false;
    while (true) {
      int c = current_position_(b);
      if (!entityComment(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "message_0", c)) break;
    }
    return true;
  }

  // field*
  private static boolean message_6(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "message_6")) return false;
    while (true) {
      int c = current_position_(b);
      if (!field(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "message_6", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // T_MESSAGE_HEAD
  public static boolean messageHead(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "messageHead")) return false;
    if (!nextTokenIs(b, T_MESSAGE_HEAD)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, T_MESSAGE_HEAD);
    exit_section_(b, m, MESSAGE_HEAD, r);
    return r;
  }

  /* ********************************************************** */
  // T_MESSAGE_ID
  public static boolean messageId(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "messageId")) return false;
    if (!nextTokenIs(b, T_MESSAGE_ID)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, T_MESSAGE_ID);
    exit_section_(b, m, MESSAGE_ID, r);
    return r;
  }

  /* ********************************************************** */
  // T_MESSAGE_NAME
  public static boolean messageName(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "messageName")) return false;
    if (!nextTokenIs(b, T_MESSAGE_NAME)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, T_MESSAGE_NAME);
    exit_section_(b, m, MESSAGE_NAME, r);
    return r;
  }

  /* ********************************************************** */
  // T_MESSAGE_TYPE_CS|T_MESSAGE_TYPE_SC
  public static boolean messageType(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "messageType")) return false;
    if (!nextTokenIs(b, "<message type>", T_MESSAGE_TYPE_CS, T_MESSAGE_TYPE_SC)) return false;
    boolean r;
    Marker m = enter_section_(b, l, _NONE_, MESSAGE_TYPE, "<message type>");
    r = consumeToken(b, T_MESSAGE_TYPE_CS);
    if (!r) r = consumeToken(b, T_MESSAGE_TYPE_SC);
    exit_section_(b, l, m, r, false, null);
    return r;
  }

  /* ********************************************************** */
  // namespaceHead namespaceValue semicolon
  public static boolean namespace(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "namespace")) return false;
    if (!nextTokenIs(b, T_NAMESPACE_HEAD)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = namespaceHead(b, l + 1);
    r = r && namespaceValue(b, l + 1);
    r = r && semicolon(b, l + 1);
    exit_section_(b, m, NAMESPACE, r);
    return r;
  }

  /* ********************************************************** */
  // T_NAMESPACE_HEAD
  public static boolean namespaceHead(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "namespaceHead")) return false;
    if (!nextTokenIs(b, T_NAMESPACE_HEAD)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, T_NAMESPACE_HEAD);
    exit_section_(b, m, NAMESPACE_HEAD, r);
    return r;
  }

  /* ********************************************************** */
  // T_NAMESPACE_VALUE
  public static boolean namespaceValue(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "namespaceValue")) return false;
    if (!nextTokenIs(b, T_NAMESPACE_VALUE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, T_NAMESPACE_VALUE);
    exit_section_(b, m, NAMESPACE_VALUE, r);
    return r;
  }

  /* ********************************************************** */
  // head entity*
  static boolean protocol(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "protocol")) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = head(b, l + 1);
    r = r && protocol_1(b, l + 1);
    exit_section_(b, m, null, r);
    return r;
  }

  // entity*
  private static boolean protocol_1(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "protocol_1")) return false;
    while (true) {
      int c = current_position_(b);
      if (!entity(b, l + 1)) break;
      if (!empty_element_parsed_guard_(b, "protocol_1", c)) break;
    }
    return true;
  }

  /* ********************************************************** */
  // T_RIGHT_BRACE
  public static boolean rightBrace(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "rightBrace")) return false;
    if (!nextTokenIs(b, T_RIGHT_BRACE)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, T_RIGHT_BRACE);
    exit_section_(b, m, RIGHT_BRACE, r);
    return r;
  }

  /* ********************************************************** */
  // T_SEMICOLON
  public static boolean semicolon(PsiBuilder b, int l) {
    if (!recursion_guard_(b, l, "semicolon")) return false;
    if (!nextTokenIs(b, T_SEMICOLON)) return false;
    boolean r;
    Marker m = enter_section_(b);
    r = consumeToken(b, T_SEMICOLON);
    exit_section_(b, m, SEMICOLON, r);
    return r;
  }

}
