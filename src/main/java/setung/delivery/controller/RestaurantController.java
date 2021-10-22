package setung.delivery.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import setung.delivery.argumentresolver.LoginOwnerId;
import setung.delivery.domain.restaurant.Restaurant;
import setung.delivery.domain.restaurant.RestaurantDto;
import setung.delivery.service.restaurant.RestaurantService;

@RestController
@RequestMapping("/restaurants")
@AllArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @PostMapping
    public Restaurant registerRestaurant(@LoginOwnerId long ownerId, @RequestBody RestaurantDto restaurantDto) {
        return restaurantService.register(ownerId, restaurantDto);
    }
}
