package setung.delivery.service.Menu;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import setung.delivery.domain.menu.Menu;
import setung.delivery.domain.menu.MenuDto;
import setung.delivery.domain.restaurant.Restaurant;
import setung.delivery.exception.CustomException;
import setung.delivery.exception.ErrorCode;
import setung.delivery.repository.MenuRepository;
import setung.delivery.repository.RestaurantRepository;

@Service
@AllArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final RestaurantRepository restaurantRepository;

    public Menu registerMenu(long ownerId, long restaurantId, MenuDto menuDto) {
        Restaurant restaurant = restaurantRepository.findByIdAndOwnerId(restaurantId, ownerId);

        if (restaurant == null)
            throw new CustomException(ErrorCode.NOT_FOUND_RESTAURANT);

        menuDto.setRestaurant(restaurant);

        return menuRepository.save(menuDto.toMenu());
    }
}
