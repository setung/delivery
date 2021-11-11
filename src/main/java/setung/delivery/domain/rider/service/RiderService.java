package setung.delivery.domain.rider.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import setung.delivery.domain.rider.model.Rider;
import setung.delivery.domain.rider.repository.RiderRepository;

@Service
@AllArgsConstructor
public class RiderService {

    private final RiderRepository riderRepository;

    public Rider join(Rider rider) {
        return riderRepository.save(rider);
    }
}
