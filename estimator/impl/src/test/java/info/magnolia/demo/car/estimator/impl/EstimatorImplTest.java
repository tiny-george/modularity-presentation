package info.magnolia.demo.car.estimator.impl;

import info.magnolia.demo.car.estimator.CarValueEstimator;
import info.magnolia.demo.car.estimator.EstimatorTest;

public class EstimatorImplTest extends EstimatorTest {
    @Override
    protected CarValueEstimator newEstimator() {
        return new EstimatorImpl();
    }
}
