package com.senpure.io.support.plugin.intellij.lexer;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;

import static com.intellij.psi.TokenType.BAD_CHARACTER;
import static com.intellij.psi.TokenType.WHITE_SPACE;
import static com.senpure.io.support.plugin.intellij.psi.IoTypes.*;

%%

%{
 private int lastState;
  public _IoSyntaxHighlighterLexer() {
    this((java.io.Reader)null);
  }
%}

%public
%class _IoSyntaxHighlighterLexer
%implements FlexLexer
%function advance
%type IElementType
%unicode

EOL=\R
WHITE_SPACE=\s+

MESSAGE_TYPE_CS=CS|cs
MESSAGE_TYPE_SC=SC|sc
DIGIT=[0-9]+
FIELD_TYPE_BASE=int|long|sint|slong|sfixed32|sfixed64|float|double|boolean|String|string
IDENTIFIER=[a-zA-Z][a-zA-Z0-9_]*
HEAD_VALUE=[a-zA-Z0-9_\./\\]+
IMPORT_VALUE=[a-zA-Z0-9\-_\./\\]+
LINE_COMMENT="//"[^\r\n]*
CODE_COMMENT=#[^\r\n]*
%state MESSAGE_TAG
%state FIELD_TAG
%state FIELD_NAME_TAG
%state BEAN_TAG
%state EVENT_TAG
%state ENUM_TAG
%state ENUM_FIELD_TAG
%state IMPORT_TAG
%state NAMEPACE_TAG
%state JAVA_PACK_TAG
%state LUA_NAMESPACE_TAG
%state ENTITY_TAG
%%
<YYINITIAL,ENTITY_TAG> {
   {WHITE_SPACE}          { return WHITE_SPACE; }
   {LINE_COMMENT}         {  yybegin(ENTITY_TAG); return T_LINE_COMMENT; }
   {CODE_COMMENT}         {  yybegin(ENTITY_TAG); return T_CODE_COMMENT; }
    "import"              {  yybegin(IMPORT_TAG);return  T_IMPORT_HEAD;}
    "namespace"           {  yybegin(NAMEPACE_TAG); return  T_NAMESPACE_HEAD;}
    "javaPack"            {  yybegin(JAVA_PACK_TAG); return  T_JAVA_PACK_HEAD;}
   "luaNamespace"         {  yybegin(LUA_NAMESPACE_TAG); return  T_LUA_NAMESPACE_HEAD;}
   "message"              {  yybegin(MESSAGE_TAG); return  T_MESSAGE_HEAD; }
   "bean"                 {  yybegin(BEAN_TAG); return  T_BEAN_HEAD; }
   "event"                {  yybegin(EVENT_TAG); return  T_EVENT_HEAD; }
   "enum"                 {  yybegin(ENUM_TAG); return  T_ENUM_HEAD; }
   {IDENTIFIER}           {  return T_IDENTIFIER; }

}

<IMPORT_TAG>
{
 {WHITE_SPACE}           { return WHITE_SPACE; }
 {IMPORT_VALUE}".io"       { return T_IMPORT_VALUE;}
  ";"                     { yybegin(YYINITIAL);return T_SEMICOLON;}
}
<NAMEPACE_TAG>
{
    {WHITE_SPACE}          { return WHITE_SPACE; }
    {HEAD_VALUE}         { return T_NAMESPACE_VALUE;}
    ";"                  { yybegin(YYINITIAL);return T_SEMICOLON;}
}
<JAVA_PACK_TAG>
{
  {WHITE_SPACE}          { return WHITE_SPACE; }
  {HEAD_VALUE}           { return T_JAVA_PACK_VALUE;}
  ";"                     { yybegin(YYINITIAL);return T_SEMICOLON;}
 }
 <LUA_NAMESPACE_TAG>
 {
   {WHITE_SPACE}          { return WHITE_SPACE; }
   {HEAD_VALUE}           { return T_LUA_NAMESPACE_VALUE;}
   ";"                     { yybegin(YYINITIAL);return T_SEMICOLON;}
  }
