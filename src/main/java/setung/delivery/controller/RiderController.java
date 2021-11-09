package setung.delivery.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import setung.delivery.domain.rider.Rider;
import setung.delivery.domain.rider.RiderDto;
import setung.delivery.service.rider.RiderService;
import setung.delivery.utils.SHA256;

@RestController
@RequestMapping("/riders")
@AllArgsConstructor
public class RiderController {

    private final RiderService riderService;

    @PostMapping
    public RiderDto join(@RequestBody RiderDto riderDto) {
        riderDto.setPassword(SHA256.encBySha256(riderDto.getPassword()));
        Rider rider = riderDto.toRider();
        return riderService.join(rider).toRiderDto();
    }
}
