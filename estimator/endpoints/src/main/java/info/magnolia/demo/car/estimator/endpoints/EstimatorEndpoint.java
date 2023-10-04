package info.magnolia.demo.car.estimator.endpoints;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

import info.magnolia.demo.car.estimator.CarValueEstimator;
import info.magnolia.demo.car.estimator.EstimateRequest;

import java.util.Map;

import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

@Path("/estimator")
public class EstimatorEndpoint {

    private final CarValueEstimator estimator;

    @Inject
    public EstimatorEndpoint(CarValueEstimator estimator) {
        this.estimator = estimator;
    }

    @POST
    @Path("/calculate")
    @Produces(APPLICATION_JSON)
    @Consumes(APPLICATION_JSON)
    @PermitAll
    public Response calculate(EstimateRequest request) {
        return this.estimator.calculate(request)
                .map(value -> Map.of("value", value))
                .map(map -> Response.ok(map).build())
                .orElse(result -> Response.status(400)
                        .entity(Map.of("error", result.getFailuresToString())).build());
    }
}
