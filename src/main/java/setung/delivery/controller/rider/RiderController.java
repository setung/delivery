package setung.delivery.controller.rider;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import setung.delivery.domain.rider.model.Rider;
import setung.delivery.controller.rider.dto.RiderDto;
import setung.delivery.domain.rider.service.RiderLoginService;
import setung.delivery.domain.rider.service.RiderService;
import setung.delivery.utils.SHA256;

@RestController
@RequestMapping("/riders")
@AllArgsConstructor
public class RiderController {

    private final RiderService riderService;
    private final RiderLoginService riderLoginService;

    @PostMapping
    public RiderDto join(@RequestBody RiderDto riderDto) {
        riderDto.setPassword(SHA256.encBySha256(riderDto.getPassword()));
        Rider rider = riderDto.toRider();
        return riderService.join(rider).toRiderDto();
    }

    @PostMapping("/login")
    public void login(@RequestParam String email, @RequestParam String password) {
        riderLoginService.login(email, password);
    }

    @PostMapping("/logout")
    public void logout() {
        riderLoginService.logout();
    }

    @GetMapping("/login")
    public RiderDto getLoginRider() {
        return riderLoginService.getLoginRider().toRiderDto();
    }
}
