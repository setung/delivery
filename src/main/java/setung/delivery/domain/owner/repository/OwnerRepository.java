package setung.delivery.domain.owner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import setung.delivery.domain.owner.model.Owner;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {
    Owner findByEmailAndPassword(String email, String password);
}
