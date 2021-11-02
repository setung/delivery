package setung.delivery.service.Menu;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import setung.delivery.domain.menu.Menu;
import setung.delivery.domain.menu.MenuDto;
import setung.delivery.domain.restaurant.Restaurant;
import setung.delivery.exception.CustomException;
import setung.delivery.exception.ErrorCode;
import setung.delivery.repository.MenuRepository;
import setung.delivery.repository.RestaurantRepository;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
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

    public Menu findByIdAndRestaurantId(long menuId, long restaurantId) {
        Menu menu = menuRepository.findByIdAndRestaurantId(menuId, restaurantId);

        if (menu == null)
            throw new CustomException(ErrorCode.NOT_FOUND_MENU);

        return menu;
    }

    public void deleteAllMenu(long ownerId, long restaurantId) {
        Restaurant restaurant = restaurantRepository.findByIdAndOwnerId(restaurantId, ownerId);

        if (restaurant == null)
            throw new CustomException(ErrorCode.NOT_FOUND_RESTAURANT);

        menuRepository.deleteByRestaurantId(restaurantId);
    }

    public void deleteMenu(long ownerId, long restaurantId, long menuId) {
        Restaurant restaurant = restaurantRepository.findByIdAndOwnerId(restaurantId, ownerId);

        if (restaurant == null)
            throw new CustomException(ErrorCode.NOT_FOUND_RESTAURANT);

        menuRepository.deleteById(menuId);
    }

    public List<Menu> findAllByRestaurantId(long restaurantId) {
        return menuRepository.findAllByRestaurantId(restaurantId);
    }
}
