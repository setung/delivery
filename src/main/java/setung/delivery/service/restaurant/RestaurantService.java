package setung.delivery.service.restaurant;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import setung.delivery.domain.owner.Owner;
import setung.delivery.domain.restaurant.Restaurant;
import setung.delivery.domain.restaurant.RestaurantDto;
import setung.delivery.repository.OwnerRepository;
import setung.delivery.repository.RestaurantRepository;

@Service
@AllArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final OwnerRepository ownerRepository;

    public Restaurant register(long ownerId, RestaurantDto restaurantDto) {
        Owner owner = ownerRepository.findById(ownerId).get();
        restaurantDto.setOwner(owner);
        Restaurant savedRestaurant = restaurantRepository.save(restaurantDto.toRestaurant());
        return savedRestaurant;
    }

}
