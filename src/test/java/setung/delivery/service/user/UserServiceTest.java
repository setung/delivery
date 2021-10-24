package setung.delivery.service.user;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import setung.delivery.domain.user.User;
import setung.delivery.domain.user.UserDto;
import setung.delivery.repository.UserRepository;
import setung.delivery.utils.SHA256;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    UserService userService;

    @Test
    public void updateUser() {
        //given
        UserDto userdto = UserDto.builder()
                .name("jsh")
                .email("test@test.com")
                .tel("000-0000-0000")
                .password("1234")
                .build();

        UserDto updatedUserDto = UserDto.builder()
                .name("aaaa")
                .password("1234")
                .build();

        //when
        User savedUser = userService.join(userdto);
        userService.updateUser(savedUser.getId(), updatedUserDto);

        //then
        User findUser = userService.findUserByEmailAndPassword(userdto.getEmail(), userdto.getPassword());
        Assertions.assertThat(findUser.getName()).isEqualTo("aaaa");
    }

    @Test
    public void deleteUser() {
        //given
        UserDto userdto = UserDto.builder()
                .name("jsh")
                .email("test@test.com")
                .tel("000-0000-0000")
                .password("1234")
                .build();

        //when
        User savedUser = userService.join(userdto);
        userService.deleteUser(savedUser.getId());

        //then
        User deletedUser = userService.findUserByEmailAndPassword(userdto.getEmail(), userdto.getPassword());
        Assertions.assertThat(deletedUser).isNull();
    }

}