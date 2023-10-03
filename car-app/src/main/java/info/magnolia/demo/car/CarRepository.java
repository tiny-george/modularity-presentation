package info.magnolia.demo.car;

import java.time.LocalDate;
import java.time.Month;
import java.util.Map;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CarRepository {

    private final Map<String, Car> cars = Map.of(
            "1", new Car("1", "Volkswagen", "Golf", LocalDate.of(2018, Month.APRIL, 12), 80000L),
            "2", new Car("2", "KIA", "Rio", LocalDate.of(2021, Month.NOVEMBER, 17), 10000L)
            );

    public Car byId(String id) {
        return cars.get(id);
    }
}
