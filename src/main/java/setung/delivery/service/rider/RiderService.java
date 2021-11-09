package setung.delivery.service.rider;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import setung.delivery.domain.rider.Rider;
import setung.delivery.repository.RiderRepository;

@Service
@AllArgsConstructor
public class RiderService {

    private final RiderRepository riderRepository;

    public Rider join(Rider rider) {
        return riderRepository.save(rider);
    }
}
