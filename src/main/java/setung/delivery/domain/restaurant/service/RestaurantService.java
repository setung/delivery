package setung.delivery.domain.restaurant.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import setung.delivery.controller.owner.dto.OwnerDto;
import setung.delivery.domain.owner.model.Owner;
import setung.delivery.domain.restaurant.model.Restaurant;
import setung.delivery.controller.restaurant.dto.RestaurantDto;
import setung.delivery.controller.restaurant.dto.UpdatedRestaurantDto;
import setung.delivery.domain.user.model.User;
import setung.delivery.domain.user.service.UserService;
import setung.delivery.exception.CustomException;
import setung.delivery.exception.ErrorCode;
import setung.delivery.domain.menu.repository.MenuRepository;
import setung.delivery.domain.owner.repository.OwnerRepository;
import setung.delivery.domain.restaurant.repository.RestaurantRepository;
import setung.delivery.utils.DistanceCalculator;
import setung.delivery.utils.geo.GeocodingUtil;
import setung.delivery.utils.geo.LatLonData;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final OwnerRepository ownerRepository;
    private final MenuRepository menuRepository;
    private final GeocodingUtil geocodingUtil;
    private final UserService userService;

    public Restaurant register(long ownerId, RestaurantDto restaurantDto) {
        Owner owner = ownerRepository.findById(ownerId).get();
        restaurantDto.setOwner(new OwnerDto(owner));

        Restaurant restaurant = new Restaurant(restaurantDto);
        setRestaurantLatLon(restaurant, restaurantDto.getAddress());
        return restaurantRepository.save(restaurant);
    }

    public Page<Restaurant> findRestaurants(long userId, Specification<Restaurant> spec, Pageable pageable) {
        User user = userService.findUserById(userId);

        List<Restaurant> all = restaurantRepository.findAll(spec, pageable).stream().filter(
                        res -> res.getDeliveryRange() >=
                                DistanceCalculator.distance(user.getLat(), user.getLon(), res.getLat(), res.getLon()))
                .collect(Collectors.toList());

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), all.size());
        Page<Restaurant> page = new PageImpl<>(all.subList(start, end), pageable, all.size());

        return page;
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

    public void updateRestaurant(long ownerId, long restaurantId, UpdatedRestaurantDto updatedRestaurantDto) {
        Restaurant restaurant = restaurantRepository.findByIdAndOwnerId(restaurantId, ownerId);
        restaurant.update(updatedRestaurantDto);
        setRestaurantLatLon(restaurant, updatedRestaurantDto.getAddress());
    }

    private void setRestaurantLatLon(Restaurant restaurant, String address) {
        LatLonData latLon = geocodingUtil.getLatLon(address);
        restaurant.setLatLon(latLon);
    }
}
