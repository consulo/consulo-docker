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

package consulo.docker.impl.remote;

import consulo.component.persist.PersistentStateComponent;
import consulo.remoteServer.configuration.ServerConfiguration;
import org.jspecify.annotations.Nullable;

public class DockerServerConfiguration extends ServerConfiguration {
    private State myState = new State();

    public String getDockerHost() {
        return myState.dockerHost;
    }

    public void setDockerHost(String dockerHost) {
        myState.dockerHost = dockerHost;
    }

    public String getCertPath() {
        return myState.certPath;
    }

    public void setCertPath(String certPath) {
        myState.certPath = certPath;
    }

    @Override
    public PersistentStateComponent<?> getSerializer() {
        return new PersistentStateComponent<State>() {
            @Nullable
            @Override
            public State getState() {
                return myState;
            }

            @Override
            public void loadState(State state) {
                myState = state;
            }
        };
    }

    public static class State {
        public String dockerHost = "unix:///var/run/docker.sock";
        public String certPath = "";
    }
}
