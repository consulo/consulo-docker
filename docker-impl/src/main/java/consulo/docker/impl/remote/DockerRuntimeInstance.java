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
import com.github.dockerjava.api.model.Container;
import consulo.docker.impl.localize.DockerLocalize;
import consulo.remoteServer.configuration.deployment.DeploymentConfiguration;
import consulo.remoteServer.runtime.deployment.DeploymentLogManager;
import consulo.remoteServer.runtime.deployment.DeploymentStatus;
import consulo.remoteServer.runtime.deployment.DeploymentTask;
import consulo.remoteServer.runtime.deployment.ServerRuntimeInstance;

import java.io.IOException;
import java.util.List;

public class DockerRuntimeInstance extends ServerRuntimeInstance<DeploymentConfiguration> {
    private final DockerServerConfiguration myConfiguration;
    private final DockerClient myDockerClient;

    public DockerRuntimeInstance(DockerServerConfiguration configuration, DockerClient dockerClient) {
        myConfiguration = configuration;
        myDockerClient = dockerClient;
    }

    @Override
    public void deploy(DeploymentTask<DeploymentConfiguration> task, DeploymentLogManager logManager, DeploymentOperationCallback callback) {
        callback.errorOccurred(DockerLocalize.errorDeployNotSupported().get());
    }

    @Override
    public void computeDeployments(ComputeDeploymentsCallback callback) {
        try {
            List<Container> containers = myDockerClient.listContainersCmd().withShowAll(true).exec();
            for (Container container : containers) {
                String state = container.getState();
                DeploymentStatus status = "running".equalsIgnoreCase(state)
                        ? DeploymentStatus.DEPLOYED
                        : DeploymentStatus.NOT_DEPLOYED;

                String name = container.getNames() != null && container.getNames().length > 0
                        ? container.getNames()[0].replaceFirst("^/", "")
                        : container.getId();

                callback.addDeployment(
                        name,
                        new DockerContainerRuntime(myDockerClient, container.getId()),
                        status,
                        container.getStatus()
                );
            }
            callback.succeeded();
        }
        catch (Exception e) {
            callback.errorOccurred(DockerLocalize.errorListContainersFailed(e.getMessage()).get());
        }
    }

    @Override
    public void disconnect() {
        try {
            myDockerClient.close();
        }
        catch (IOException ignored) {
        }
    }
}
