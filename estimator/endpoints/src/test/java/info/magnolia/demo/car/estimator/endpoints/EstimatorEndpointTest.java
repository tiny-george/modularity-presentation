package info.magnolia.demo.car.estimator.endpoints;

import static io.restassured.RestAssured.with;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import info.magnolia.demo.car.estimator.Car;
import info.magnolia.demo.car.estimator.CarValueEstimator;
import info.magnolia.demo.car.estimator.EstimateRequest;

import java.math.BigDecimal;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import dev.yila.functional.Result;
import dev.yila.functional.failure.Failure;
import io.restassured.RestAssured;
import jakarta.ws.rs.core.Application;

public class EstimatorEndpointTest extends JerseyTest {

    private CarValueEstimator estimator;

    @Test
    void successCalculation() {
        var request = new EstimateRequest(new Car("", 2000, 100), new BigDecimal("20000"));
        var value = new BigDecimal("14897");
        given(estimator.calculate(request)).willReturn(Result.ok(value));
        with().body(request).header("Content-Type", "application/json").when()
                .post("/estimator/calculate")
                .then()
                .statusCode(200)
                .log().all()
                .body("value", is(14897));
    }

    @Test
    void failureCalculation() {
        var request = new EstimateRequest(new Car("", 2000, 100), new BigDecimal("20000"));
        given(estimator.calculate(request)).willReturn(Result.failure(Failure.create("code", "description")));
        with().body(request).header("Content-Type", "application/json").when()
                .post("/estimator/calculate")
                .then()
                .statusCode(400)
                .log().all()
                .body("error", is("[code: description]"));
    }

    @BeforeAll
    public static void setupRestAssures() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 9998;
    }

    @Override
    protected Application configure() {
        estimator = mock(CarValueEstimator.class);
        return new ResourceConfig(EstimatorEndpoint.class)
                .register(new AbstractBinder() {
                    @Override
                    protected void configure() {
                        bind(estimator).to(CarValueEstimator.class);
                    }
                });
    }
}
