package setung.delivery.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import setung.delivery.argumentresolver.LoginOwnerId;
import setung.delivery.domain.restaurant.Restaurant;
import setung.delivery.domain.restaurant.RestaurantCategory;
import setung.delivery.domain.restaurant.RestaurantDto;
import setung.delivery.service.restaurant.RestaurantService;

@RestController
@RequestMapping("/restaurants")
@AllArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @PostMapping
    public RestaurantDto registerRestaurant(@LoginOwnerId long ownerId, @RequestBody RestaurantDto restaurantDto) {
        return restaurantService.register(ownerId, restaurantDto).toRestaurantDto();
    }

    @GetMapping()
    public Page<RestaurantDto> findRestaurants(
            @RequestParam(required = false) RestaurantCategory category,
            Pageable pageable) {

        if (category == null) {
            return restaurantService.findRestaurants(pageable).map(Restaurant::toRestaurantDto);
        } else {
            return restaurantService.findRestaurants(category, pageable).map(Restaurant::toRestaurantDto);
        }
    }

    @DeleteMapping("/{restaurantId}")
    public void deleteRestaurant(@LoginOwnerId long ownerId,@PathVariable long restaurantId) {
        restaurantService.deleteRestaurant(ownerId, restaurantId);
    }
}
