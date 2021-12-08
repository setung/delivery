package setung.delivery.utils.geo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import setung.delivery.exception.CustomException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GeocodingUtilTest {

    @Autowired
    GeocodingUtil geocodingUtil;

    @Test
    public void getGeoData() {
        GeoData geoData = geocodingUtil.getGeoDataByAddress("서울 강남구 영동대로 513");
        System.out.println(geoData);
    }

    @Test
    public void getGeoDataWithWrongAddress() {
        Assertions.assertThatThrownBy(()->geocodingUtil.getGeoDataByAddress("서울군 강남구 영동대로 513"))
                .isInstanceOf(CustomException.class);
    }
}