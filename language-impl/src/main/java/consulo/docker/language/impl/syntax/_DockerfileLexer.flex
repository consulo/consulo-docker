/*
 * Copyright 2013-2026 consulo.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package consulo.docker.language.impl.syntax;

import consulo.language.ast.IElementType;
import consulo.language.ast.TokenType;
import consulo.language.lexer.LexerBase;
import consulo.docker.language.psi.DockerfileTypes;

%%

%public
%class _DockerfileLexer
%extends LexerBase
%function advanceImpl
%type IElementType
%unicode
%eof{  return;
%eof}

%state ARGS

WHITE_SPACE=[ \t]+
LINE_CONTINUATION=\\(\r\n|\r|\n)[ \t]*
NEWLINE=\r\n|\r|\n

DOUBLE_QUOTED_STRING=\"([^\\\"\r\n]|\\[^\r\n])*\"?
SINGLE_QUOTED_STRING='([^\\'\r\n]|\\[^\r\n])*'?
VARIABLE=\$\{[^}]+\}|\$[a-zA-Z_][a-zA-Z0-9_]*
FLAG=--[a-zA-Z][-a-zA-Z0-9]*
NUMBER=[0-9]+
IDENTIFIER=[a-zA-Z_][a-zA-Z0-9_]*([.\-/][a-zA-Z0-9_]+)*
COMMENT=#[^\r\n]*

%%

<YYINITIAL> {
  {WHITE_SPACE}               { return TokenType.WHITE_SPACE; }
  {NEWLINE}                   { return TokenType.WHITE_SPACE; }
  {LINE_CONTINUATION}         { return TokenType.WHITE_SPACE; }
  {COMMENT}                   { return DockerfileTypes.COMMENT; }

  [Ff][Rr][Oo][Mm]           { yybegin(ARGS); return DockerfileTypes.FROM_KEYWORD; }
  [Rr][Uu][Nn]               { yybegin(ARGS); return DockerfileTypes.RUN_KEYWORD; }
  [Cc][Mm][Dd]               { yybegin(ARGS); return DockerfileTypes.CMD_KEYWORD; }
  [Ll][Aa][Bb][Ee][Ll]       { yybegin(ARGS); return DockerfileTypes.LABEL_KEYWORD; }
  [Ee][Xx][Pp][Oo][Ss][Ee]   { yybegin(ARGS); return DockerfileTypes.EXPOSE_KEYWORD; }
  [Ee][Nn][Vv]               { yybegin(ARGS); return DockerfileTypes.ENV_KEYWORD; }
  [Aa][Dd][Dd]               { yybegin(ARGS); return DockerfileTypes.ADD_KEYWORD; }
  [Cc][Oo][Pp][Yy]           { yybegin(ARGS); return DockerfileTypes.COPY_KEYWORD; }
  [Ee][Nn][Tt][Rr][Yy][Pp][Oo][Ii][Nn][Tt] { yybegin(ARGS); return DockerfileTypes.ENTRYPOINT_KEYWORD; }
  [Vv][Oo][Ll][Uu][Mm][Ee]   { yybegin(ARGS); return DockerfileTypes.VOLUME_KEYWORD; }
  [Uu][Ss][Ee][Rr]           { yybegin(ARGS); return DockerfileTypes.USER_KEYWORD; }
  [Ww][Oo][Rr][Kk][Dd][Ii][Rr] { yybegin(ARGS); return DockerfileTypes.WORKDIR_KEYWORD; }
  [Aa][Rr][Gg]               { yybegin(ARGS); return DockerfileTypes.ARG_KEYWORD; }
  [Oo][Nn][Bb][Uu][Ii][Ll][Dd] { yybegin(ARGS); return DockerfileTypes.ONBUILD_KEYWORD; }
  [Ss][Tt][Oo][Pp][Ss][Ii][Gg][Nn][Aa][Ll] { yybegin(ARGS); return DockerfileTypes.STOPSIGNAL_KEYWORD; }
  [Hh][Ee][Aa][Ll][Tt][Hh][Cc][Hh][Ee][Cc][Kk] { yybegin(ARGS); return DockerfileTypes.HEALTHCHECK_KEYWORD; }
  [Ss][Hh][Ee][Ll][Ll]       { yybegin(ARGS); return DockerfileTypes.SHELL_KEYWORD; }
  [Mm][Aa][Ii][Nn][Tt][Aa][Ii][Nn][Ee][Rr] { yybegin(ARGS); return DockerfileTypes.MAINTAINER_KEYWORD; }
}

<ARGS> {
  {WHITE_SPACE}               { return TokenType.WHITE_SPACE; }
  {LINE_CONTINUATION}         { return TokenType.WHITE_SPACE; }
  {NEWLINE}                   { yybegin(YYINITIAL); return TokenType.WHITE_SPACE; }

  [Aa][Ss]                    { return DockerfileTypes.AS_KEYWORD; }
  [Nn][Oo][Nn][Ee]           { return DockerfileTypes.NONE_KEYWORD; }

  // Keywords for ONBUILD nesting and HEALTHCHECK CMD
  [Ff][Rr][Oo][Mm]           { return DockerfileTypes.FROM_KEYWORD; }
  [Rr][Uu][Nn]               { return DockerfileTypes.RUN_KEYWORD; }
  [Cc][Mm][Dd]               { return DockerfileTypes.CMD_KEYWORD; }
  [Ll][Aa][Bb][Ee][Ll]       { return DockerfileTypes.LABEL_KEYWORD; }
  [Ee][Xx][Pp][Oo][Ss][Ee]   { return DockerfileTypes.EXPOSE_KEYWORD; }
  [Ee][Nn][Vv]               { return DockerfileTypes.ENV_KEYWORD; }
  [Aa][Dd][Dd]               { return DockerfileTypes.ADD_KEYWORD; }
  [Cc][Oo][Pp][Yy]           { return DockerfileTypes.COPY_KEYWORD; }
  [Ee][Nn][Tt][Rr][Yy][Pp][Oo][Ii][Nn][Tt] { return DockerfileTypes.ENTRYPOINT_KEYWORD; }
  [Vv][Oo][Ll][Uu][Mm][Ee]   { return DockerfileTypes.VOLUME_KEYWORD; }
  [Uu][Ss][Ee][Rr]           { return DockerfileTypes.USER_KEYWORD; }
  [Ww][Oo][Rr][Kk][Dd][Ii][Rr] { return DockerfileTypes.WORKDIR_KEYWORD; }
  [Aa][Rr][Gg]               { return DockerfileTypes.ARG_KEYWORD; }
  [Oo][Nn][Bb][Uu][Ii][Ll][Dd] { return DockerfileTypes.ONBUILD_KEYWORD; }
  [Ss][Tt][Oo][Pp][Ss][Ii][Gg][Nn][Aa][Ll] { return DockerfileTypes.STOPSIGNAL_KEYWORD; }
  [Hh][Ee][Aa][Ll][Tt][Hh][Cc][Hh][Ee][Cc][Kk] { return DockerfileTypes.HEALTHCHECK_KEYWORD; }
  [Ss][Hh][Ee][Ll][Ll]       { return DockerfileTypes.SHELL_KEYWORD; }
  [Mm][Aa][Ii][Nn][Tt][Aa][Ii][Nn][Ee][Rr] { return DockerfileTypes.MAINTAINER_KEYWORD; }

  "="                         { return DockerfileTypes.EQ; }
  ":"                         { return DockerfileTypes.COLON; }
  "@"                         { return DockerfileTypes.AT; }
  ","                         { return DockerfileTypes.COMMA; }
  "["                         { return DockerfileTypes.L_BRACKET; }
  "]"                         { return DockerfileTypes.R_BRACKET; }

  {DOUBLE_QUOTED_STRING}      { return DockerfileTypes.DOUBLE_QUOTED_STRING; }
  {SINGLE_QUOTED_STRING}      { return DockerfileTypes.SINGLE_QUOTED_STRING; }
  {VARIABLE}                  { return DockerfileTypes.VARIABLE; }
  {FLAG}                      { return DockerfileTypes.FLAG_TOKEN; }
  {NUMBER}                    { return DockerfileTypes.NUMBER; }
  {IDENTIFIER}                { return DockerfileTypes.IDENTIFIER; }
  {COMMENT}                   { yybegin(YYINITIAL); return DockerfileTypes.COMMENT; }
}

[^]                           { return TokenType.BAD_CHARACTER; }
