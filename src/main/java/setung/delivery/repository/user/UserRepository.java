package setung.delivery.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import setung.delivery.domain.user.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
}
