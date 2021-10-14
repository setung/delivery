package setung.delivery.controller.owner;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import setung.delivery.domain.owner.Owner;
import setung.delivery.domain.owner.OwnerDto;
import setung.delivery.service.owner.OwnerService;

@RestController
@RequestMapping("/owners")
@AllArgsConstructor
public class OwnerController {

    private final OwnerService ownerService;

    @PostMapping
    public Owner join(@RequestBody OwnerDto ownerDto) {
        return ownerService.join(ownerDto);
    }

}
