package info.magnolia.demo.car.estimator;

import java.math.BigDecimal;

public record EstimateRequest(Car car, BigDecimal priceNew) {
}
