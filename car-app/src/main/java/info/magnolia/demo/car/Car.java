package info.magnolia.demo.car;

import java.time.LocalDate;

public record Car(String id, String brand, String model, LocalDate manufactured, Long kilometers) {
}