<MESSAGE_TAG>
{
   {WHITE_SPACE}          { return WHITE_SPACE; }
   {MESSAGE_TYPE_CS}      { return T_MESSAGE_TYPE_CS; }
   {MESSAGE_TYPE_SC}      { return T_MESSAGE_TYPE_SC; }
   {IDENTIFIER}           { return T_MESSAGE_NAME; }
   {DIGIT}                { return T_MESSAGE_ID; }
   "{"                    { yybegin(FIELD_TAG); return T_LEFT_BRACE; }

}
<FIELD_TAG>{
  {WHITE_SPACE}          { return WHITE_SPACE; }
  {FIELD_TYPE_BASE}      { yybegin(FIELD_NAME_TAG);return T_FIELD_TYPE_BASE; }
  {IDENTIFIER}           { yybegin(FIELD_NAME_TAG); return T_FIELD_TYPE_QUOTE; }
  {LINE_COMMENT}         { yybegin(FIELD_TAG);return T_FIELD_COMMENT;}
  {CODE_COMMENT}         { yybegin(FIELD_TAG); return T_CODE_COMMENT; }

}
<FIELD_NAME_TAG>{
 {WHITE_SPACE}           { return WHITE_SPACE; }
 "["                     { return T_LEFT_BRACKET; }
 "]"                     { return T_RIGHT_BRACKET; }
 {IDENTIFIER}            { return T_FIELD_NAME; }
 "="                     { return T_EQUAL; }
 {DIGIT}                 { return T_FIELD_INDEX; }
 ";"                     { yybegin(FIELD_TAG);return T_SEMICOLON;}
 "}"                     { yybegin(YYINITIAL);return T_RIGHT_BRACE; }
}
<BEAN_TAG>
{
   {WHITE_SPACE}          { return WHITE_SPACE; }
   {IDENTIFIER}           { return T_BEAN_NAME; }
   "{"                    { yybegin(FIELD_TAG); return T_LEFT_BRACE; }
}
<EVENT_TAG>
{
   {WHITE_SPACE}          { return WHITE_SPACE; }
   {IDENTIFIER}           { return T_EVENT_NAME; }
   {DIGIT}                { return T_EVENT_ID; }
   "{"                    { yybegin(FIELD_TAG); return T_LEFT_BRACE; }
}
<ENUM_TAG>
{
   {WHITE_SPACE}          { return WHITE_SPACE; }
   {IDENTIFIER}           { return T_ENUM_NAME; }
   "{"                    { yybegin(ENUM_FIELD_TAG); return T_LEFT_BRACE; }

}
<ENUM_FIELD_TAG>{
 {WHITE_SPACE}           { return WHITE_SPACE; }
 {IDENTIFIER}            { return T_FIELD_NAME; }
 "="                     { return T_EQUAL; }
 {DIGIT}                 { return T_FIELD_INDEX; }
 ";"                     { return T_SEMICOLON;}
  {LINE_COMMENT}         { return T_FIELD_COMMENT;}
  {CODE_COMMENT}         { return T_CODE_COMMENT; }
 "}"                     { yybegin(YYINITIAL);return T_RIGHT_BRACE; }
}

{WHITE_SPACE}          { return WHITE_SPACE; }
{LINE_COMMENT}         { return T_LINE_COMMENT; }
{CODE_COMMENT}         { return T_CODE_COMMENT; }
"["                    { return T_LEFT_BRACKET; }
"]"                    { return T_RIGHT_BRACKET; }
"{"                    { yybegin(FIELD_TAG); return T_LEFT_BRACE; }
 ";"                   { yybegin(FIELD_TAG);return T_SEMICOLON;}
"}"                    { yybegin(YYINITIAL);return T_RIGHT_BRACE; }
[^]                    {return BAD_CHARACTER;}
