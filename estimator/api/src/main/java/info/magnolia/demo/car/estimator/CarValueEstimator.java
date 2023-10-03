package info.magnolia.demo.car.estimator;

import java.math.BigDecimal;
import dev.yila.functional.Result;

public interface CarValueEstimator {
    Result<BigDecimal> calculate(EstimateRequest request);
}
