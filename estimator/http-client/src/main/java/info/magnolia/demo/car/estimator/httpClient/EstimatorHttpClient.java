package info.magnolia.demo.car.estimator.httpClient;

import info.magnolia.demo.car.estimator.CarValueEstimator;
import info.magnolia.demo.car.estimator.EstimateRequest;

import java.math.BigDecimal;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import dev.yila.functional.Result;
import dev.yila.functional.failure.Failure;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class EstimatorHttpClient implements CarValueEstimator {

    private final EstimatorRestClient estimatorRestClient;

    @Inject
    public EstimatorHttpClient(@RestClient EstimatorRestClient estimatorRestClient) {
        this.estimatorRestClient = estimatorRestClient;
    }

    @Override
    public Result<BigDecimal> calculate(EstimateRequest request) {
        try (var response = estimatorRestClient.calculate(request)) {
            if (response.getStatusInfo().getFamily().equals(Response.Status.Family.SUCCESSFUL)) {
                var price = response.readEntity(PriceValue.class);
                return Result.ok(price.value);
            } else {
                var message = response.readEntity(ErrorMessage.class);
                return Result.failure(Failure.create("error.http.client", message.error()));
            }
        } catch (WebApplicationException webApplicationException) {
            return Result.failure(webApplicationException);
        }
    }

    record PriceValue(BigDecimal value) {}
    record ErrorMessage(String error) {}
}
