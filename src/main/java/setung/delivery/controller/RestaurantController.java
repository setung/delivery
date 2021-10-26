package setung.delivery.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import setung.delivery.argumentresolver.LoginOwnerId;
import setung.delivery.domain.restaurant.Restaurant;
import setung.delivery.domain.restaurant.RestaurantCategory;
import setung.delivery.domain.restaurant.RestaurantDto;
import setung.delivery.service.restaurant.RestaurantService;

import java.util.List;

@RestController
@RequestMapping("/restaurants")
@AllArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @PostMapping
    public Restaurant registerRestaurant(@LoginOwnerId long ownerId, @RequestBody RestaurantDto restaurantDto) {
        return restaurantService.register(ownerId, restaurantDto);
    }

    @GetMapping()
    public Page<Restaurant> findRestaurants(
            @RequestParam(required = false) RestaurantCategory category,
            Pageable pageable) {

        if (category == null) {
            return restaurantService.findRestaurants(pageable);
        } else {
            return restaurantService.findRestaurants(category, pageable);
        }
    }
}
