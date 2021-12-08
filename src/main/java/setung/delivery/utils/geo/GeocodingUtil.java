package setung.delivery.utils.geo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import setung.delivery.exception.CustomException;
import setung.delivery.exception.ErrorCode;

@Component
public class GeocodingUtil {

    @Value("${geo.api.id}")
    private String API_KEY_ID;
    @Value("${geo.api.key}")
    private String API_KEY;
    @Value("${geo.api.url}")
    private String API_URL;

    public GeoData getGeoDataByAddress(String completeAddress) {
        if (completeAddress == null || completeAddress.isBlank() || completeAddress.isEmpty())
            throw new CustomException(ErrorCode.BAD_REQUEST_ADDRESS, completeAddress);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-NCP-APIGW-API-KEY-ID", API_KEY_ID);
        headers.set("X-NCP-APIGW-API-KEY", API_KEY);

        HttpEntity request = new HttpEntity(headers);

        ResponseEntity<GeoData> response = restTemplate.exchange(API_URL + completeAddress, HttpMethod.GET, request, GeoData.class);

        GeoData geoData = response.getBody();
        if (geoData.getMeta().getCount() == 0)
            throw new CustomException(ErrorCode.BAD_REQUEST_ADDRESS, completeAddress);

        return response.getBody();
    }

    public LatLonData getLatLon(String completeAddress) {
        GeoData geoData = getGeoDataByAddress(completeAddress);
        GeoData.Address[] addresses = geoData.getAddresses();
        double lat = addresses[0].getX();
        double lon = addresses[0].getY();
        return new LatLonData(lat, lon);
    }

}
