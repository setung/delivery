package setung.delivery.controller.user;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import setung.delivery.domain.user.User;
import setung.delivery.domain.user.UserDto;
import setung.delivery.service.user.UserService;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public User join(@RequestBody UserDto userDto) {
        return userService.join(userDto);
    }


}
