package setung.delivery.repository.owner;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import setung.delivery.domain.owner.Owner;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {
}
