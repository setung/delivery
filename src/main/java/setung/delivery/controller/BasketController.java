package setung.delivery.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import setung.delivery.argumentresolver.LoginUserId;
import setung.delivery.domain.basket.BasketMenu;
import setung.delivery.service.basket.BasketService;

import java.util.List;

@RestController
@RequestMapping("/baskets/restaurants/{restaurantId}")
@RequiredArgsConstructor
public class BasketController {

    private final BasketService basketService;

    @GetMapping
    public List<BasketMenu> findBasketMenus(@LoginUserId long userId, @PathVariable long restaurantId) {
        return basketService.findBasketMenus(userId, restaurantId);
    }

    @PostMapping
    public void addMenuInBasket(@LoginUserId long userId, @PathVariable long restaurantId, @RequestBody BasketMenu basketMenu) {
        basketService.addMenuInBasket(userId, restaurantId, basketMenu);
    }

    @DeleteMapping
    public void clearBasket(@LoginUserId long userId, @PathVariable long restaurantId) {
        basketService.clearBasket(userId, restaurantId);
    }

    @DeleteMapping("/basketmenus/{index}")
    public void deleteBasketMenu(@LoginUserId long userId, @PathVariable long restaurantId, @PathVariable long index) {
        basketService.deleteMenuInBasket(userId, restaurantId, index);
    }

    @PutMapping("/basketmenus/{index}")
    public void updateBasketMenu(@LoginUserId long userId, @PathVariable long restaurantId,
                                 @PathVariable long index, @RequestBody BasketMenu basketMenu) {
        basketService.updateMenuInBasket(userId, restaurantId, index, basketMenu);
    }

    @GetMapping("/basketmenus/{index}")
    public BasketMenu findBasketMenu(@LoginUserId long userId, @PathVariable long restaurantId,
                                     @PathVariable long index) {
        return basketService.findBasketMenu(userId, restaurantId, index);
    }
}
