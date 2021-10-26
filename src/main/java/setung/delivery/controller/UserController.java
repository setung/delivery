package setung.delivery.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import setung.delivery.argumentresolver.LoginUserId;
import setung.delivery.domain.user.UserDto;
import setung.delivery.service.user.UserLoginService;
import setung.delivery.service.user.UserService;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserLoginService loginService;

    @PostMapping
    public UserDto join(@RequestBody UserDto userDto) {
        return userService.join(userDto).toUserDto();
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
        return loginService.getLoginUser().toUserDto();
    }

    @PutMapping
    public UserDto updateUser(@LoginUserId long userId, @RequestBody UserDto userDto) {
        return userService.updateUser(userId, userDto).toUserDto();
    }

    @DeleteMapping
    public void deleteUser(@LoginUserId long userId) {
        userService.deleteUser(userId);
        loginService.logout();
    }
}
