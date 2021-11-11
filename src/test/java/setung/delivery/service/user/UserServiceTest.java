package setung.delivery.service.user;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import setung.delivery.domain.user.model.User;
import setung.delivery.controller.user.dto.UserDto;
import setung.delivery.domain.user.service.UserService;

@SpringBootTest
@Transactional
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