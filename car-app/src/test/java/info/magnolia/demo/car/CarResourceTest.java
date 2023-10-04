package info.magnolia.demo.car;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import info.magnolia.demo.car.estimator.CarValueEstimator;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import dev.yila.functional.Result;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class CarResourceTest {

    @InjectMock
    CarValueEstimator carValueEstimator;

    @Test
    public void getFirstCar() {
        when(carValueEstimator.calculate(any())).thenReturn(Result.ok(new BigDecimal("13601")));
        var car = given().when().get("/car/1")
                .then().statusCode(200).log().all()
                .extract().as(CarInfo.class);

        assertEquals(80000L, car.kilometers());
        assertEquals(29000, car.priceNew().intValue());
        assertEquals(13601, car.priceNow().intValue());
    }

    @Test
    public void getSecondCar() {
        when(carValueEstimator.calculate(any())).thenReturn(Result.ok(new BigDecimal("15486")));
        var car = given().when().get("/car/2")
                .then().statusCode(200).log().all()
                .extract().as(CarInfo.class);

        assertEquals(10000L, car.kilometers());
        assertEquals(29000, car.priceNew().intValue());
        assertEquals(15486, car.priceNow().intValue());
    }
}
