package setung.delivery.domain.rider.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import setung.delivery.controller.rider.dto.RiderDto;
import setung.delivery.domain.rider.model.Rider;
import setung.delivery.domain.rider.repository.RiderRepository;
import setung.delivery.exception.CustomException;
import setung.delivery.exception.ErrorCode;
import setung.delivery.utils.geo.GeocodingUtil;

@Service
@AllArgsConstructor
public class RiderService {

    private final RiderRepository riderRepository;
    private final GeocodingUtil geocodingUtil;

    public Rider join(RiderDto riderDto) {
        Rider rider = new Rider(riderDto);
        rider.setLatLon(geocodingUtil.getLatLon(riderDto.getAddress()));
        return riderRepository.save(rider);
    }

    public Rider findRiderById(long riderId) {
        return riderRepository.findById(riderId).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_RIDER));
    }
}
