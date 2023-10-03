package info.magnolia.demo.car;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CarInfo(String brand, String model, LocalDate manufactured, Long kilometers, BigDecimal priceNew, BigDecimal priceNow) {
}
