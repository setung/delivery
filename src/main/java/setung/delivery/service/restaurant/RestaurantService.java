package setung.delivery.service.restaurant;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import setung.delivery.domain.owner.Owner;
import setung.delivery.domain.restaurant.Restaurant;
import setung.delivery.domain.restaurant.RestaurantCategory;
import setung.delivery.domain.restaurant.RestaurantDto;
import setung.delivery.exception.CustomException;
import setung.delivery.exception.ErrorCode;
import setung.delivery.repository.MenuRepository;
import setung.delivery.repository.OwnerRepository;
import setung.delivery.repository.RestaurantRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final OwnerRepository ownerRepository;
    private final MenuRepository menuRepository;

    public Restaurant register(long ownerId, RestaurantDto restaurantDto) {
        Owner owner = ownerRepository.findById(ownerId).get();
        restaurantDto.setOwner(owner);
        Restaurant savedRestaurant = restaurantRepository.save(restaurantDto.toRestaurant());
        return savedRestaurant;
    }

    public Page<Restaurant> findRestaurants(Pageable pageable) {
        return restaurantRepository.findAll(pageable);
    }

    public Page<Restaurant> findRestaurants(RestaurantCategory category, Pageable pageable) {
        return restaurantRepository.findByCategory(category, pageable);
    }

    public Restaurant findRestaurantById(long restaurantId) {
        return restaurantRepository.findById(restaurantId).orElse(null);
    }

    public void deleteRestaurant(long ownerId, long restaurantId) {
        Restaurant restaurant = restaurantRepository.findByIdAndOwnerId(restaurantId, ownerId);

        if (restaurant == null)
            throw new CustomException(ErrorCode.NOT_FOUND_RESTAURANT);

        menuRepository.deleteByRestaurantId(restaurantId);
        restaurantRepository.deleteById(restaurantId);
    }
}
