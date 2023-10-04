package info.magnolia.demo.car.estimator.httpClient;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

import info.magnolia.demo.car.estimator.EstimateRequest;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

@RegisterRestClient(configKey="estimator-service")
@Path("/estimator")
public interface EstimatorRestClient {

    @POST
    @Path("/calculate")
    @Produces(APPLICATION_JSON)
    @Consumes(APPLICATION_JSON)
    Response calculate(EstimateRequest request);
}
