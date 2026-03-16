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

package consulo.docker.language.impl.completion;

import consulo.annotation.component.ExtensionImpl;
import consulo.docker.language.DockerfileLanguage;
import consulo.docker.language.DockerfileTokenSets;
import consulo.language.Language;
import consulo.language.ast.IElementType;
import consulo.language.editor.completion.CompletionContributor;
import consulo.language.editor.completion.CompletionParameters;
import consulo.language.editor.completion.CompletionResultSet;
import consulo.language.editor.completion.lookup.LookupElementBuilder;
import consulo.language.impl.ast.TreeUtil;
import consulo.language.impl.parser.GeneratedParserUtilBase;
import consulo.language.psi.PsiFile;
import consulo.language.psi.PsiFileFactory;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.Collection;

@ExtensionImpl
public class DockerfileCompletionContributor extends CompletionContributor {
    @Override
    public void fillCompletionVariants(@Nonnull CompletionParameters parameters, @Nonnull CompletionResultSet result) {
        Collection<String> keywords = suggestKeywords(parameters);
        for (String keyword : keywords) {
            result.addElement(LookupElementBuilder.create(keyword).bold());
        }
    }

    @Nonnull
    private static Collection<String> suggestKeywords(@Nonnull CompletionParameters parameters) {
        PsiFile posFile = parameters.getOriginalFile();
        int completionOffset = parameters.getOffset();
        CharSequence text = posFile.getText();

        GeneratedParserUtilBase.CompletionState state = new GeneratedParserUtilBase.CompletionState(completionOffset) {
            @Nullable
            @Override
            public String convertItem(Object o) {
                if (o instanceof IElementType && DockerfileTokenSets.KEYWORDS.contains((IElementType) o)) {
                    return o.toString();
                }
                return null;
            }
        };

        PsiFile file = PsiFileFactory.getInstance(posFile.getProject())
                .createFileFromText("a.Dockerfile", DockerfileLanguage.INSTANCE, text, true, false);
        file.putUserData(GeneratedParserUtilBase.COMPLETION_STATE_KEY, state);
        TreeUtil.ensureParsed(file.getNode());

        return state.items;
    }

    @Nonnull
    @Override
    public Language getLanguage() {
        return DockerfileLanguage.INSTANCE;
    }
}
