package setung.delivery.repository.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import setung.delivery.domain.user.User;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("정상적인 회원 가입")
    public void save() {
        //given
        User user = User.builder()
                .name("user")
                .address("address")
                .email("user@user.com")
                .tel("010-0000-0000")
                .build();

        long beforeCount = userRepository.count();

        //when
        userRepository.save(user);
        User findUser = userRepository.findById(user.getId()).get();

        //then
        assertThat(userRepository.count()).isEqualTo(beforeCount + 1);
        assertThat(user.getId()).isEqualTo(findUser.getId());
        assertThat(user).isEqualTo(findUser);
    }


    /**
     * 롤백 설정에 따라 결과가 다름.
     */
    @Test
    @DisplayName("중복되는 이메일로 회원 가입시 예외 발생")
    public void saveUserWithDuplicatedEmail() {
        //given
        User userA = User.builder()
                .name("userA")
                .address("address")
                .email("user@user.com")
                .tel("010-0000-0000")
                .build();

        User userB = User.builder()
                .name("userB")
                .address("address")
                .email("user@user.com")
                .tel("010-0000-0000")
                .build();

        //when
        userRepository.save(userA);

        //then
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
            userRepository.save(userB);
        });
    }

    @Test
    @DisplayName("email과 password로 User 찾기")
    public void findUserByEmailAndPassword() {
        User userA = User.builder()
                .name("userA")
                .address("address")
                .email("user@user.com")
                .tel("010-0000-0000")
                .password("1234")
                .build();

        userRepository.save(userA);

        User findUser = userRepository.findByEmailAndPassword("user@user.com", "1234");
        User wrongEmail = userRepository.findByEmailAndPassword("user", "1234");
        User wrongPassword = userRepository.findByEmailAndPassword("user@user.com", "11");

        assertThat(userA).isNotNull();
        assertThat(userA).isEqualTo(findUser);
        assertThat(wrongEmail).isNull();
        assertThat(wrongPassword).isNull();
    }
}