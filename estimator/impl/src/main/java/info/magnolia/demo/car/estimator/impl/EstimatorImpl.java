package info.magnolia.demo.car.estimator.impl;

import info.magnolia.demo.car.estimator.CarValueEstimator;
import info.magnolia.demo.car.estimator.EstimateRequest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

import dev.yila.functional.Result;
import dev.yila.functional.failure.Failure;

public class EstimatorImpl implements CarValueEstimator {

    private final Map<String, BigDecimal> brandQuality;

    public EstimatorImpl(Map<String, BigDecimal> brandQuality) {
        this.brandQuality = brandQuality;
    }

    @Override
    public Result<BigDecimal> calculate(EstimateRequest request) {
        var brand = request.car().brand();
        if (brand == null) {
            return Result.failure(Failure.create("car.emptyBrand", "Null brand"));
        }
        if (!brandQuality.containsKey(brand)) {
            return Result.failure(Failure.create("car.unknownBrand", "Brand not supported: " + brand));
        }
        return Result.ok(calculate(
                this.brandQuality.get(brand),
                LocalDate.now().getYear() - request.car().manufacturedYear(),
                request.car().kilometers(),
                request.priceNew()));
    }

    private BigDecimal calculate(BigDecimal brandQuality, int yearsOld, long kilometers, BigDecimal priceNew) {
        var kmFactor = kilometers * 100 / 1000000;
        var yearsFactor = yearsOld * 100 / 20;
        var factor = (kmFactor + yearsFactor) > 100 ?
                new BigDecimal("0.01") :
                new BigDecimal(100 - (kmFactor + yearsFactor)).divide(new BigDecimal("100"));
        return brandQuality.multiply(priceNew.multiply(factor));
    }
}
