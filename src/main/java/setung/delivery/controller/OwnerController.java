package setung.delivery.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import setung.delivery.argumentresolver.LoginOwnerId;
import setung.delivery.domain.owner.Owner;
import setung.delivery.domain.owner.OwnerDto;
import setung.delivery.service.owner.OwnerLoginService;
import setung.delivery.service.owner.OwnerService;

@RestController
@RequestMapping("/owners")
@AllArgsConstructor
public class OwnerController {

    private final OwnerService ownerService;
    private final OwnerLoginService loginService;

    @PostMapping
    public Owner join(@RequestBody OwnerDto ownerDto) {
        return ownerService.join(ownerDto);
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
    public Owner getLoginUser() {
        return loginService.getLoginOwner();
    }


    @DeleteMapping
    public void deleteOwner(@LoginOwnerId long ownerId) {
        ownerService.deleteOwner(ownerId);
        loginService.logout();

    @PutMapping
    public Owner updateOwner(@LoginOwnerId long ownerId, @RequestBody OwnerDto ownerDto) {
        return ownerService.updateOwner(ownerId, ownerDto);

    }
}
