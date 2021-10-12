package setung.delivery.domain.user;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
//@Rollback(value = false)
class UserTest {

    @PersistenceContext
    EntityManager em;

    @Test
    public void saveTest() {
        User userA = User.builder().name("userA")
                .email("user@user.com")
                .address("address")
                .tel("000-0000-1234")
                .build();

        em.persist(userA);
    }
}