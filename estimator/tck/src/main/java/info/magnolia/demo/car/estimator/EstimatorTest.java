package info.magnolia.demo.car.estimator;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.Map;

import org.junit.jupiter.api.Test;

public abstract class EstimatorTest {

    private final static String GOOD_BRAND = "Volkswagen";
    private final static String NOT_SO_GOOD_BRAND = "Kia";
    private final static BigDecimal PRICE_NEW = new BigDecimal("29000");
    private final static Long LOW_KILOMETERS = 10000L;
    private final static Long BIG_KILOMETERS = 30000L;
    private final static int YEAR = 2021;

    private final Map<String, BigDecimal> BRAND_QUALITY = Map.of(
            GOOD_BRAND, new BigDecimal("0.7"),
            NOT_SO_GOOD_BRAND, new BigDecimal("0.6"));

    @Test
    public void calculation() {
        var estimator = newEstimator();
        assertEquals(17661, estimator.calculate(
                new EstimateRequest(new Car(GOOD_BRAND, YEAR, BIG_KILOMETERS), PRICE_NEW)
        ).get().intValue());
        assertEquals(13746, estimator.calculate(
                new EstimateRequest(new Car(NOT_SO_GOOD_BRAND, YEAR - 2, LOW_KILOMETERS), PRICE_NEW)
        ).get().intValue());
    }

    @Test
    public void moreKilometersHasLessValue() {
        var estimator = newEstimator();
        var moreKms = estimator.calculate(new EstimateRequest(new Car(GOOD_BRAND, YEAR, BIG_KILOMETERS), PRICE_NEW)).get();
        var lessKms = estimator.calculate(new EstimateRequest(new Car(GOOD_BRAND, YEAR, LOW_KILOMETERS), PRICE_NEW)).get();

        assertTrue(lessKms.compareTo(moreKms) > 0);
    }

    @Test
    public void cannotCalculateValue() {
        var estimator = newEstimator();
        assertTrue(estimator.calculate(new EstimateRequest(new Car(null, YEAR, BIG_KILOMETERS), PRICE_NEW)).hasFailures());
        assertTrue(estimator.calculate(new EstimateRequest(new Car("unknownBrand", YEAR, BIG_KILOMETERS), PRICE_NEW)).hasFailures());
    }

    @Test
    public void notSoGoodBrandHasLessValue() {
        var estimator = newEstimator(BRAND_QUALITY);
        var good = estimator.calculate(new EstimateRequest(new Car(GOOD_BRAND, YEAR, LOW_KILOMETERS), PRICE_NEW)).get();
        var notSoGood = estimator.calculate(new EstimateRequest(new Car(NOT_SO_GOOD_BRAND, YEAR, LOW_KILOMETERS), PRICE_NEW)).get();

        assertTrue(good.compareTo(notSoGood) > 0);
    }

    @Test
    public void olderCarHasLessValue() {
        var estimator = newEstimator();
        var younger = estimator.calculate(new EstimateRequest(new Car(GOOD_BRAND, 2021, LOW_KILOMETERS), PRICE_NEW)).get();
        var older = estimator.calculate(new EstimateRequest(new Car(GOOD_BRAND, 2017, LOW_KILOMETERS), PRICE_NEW)).get();

        assertTrue(younger.compareTo(older) > 0);
    }

    protected CarValueEstimator newEstimator() {
        return newEstimator(BRAND_QUALITY);
    }

    protected abstract CarValueEstimator newEstimator(Map<String, BigDecimal> brandQuality);
}
