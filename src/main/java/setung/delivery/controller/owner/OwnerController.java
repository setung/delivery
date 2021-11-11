package setung.delivery.controller.owner;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import setung.delivery.argumentresolver.LoginOwnerId;
import setung.delivery.controller.owner.dto.OwnerDto;
import setung.delivery.domain.owner.service.OwnerLoginService;
import setung.delivery.domain.owner.service.OwnerService;

@RestController
@RequestMapping("/owners")
@AllArgsConstructor
public class OwnerController {

    private final OwnerService ownerService;
    private final OwnerLoginService loginService;

    @PostMapping
    public OwnerDto join(@RequestBody OwnerDto ownerDto) {
        return ownerService.join(ownerDto).toOwnerDto();
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
    public OwnerDto getLoginUser() {
        return loginService.getLoginOwner().toOwnerDto();
    }


    @DeleteMapping
    public void deleteOwner(@LoginOwnerId long ownerId) {
        ownerService.deleteOwner(ownerId);
        loginService.logout();
    }

    @PutMapping
    public OwnerDto updateOwner(@LoginOwnerId long ownerId, @RequestBody OwnerDto ownerDto) {
        return ownerService.updateOwner(ownerId, ownerDto).toOwnerDto();
    }
}
