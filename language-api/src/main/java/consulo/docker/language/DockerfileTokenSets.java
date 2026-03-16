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

import consulo.docker.language.psi.DockerfileTypes;
import consulo.language.ast.TokenSet;

public final class DockerfileTokenSets {
    public static final TokenSet COMMENTS = TokenSet.create(DockerfileTypes.COMMENT);

    public static final TokenSet STRING_LITERALS = TokenSet.create(
            DockerfileTypes.DOUBLE_QUOTED_STRING,
            DockerfileTypes.SINGLE_QUOTED_STRING
    );

    public static final TokenSet KEYWORDS = TokenSet.create(
            DockerfileTypes.FROM_KEYWORD, DockerfileTypes.RUN_KEYWORD, DockerfileTypes.CMD_KEYWORD,
            DockerfileTypes.LABEL_KEYWORD, DockerfileTypes.EXPOSE_KEYWORD, DockerfileTypes.ENV_KEYWORD,
            DockerfileTypes.ADD_KEYWORD, DockerfileTypes.COPY_KEYWORD, DockerfileTypes.ENTRYPOINT_KEYWORD,
            DockerfileTypes.VOLUME_KEYWORD, DockerfileTypes.USER_KEYWORD, DockerfileTypes.WORKDIR_KEYWORD,
            DockerfileTypes.ARG_KEYWORD, DockerfileTypes.ONBUILD_KEYWORD, DockerfileTypes.STOPSIGNAL_KEYWORD,
            DockerfileTypes.HEALTHCHECK_KEYWORD, DockerfileTypes.SHELL_KEYWORD, DockerfileTypes.MAINTAINER_KEYWORD,
            DockerfileTypes.AS_KEYWORD, DockerfileTypes.NONE_KEYWORD
    );

    private DockerfileTokenSets() {
    }
}
