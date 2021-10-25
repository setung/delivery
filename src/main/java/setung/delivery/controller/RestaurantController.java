package setung.delivery.controller;

import lombok.AllArgsConstructor;
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
    public List<Restaurant> findRestaurants(
            @RequestParam(required = false) RestaurantCategory category,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "5") int size,
            @RequestParam(required = false, defaultValue = "id") String[] sortBy,
            @RequestParam(required = false, defaultValue = "DESC") Sort.Direction orderBy) {

        Pageable pageable = PageRequest.of(page, size, orderBy, sortBy);

        if (category == null) {
            return restaurantService.findRestaurants(pageable);
        } else {
            return restaurantService.findRestaurants(category, pageable);
        }
    }
}
