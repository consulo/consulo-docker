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

package consulo.docker.language.impl.psi;

import consulo.docker.language.DockerfileLanguage;
import consulo.docker.language.psi.DockerfileFile;
import consulo.language.file.FileViewProvider;
import consulo.language.impl.psi.PsiFileBase;
import consulo.virtualFileSystem.fileType.FileType;
import jakarta.annotation.Nonnull;

public final class DockerfileFileImpl extends PsiFileBase implements DockerfileFile {
    public DockerfileFileImpl(@Nonnull FileViewProvider viewProvider) {
        super(viewProvider, DockerfileLanguage.INSTANCE);
    }

    @Override
    @Nonnull
    public FileType getFileType() {
        return getViewProvider().getFileType();
    }

    @Override
    public String toString() {
        return "DockerfileFile: " + getName();
    }
}
