package com.senpure.io.support.plugin.intellij.lexer;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;

import static com.intellij.psi.TokenType.BAD_CHARACTER;
import static com.intellij.psi.TokenType.WHITE_SPACE;
import static com.senpure.io.support.plugin.intellij.psi.IoTypes.*;

%%

%{
  public _IoLexer() {
    this((java.io.Reader)null);
  }
%}

%public
%class _IoLexer
%implements FlexLexer
%function advance
%type IElementType
%unicode

EOL=\R
WHITE_SPACE=\s+

T_MESSAGE_TYPE_CS=CS|cs
T_MESSAGE_TYPE_SC=SC|sc
T_DIGIT=[0-9]+
T_FIELD_TYPE_BASE=int|long|sint|slong|sfixed32|sfixed64|float|double|boolean|String|string
T_IDENTIFIER=[a-zA-Z][a-zA-Z0-9_]*
T_LINE_COMMENT="//"[^\r\n]*

%%
<YYINITIAL> {
  {WHITE_SPACE}            { return WHITE_SPACE; }

  ";"                      { return T_SEMICOLON; }
  "="                      { return T_EQUAL; }
  "["                      { return T_LEFT_BRACKET; }
  "]"                      { return T_RIGHT_BRACKET; }
  "{"                      { return T_LEFT_BRACE; }
  "}"                      { return T_RIGHT_BRACE; }
  "T_MESSAGE_HEAD"         { return T_MESSAGE_HEAD; }
  "T_MESSAGE_NAME"         { return T_MESSAGE_NAME; }
  "T_MESSAGE_ID"           { return T_MESSAGE_ID; }
  "T_FIELD_NAME"           { return T_FIELD_NAME; }
  "T_FIELD_INDEX"          { return T_FIELD_INDEX; }
  "T_FIELD_COMMENT"        { return T_FIELD_COMMENT; }
  "T_FIELD_TYPE_QUOTE"     { return T_FIELD_TYPE_QUOTE; }
  "T_BEAN_HEAD"            { return T_BEAN_HEAD; }
  "T_BEAN_NAME"            { return T_BEAN_NAME; }
  "T_EVENT_HEAD"           { return T_EVENT_HEAD; }
  "T_EVENT_NAME"           { return T_EVENT_NAME; }
  "T_EVENT_ID"             { return T_EVENT_ID; }
  "T_ENUM_HEAD"            { return T_ENUM_HEAD; }
  "T_ENUM_NAME"            { return T_ENUM_NAME; }
  "T_NAMESPACE_HEAD"       { return T_NAMESPACE_HEAD; }
  "T_NAMESPACE_VALUE"      { return T_NAMESPACE_VALUE; }
  "T_JAVA_PACK_HEAD"       { return T_JAVA_PACK_HEAD; }
  "T_JAVA_PACK_VALUE"      { return T_JAVA_PACK_VALUE; }
  "T_IMPORT_HEAD"          { return T_IMPORT_HEAD; }
  "T_IMPORT_VALUE"         { return T_IMPORT_VALUE; }

  {T_MESSAGE_TYPE_CS}      { return T_MESSAGE_TYPE_CS; }
  {T_MESSAGE_TYPE_SC}      { return T_MESSAGE_TYPE_SC; }
  {T_DIGIT}                { return T_DIGIT; }
  {T_FIELD_TYPE_BASE}      { return T_FIELD_TYPE_BASE; }
  {T_IDENTIFIER}           { return T_IDENTIFIER; }
  {T_LINE_COMMENT}         { return T_LINE_COMMENT; }

}

[^] { return BAD_CHARACTER; }
