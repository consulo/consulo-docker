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

package consulo.docker.language.impl.highlighting;

import consulo.annotation.component.ExtensionImpl;
import consulo.codeEditor.HighlighterColors;
import consulo.colorScheme.TextAttributesKey;
import consulo.docker.language.DockerfileLanguage;
import consulo.docker.language.impl.DockerfileLexer;
import consulo.docker.language.psi.DockerfileTypes;
import consulo.language.Language;
import consulo.language.ast.IElementType;
import consulo.language.ast.TokenType;
import consulo.language.editor.highlight.SyntaxHighlighter;
import consulo.language.editor.highlight.SyntaxHighlighterBase;
import consulo.language.editor.highlight.SyntaxHighlighterFactory;
import consulo.language.lexer.Lexer;
import consulo.project.Project;
import consulo.virtualFileSystem.VirtualFile;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

import static consulo.codeEditor.DefaultLanguageHighlighterColors.*;

@ExtensionImpl
public class DockerfileSyntaxHighlighterFactory extends SyntaxHighlighterFactory {
    @Nonnull
    @Override
    public SyntaxHighlighter getSyntaxHighlighter(@Nullable Project project, @Nullable VirtualFile virtualFile) {
        return new DockerfileSyntaxHighlighter();
    }

    @Nonnull
    @Override
    public Language getLanguage() {
        return DockerfileLanguage.INSTANCE;
    }

    private static class DockerfileSyntaxHighlighter extends SyntaxHighlighterBase {
        private static final Map<IElementType, TextAttributesKey> ourAttributes = new HashMap<>();

        static {
            fillMap(ourAttributes, KEYWORD,
                    DockerfileTypes.FROM_KEYWORD, DockerfileTypes.RUN_KEYWORD, DockerfileTypes.CMD_KEYWORD,
                    DockerfileTypes.LABEL_KEYWORD, DockerfileTypes.EXPOSE_KEYWORD, DockerfileTypes.ENV_KEYWORD,
                    DockerfileTypes.ADD_KEYWORD, DockerfileTypes.COPY_KEYWORD, DockerfileTypes.ENTRYPOINT_KEYWORD,
                    DockerfileTypes.VOLUME_KEYWORD, DockerfileTypes.USER_KEYWORD, DockerfileTypes.WORKDIR_KEYWORD,
                    DockerfileTypes.ARG_KEYWORD, DockerfileTypes.ONBUILD_KEYWORD, DockerfileTypes.STOPSIGNAL_KEYWORD,
                    DockerfileTypes.HEALTHCHECK_KEYWORD, DockerfileTypes.SHELL_KEYWORD, DockerfileTypes.MAINTAINER_KEYWORD,
                    DockerfileTypes.AS_KEYWORD, DockerfileTypes.NONE_KEYWORD);
            fillMap(ourAttributes, LINE_COMMENT, DockerfileTypes.COMMENT);
            fillMap(ourAttributes, STRING, DockerfileTypes.DOUBLE_QUOTED_STRING, DockerfileTypes.SINGLE_QUOTED_STRING);
            fillMap(ourAttributes, NUMBER, DockerfileTypes.NUMBER);
            fillMap(ourAttributes, INSTANCE_FIELD, DockerfileTypes.VARIABLE);
            fillMap(ourAttributes, METADATA, DockerfileTypes.FLAG_TOKEN);
            fillMap(ourAttributes, BRACKETS, DockerfileTypes.L_BRACKET, DockerfileTypes.R_BRACKET);
            fillMap(ourAttributes, COMMA, DockerfileTypes.COMMA);
            fillMap(ourAttributes, OPERATION_SIGN, DockerfileTypes.EQ);
            fillMap(ourAttributes, HighlighterColors.BAD_CHARACTER, TokenType.BAD_CHARACTER);
        }

        @Nonnull
        @Override
        public Lexer getHighlightingLexer() {
            return new DockerfileLexer();
        }

        @Nonnull
        @Override
        public TextAttributesKey[] getTokenHighlights(IElementType tokenType) {
            return pack(ourAttributes.get(tokenType));
        }
    }
}
