package setung.delivery.service.basket;

import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import setung.delivery.domain.basket.BasketMenu;
import setung.delivery.domain.menu.Menu;
import setung.delivery.exception.CustomException;
import setung.delivery.exception.ErrorCode;
import setung.delivery.repository.MenuRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class BasketService {

    private final RedisTemplate redisTemplate;
    private final MenuRepository menuRepository;

    public void addMenuInBasket(long userId, long restaurantId, BasketMenu basketMenu) {
        ListOperations ops = redisTemplate.opsForList();
        Menu findMenu = menuRepository.findByIdAndRestaurantId(basketMenu.getMenuId(), restaurantId);

        if (findMenu == null)
            throw new CustomException(ErrorCode.NOT_FOUND_MENU);
        if (findMenu.getQuantity() < basketMenu.getQuantity())
            throw new CustomException(ErrorCode.BAD_REQUEST_MENU, "재고량 : " + findMenu.getQuantity() + " 신청량 : " + basketMenu.getQuantity());

        ops.rightPush(getBasketKey(userId, restaurantId), basketMenu);
    }

    public void deleteMenuInBasket(long userId, long restaurantId, long index) {
        ListOperations ops = redisTemplate.opsForList();
        String key = getBasketKey(userId, restaurantId);

        if (index >= ops.size(key))
            throw new CustomException(ErrorCode.BAD_REQUEST_MENU);

        ops.remove(key, 0, ops.index(key, index));
    }

    public void updateMenuInBasket(long userId, long restaurantId, long index, BasketMenu basketMenu) {
        ListOperations ops = redisTemplate.opsForList();
        String key = getBasketKey(userId, restaurantId);
        BasketMenu findBasketMenu = (BasketMenu) ops.index(key, index);
        Menu findMenu = menuRepository.findByIdAndRestaurantId(basketMenu.getMenuId(), restaurantId);

        if (index >= ops.size(key))
            throw new CustomException(ErrorCode.NOT_FOUND_MENU);
        if (!findBasketMenu.getMenuId().equals(basketMenu.getMenuId()))
            throw new CustomException(ErrorCode.BAD_REQUEST_MENU);
        if (findMenu.getQuantity() < basketMenu.getQuantity())
            throw new CustomException(ErrorCode.BAD_REQUEST_MENU);

        ops.set(key, index, basketMenu);
    }

    public void clearBasket(long userId, long restaurantId) {
        ListOperations ops = redisTemplate.opsForList();
        ops.getOperations().delete(getBasketKey(userId, restaurantId));
    }

    public List<BasketMenu> findBasketMenus(long userId, long restaurantId) {
        ListOperations ops = redisTemplate.opsForList();
        String key = getBasketKey(userId, restaurantId);

        List<BasketMenu> basketMenus = ops.range(key, 0, ops.size(key) - 1);

        if (basketMenus.isEmpty())
            throw new CustomException(ErrorCode.NOT_FOUND_BASKET);

        return basketMenus;
    }

    public BasketMenu findBasketMenu(long userId, long restaurantId, long index) {
        ListOperations ops = redisTemplate.opsForList();
        String key = getBasketKey(userId, restaurantId);

        if (index >= ops.size(key))
            throw new CustomException(ErrorCode.NOT_FOUND_MENU);

        return (BasketMenu) ops.index(key, index);
    }

    private String getBasketKey(long userId, long restaurantId) {
        return "BASKET_" + userId + "_" + restaurantId;
    }
}
