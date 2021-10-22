package setung.delivery.service.Menu;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import setung.delivery.domain.menu.Menu;
import setung.delivery.domain.menu.MenuDto;
import setung.delivery.domain.restaurant.Restaurant;
import setung.delivery.exception.NotFoundException;
import setung.delivery.repository.MenuRepository.MenuRepository;
import setung.delivery.repository.restaurant.RestaurantRepository;

@Service
@AllArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final RestaurantRepository restaurantRepository;

    public Menu registerMenu(long ownerId, long restaurantId, MenuDto menuDto) {
        Restaurant restaurant = restaurantRepository.findByIdAndOwnerId(restaurantId, ownerId);

        if (restaurant == null)
            throw new NotFoundException("restaurantId : " + restaurantId + " OwnerId : " + ownerId + " 존재하지 않는 Restaurant 입니다.");

        menuDto.setRestaurant(restaurant);

        return menuRepository.save(menuDto.toMenu());
    }
}
