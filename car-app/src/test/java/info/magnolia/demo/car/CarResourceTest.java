package info.magnolia.demo.car;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class CarResourceTest {

    @Test
    public void getCar() {
        var car = given().when().get("/car/1")
                .then().statusCode(200).log().all()
                .extract().as(Car.class);

        assertEquals(80000L, car.kilometers());
    }
}
