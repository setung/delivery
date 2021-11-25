package setung.delivery.controller.user;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import setung.delivery.argumentresolver.LoginUserId;
import setung.delivery.controller.user.dto.UserDto;
import setung.delivery.domain.user.service.UserLoginService;
import setung.delivery.domain.user.service.UserService;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserLoginService loginService;

    @PostMapping
    public UserDto join(@RequestBody UserDto userDto) {
        return new UserDto(userService.join(userDto));
    }

    @PostMapping("/login")
    public void login(@RequestParam String email, @RequestParam String password) {
        loginService.login(email, password);
    }

    @PostMapping("/logout")
    public void logout() {
        loginService.logout();
    }

    @GetMapping("/login")
    public UserDto getLoginUser() {
        return new UserDto(loginService.getLoginUser());
    }

    @PutMapping
    public UserDto updateUser(@LoginUserId long userId, @RequestBody UserDto userDto) {
        return new UserDto(userService.updateUser(userId, userDto));
    }

    @DeleteMapping
    public void deleteUser(@LoginUserId long userId) {
        userService.deleteUser(userId);
        loginService.logout();
    }
}
