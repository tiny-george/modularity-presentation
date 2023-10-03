package info.magnolia.demo.car.estimator.impl;

import info.magnolia.demo.car.estimator.CarValueEstimator;
import info.magnolia.demo.car.estimator.EstimatorTest;

import java.math.BigDecimal;
import java.util.Map;

public class EstimatorImplTest extends EstimatorTest {
    @Override
    protected CarValueEstimator newEstimator(Map<String, BigDecimal> brandQuality) {
        return new EstimatorImpl(brandQuality);
    }
}
