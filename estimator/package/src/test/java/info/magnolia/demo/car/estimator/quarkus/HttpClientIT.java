package info.magnolia.demo.car.estimator.quarkus;

import info.magnolia.demo.car.estimator.CarValueEstimator;
import info.magnolia.demo.car.estimator.EstimatorTest;
import info.magnolia.demo.car.estimator.httpClient.EstimatorHttpClient;
import info.magnolia.demo.car.estimator.httpClient.EstimatorRestClient;

import java.net.URI;

import org.eclipse.microprofile.rest.client.RestClientBuilder;

import io.quarkus.test.junit.QuarkusIntegrationTest;

@QuarkusIntegrationTest
public class HttpClientIT extends EstimatorTest {

    @Override
    protected CarValueEstimator newEstimator() {
        var restClient = RestClientBuilder.newBuilder()
                .baseUri(URI.create("http://localhost:8081"))
                .build(EstimatorRestClient.class);
        return new EstimatorHttpClient(restClient);
    }
}
