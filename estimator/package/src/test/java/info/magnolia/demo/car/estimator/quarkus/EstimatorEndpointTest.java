package info.magnolia.demo.car.estimator.quarkus;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.is;

import info.magnolia.demo.car.estimator.Car;
import info.magnolia.demo.car.estimator.EstimateRequest;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class EstimatorEndpointTest {

    @Test
    public void calculate() {
        var request = new EstimateRequest(new Car("Volkswagen", 2016, 100000), new BigDecimal("23500"));
        with().body(request).header("Content-Type", "application/json").when()
                .post("/estimator/calculate")
                .then()
                .statusCode(200)
                .log().all()
                .body("value", is(9047.500f));
    }
}
