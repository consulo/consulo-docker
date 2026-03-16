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

package consulo.docker.language;

import consulo.docker.language.icon.DockerfileIconGroup;
import consulo.docker.language.localize.DockerfileLocalize;
import consulo.language.file.LanguageFileType;
import consulo.localize.LocalizeValue;
import consulo.ui.image.Image;
import jakarta.annotation.Nonnull;

public class DockerfileFileType extends LanguageFileType {
    public static final DockerfileFileType INSTANCE = new DockerfileFileType();

    protected DockerfileFileType() {
        super(DockerfileLanguage.INSTANCE);
    }

    @Override
    public @Nonnull String getId() {
        return "Dockerfile";
    }

    @Override
    public @Nonnull LocalizeValue getDescription() {
        return DockerfileLocalize.filetypeDockerfileDescription();
    }

    @Override
    public @Nonnull String getDefaultExtension() {
        return "Dockerfile";
    }

    @Override
    public Image getIcon() {
        return DockerfileIconGroup.dockerfile();
    }
}
