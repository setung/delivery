package setung.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import setung.delivery.domain.rider.Rider;

public interface RiderRepository extends JpaRepository<Rider, Long> {
    Rider findByEmailAndPassword(String email, String password);
}
