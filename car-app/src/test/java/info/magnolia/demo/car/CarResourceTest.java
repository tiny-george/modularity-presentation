package info.magnolia.demo.car;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class CarResourceTest {

    @Test
    public void getFirstCar() {
        var car = given().when().get("/car/1")
                .then().statusCode(200).log().all()
                .extract().as(CarInfo.class);

        assertEquals(80000L, car.kilometers());
        assertEquals(29000, car.priceNew().intValue());
        assertEquals(13601, car.priceNow().intValue());
    }

    @Test
    public void getSecondCar() {
        var car = given().when().get("/car/2")
                .then().statusCode(200).log().all()
                .extract().as(CarInfo.class);

        assertEquals(10000L, car.kilometers());
        assertEquals(29000, car.priceNew().intValue());
        assertEquals(15486, car.priceNow().intValue());
    }
}
