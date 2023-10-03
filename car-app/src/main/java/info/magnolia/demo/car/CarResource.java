package info.magnolia.demo.car;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/car")
public class CarResource {

    @Inject
    private CarRepository carRepository;

    @GET
    @Path("/{carId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Car carData(@PathParam("carId") String carId) {
        return carRepository.byId(carId);
    }
}