package setung.delivery.domain.restaurant.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import setung.delivery.controller.owner.dto.OwnerDto;
import setung.delivery.domain.owner.model.Owner;
import setung.delivery.domain.restaurant.model.Restaurant;
import setung.delivery.domain.restaurant.model.RestaurantCategory;
import setung.delivery.controller.restaurant.dto.RestaurantDto;
import setung.delivery.controller.restaurant.dto.UpdatedRestaurantDto;
import setung.delivery.exception.CustomException;
import setung.delivery.exception.ErrorCode;
import setung.delivery.domain.menu.repository.MenuRepository;
import setung.delivery.domain.owner.repository.OwnerRepository;
import setung.delivery.domain.restaurant.repository.RestaurantRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final OwnerRepository ownerRepository;
    private final MenuRepository menuRepository;

    public Restaurant register(long ownerId, RestaurantDto restaurantDto) {
        Owner owner = ownerRepository.findById(ownerId).get();
        restaurantDto.setOwner(new OwnerDto(owner));
        Restaurant savedRestaurant = restaurantRepository.save(new Restaurant(restaurantDto));
        return savedRestaurant;
    }

    public Page<Restaurant> findRestaurants(Pageable pageable) {
        return restaurantRepository.findAll(pageable);
    }

    public Page<Restaurant> findRestaurants(RestaurantCategory category, Pageable pageable) {
        return restaurantRepository.findByCategory(category, pageable);
    }

    public Restaurant findRestaurantById(long restaurantId) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(restaurantId);

        if (!restaurant.isPresent())
            throw new CustomException(ErrorCode.NOT_FOUND_RESTAURANT);

        return restaurant.get();
    }

    public void deleteRestaurant(long ownerId, long restaurantId) {
        Restaurant restaurant = restaurantRepository.findByIdAndOwnerId(restaurantId, ownerId);

        if (restaurant == null)
            throw new CustomException(ErrorCode.NOT_FOUND_RESTAURANT);

        menuRepository.deleteByRestaurantId(restaurantId);
        restaurantRepository.deleteById(restaurantId);
    }

    public Restaurant findRestaurantByIdAndOwnerId(long ownerId, long restaurantId) {
        Restaurant restaurant = restaurantRepository.findByIdAndOwnerId(restaurantId, ownerId);

        if (restaurant == null)
            throw new CustomException(ErrorCode.NOT_FOUND_RESTAURANT);

        return restaurant;
    }

    public void updateRestaurant(Restaurant restaurant, UpdatedRestaurantDto updatedRestaurantDto) {
        restaurant.update(updatedRestaurantDto);
    }
}
