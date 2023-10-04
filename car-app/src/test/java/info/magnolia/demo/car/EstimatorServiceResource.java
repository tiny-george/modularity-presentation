package info.magnolia.demo.car;

import java.util.Map;
import java.util.Optional;

import org.testcontainers.containers.GenericContainer;

import io.quarkus.test.common.DevServicesContext;
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;

public class EstimatorServiceResource implements QuarkusTestResourceLifecycleManager, DevServicesContext.ContextAware {

    private final static Integer PORT = 9000;

    private DevServicesContext context;
    private GenericContainer container;

    @Override
    public void setIntegrationTestContext(DevServicesContext context) {
        this.context = context;
    }

    @Override
    public Map<String, String> start() {
        container = new GenericContainer<>("demo/estimator-service:latest");
        Optional.ofNullable(context.devServicesProperties().get("network.mode"))
                .ifPresent(container::withNetworkMode);
        container.withEnv(Map.of("QUARKUS_HTTP_PORT", PORT.toString()));
        container.withExposedPorts(PORT);
        container.start();
        var port = container.getMappedPort(PORT);
        var host = container.getHost();
        var url = "http://%s:%s".formatted(host, port.toString());
        return Map.of("quarkus.rest-client.estimator-service.url", url);
    }

    @Override
    public void stop() {
        if (container != null) {
            System.out.println("------------------------- Estimator service logs ---- Begin ------------------");
            System.out.println(container.getLogs());
            System.out.println("----------------------------------------------------- End --------------------");
            container.stop();
        }
    }
}
