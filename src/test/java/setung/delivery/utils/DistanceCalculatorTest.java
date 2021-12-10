package setung.delivery.utils;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DistanceCalculatorTest {

    @Test
    public void calculateDistance() {
        double distance = DistanceCalculator.distance(37.48122277083822, 126.9526690847144, 37.133953425227354, 127.24647193563443);
        double distance2 = DistanceCalculator.distance(37.133953425227354, 127.24647193563443, 37.48122277083822, 126.9526690847144);
        Assertions.assertThat(distance).isEqualTo(distance2);
    }
}