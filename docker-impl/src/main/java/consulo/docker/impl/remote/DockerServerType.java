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

import com.github.dockerjava.api.DockerClient;
import consulo.annotation.component.ExtensionImpl;
import consulo.configurable.ConfigurationException;
import consulo.disposer.Disposable;
import consulo.docker.impl.localize.DockerLocalize;
import consulo.docker.language.icon.DockerfileIconGroup;
import consulo.execution.configuration.ui.SettingsEditor;
import consulo.project.Project;
import consulo.remoteServer.RemoteServerConfigurable;
import consulo.remoteServer.ServerType;
import consulo.remoteServer.configuration.RemoteServer;
import consulo.remoteServer.configuration.deployment.DeploymentConfiguration;
import consulo.remoteServer.configuration.deployment.DeploymentConfigurator;
import consulo.remoteServer.configuration.deployment.DeploymentSource;
import consulo.remoteServer.configuration.deployment.DummyDeploymentConfiguration;
import consulo.remoteServer.runtime.ServerConnector;
import consulo.remoteServer.runtime.ServerTaskExecutor;
import consulo.ui.Component;
import consulo.ui.Label;
import consulo.ui.TextBox;
import consulo.ui.annotation.RequiredUIAccess;
import consulo.ui.layout.VerticalLayout;
import org.jspecify.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@ExtensionImpl
public class DockerServerType extends ServerType<DockerServerConfiguration> {
    public DockerServerType() {
        super("docker", "DockerDeployment", DockerLocalize.serverName(), DockerfileIconGroup.dockerfile());
    }

    @Override
    public DockerServerConfiguration createDefaultConfiguration() {
        return new DockerServerConfiguration();
    }

    @Override
    public boolean canAutoDetectConfiguration() {
        return true;
    }

    @Override
    public RemoteServerConfigurable createServerConfigurable(DockerServerConfiguration configuration) {
        return new RemoteServerConfigurable() {
            private TextBox myDockerHostField;
            private TextBox myCertPathField;

            @RequiredUIAccess
            @Nullable
            @Override
            public Component createUIComponent(Disposable uiDisposable) {
                VerticalLayout layout = VerticalLayout.create();
                layout.add(Label.create(DockerLocalize.dockerHostLabel()));
                myDockerHostField = TextBox.create(configuration.getDockerHost());
                layout.add(myDockerHostField);
                layout.add(Label.create(DockerLocalize.certificatesPathLabel()));
                myCertPathField = TextBox.create(configuration.getCertPath());
                layout.add(myCertPathField);
                return layout;
            }

            @RequiredUIAccess
            @Override
            public boolean isModified() {
                if (myDockerHostField == null) {
                    return false;
                }
                return !Objects.equals(configuration.getDockerHost(), myDockerHostField.getValue())
                        || !Objects.equals(configuration.getCertPath(), myCertPathField.getValue());
            }

            @RequiredUIAccess
            @Override
            public void apply() throws ConfigurationException {
                configuration.setDockerHost(myDockerHostField.getValue());
                configuration.setCertPath(myCertPathField.getValue());
            }

            @RequiredUIAccess
            @Override
            public void reset() {
                myDockerHostField.setValue(configuration.getDockerHost());
                myCertPathField.setValue(configuration.getCertPath());
            }
        };
    }

    @Override
    public DeploymentConfigurator<?, DockerServerConfiguration> createDeploymentConfigurator(Project project) {
        return new DeploymentConfigurator<DeploymentConfiguration, DockerServerConfiguration>() {
            @Override
            public List<DeploymentSource> getAvailableDeploymentSources() {
                return Collections.emptyList();
            }

            @Override
            public DeploymentConfiguration createDefaultConfiguration(DeploymentSource source) {
                return new DummyDeploymentConfiguration();
            }

            @Nullable
            @Override
            public SettingsEditor<DeploymentConfiguration> createEditor(DeploymentSource source,
                                                                        @Nullable RemoteServer<DockerServerConfiguration> server) {
                return null;
            }
        };
    }

    @Override
    public ServerConnector<?> createConnector(DockerServerConfiguration configuration, ServerTaskExecutor asyncTasksExecutor) {
        return new ServerConnector<DeploymentConfiguration>() {
            @Override
            public void connect(ConnectionCallback<DeploymentConfiguration> callback) {
                try {
                    DockerClient dockerClient = DockerClientFactory.createClient(configuration);
                    dockerClient.pingCmd().exec();
                    callback.connected(new DockerRuntimeInstance(configuration, dockerClient));
                }
                catch (Exception e) {
                    callback.errorOccurred(DockerLocalize.errorConnectFailed(e.getMessage()));
                }
            }
        };
    }
}
