package info.magnolia.demo.car;

import info.magnolia.demo.car.estimator.Car;
import info.magnolia.demo.car.estimator.CarValueEstimator;
import info.magnolia.demo.car.estimator.EstimateRequest;

import java.math.BigDecimal;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/car")
public class CarResource {

    private static final BigDecimal PRICE_NEW = new BigDecimal("29000");
    private final CarRepository carRepository;
    private final CarValueEstimator carValueEstimator;

    @Inject
    public CarResource(CarRepository carRepository, CarValueEstimator carValueEstimator) {
        this.carRepository = carRepository;
        this.carValueEstimator = carValueEstimator;
    }

    @GET
    @Path("/{carId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response carData(@PathParam("carId") String carId) {
        var car = carRepository.byId(carId);
        return carValueEstimator.calculate(
                new EstimateRequest(new Car(car.brand(), car.manufactured().getYear(), car.kilometers()), PRICE_NEW))
                .map(nowValue -> new CarInfo(car.brand(), car.model(), car.manufactured(), car.kilometers(), PRICE_NEW, nowValue))
                .map(carInfo -> Response.ok(carInfo).build())
                .orElse(response -> Response.status(400, response.getFailuresToString()).build());
    }
}
