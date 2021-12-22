package setung.delivery.controller.rider;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import setung.delivery.argumentresolver.LoginRiderId;
import setung.delivery.domain.order.model.Order;
import setung.delivery.domain.order.service.OrderService;
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
    private final OrderService orderService;

    @PostMapping
    public RiderDto join(@RequestBody RiderDto riderDto) {
        riderDto.setPassword(SHA256.encBySha256(riderDto.getPassword()));
        return new RiderDto(riderService.join(riderDto));
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
        return new RiderDto(riderLoginService.getLoginRider());
    }

    @PostMapping("/orders/{orderId}/approve")
    public Order approveDelivery(@LoginRiderId long riderId, @PathVariable long orderId) {
        return orderService.approveDelivery(riderId, orderId);
    }

    @PostMapping("/orders/{orderId}/success")
    public Order successDelivery(@LoginRiderId long riderId, @PathVariable long orderId) {
        return orderService.successDelivery(riderId, orderId);
    }
}
