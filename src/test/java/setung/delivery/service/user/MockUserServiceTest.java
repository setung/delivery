package setung.delivery.service.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import setung.delivery.domain.user.User;
import setung.delivery.repository.UserRepository;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class MockUserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userService;

    @Test
    @DisplayName("정상적인 계정 삭제")
    public void deleteUser() {
        //given
        User user = User.builder().id(1L).build();
        //when
        userService.deleteUser(user.getId());
        //then
        verify(userRepository, times(1)).deleteById(user.getId());
    }
}
