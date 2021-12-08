package setung.delivery.controller.restaurant;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;
import setung.delivery.argumentresolver.LoginOwnerId;
import setung.delivery.controller.restaurant.specification.RestaurantSpecification;
import setung.delivery.domain.restaurant.model.Restaurant;
import setung.delivery.domain.restaurant.model.RestaurantCategory;
import setung.delivery.controller.restaurant.dto.RestaurantDto;
import setung.delivery.controller.restaurant.dto.UpdatedRestaurantDto;
import setung.delivery.domain.restaurant.service.RestaurantService;

@RestController
@RequestMapping("/restaurants")
@AllArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @PostMapping
    public RestaurantDto registerRestaurant(@LoginOwnerId long ownerId, @RequestBody RestaurantDto restaurantDto) {
        return new RestaurantDto(restaurantService.register(ownerId, restaurantDto));
    }

    @GetMapping()
    public Page<RestaurantDto> findRestaurants(
            @RequestParam(required = false) RestaurantCategory category,
            Pageable pageable) {

        Specification<Restaurant> spec = (root, query, criteriaBuilder) -> null;
        if (category != null) spec = spec.and(RestaurantSpecification.equalCategory(category));

        return restaurantService.findRestaurants(spec, pageable).map(RestaurantDto::new);
    }

    @DeleteMapping("/{restaurantId}")
    public void deleteRestaurant(@LoginOwnerId long ownerId, @PathVariable long restaurantId) {
        restaurantService.deleteRestaurant(ownerId, restaurantId);
    }

    @PutMapping("/{restaurantId}")
    public void updateRestaurant(@LoginOwnerId long ownerId, @PathVariable long restaurantId, @RequestBody UpdatedRestaurantDto updatedRestaurantDto) {
        Restaurant restaurant = restaurantService.findRestaurantByIdAndOwnerId(ownerId, restaurantId);
        restaurantService.updateRestaurant(ownerId, restaurantId, updatedRestaurantDto);
    }
}
