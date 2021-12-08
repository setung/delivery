package setung.delivery.service.basket;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import setung.delivery.domain.basket.model.BasketMenu;
import setung.delivery.domain.basket.service.BasketService;
import setung.delivery.domain.menu.model.Menu;
import setung.delivery.controller.menu.dto.MenuDto;
import setung.delivery.domain.owner.model.Owner;
import setung.delivery.domain.restaurant.model.Restaurant;
import setung.delivery.controller.restaurant.dto.RestaurantDto;
import setung.delivery.domain.user.model.User;
import setung.delivery.exception.CustomException;
import setung.delivery.domain.owner.repository.OwnerRepository;
import setung.delivery.domain.user.repository.UserRepository;
import setung.delivery.domain.menu.service.MenuService;
import setung.delivery.domain.restaurant.service.RestaurantService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
//@Commit
class BasketServiceTest {

    @Autowired
    BasketService basketService;
    @Autowired
    OwnerRepository ownerRepository;
    @Autowired
    RestaurantService restaurantService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    MenuService menuService;

    User user;
    Owner owner;
    Restaurant restaurant;
    Menu menu1;
    Menu menu2;
    Menu menu3;

    @BeforeEach
    public void beforeEach() {
        user = User.builder().build();
        owner = Owner.builder().build();
        RestaurantDto restaurantDto = RestaurantDto.builder()
                .address("서울 강남구 강남대로94길 27").build();
        MenuDto menuDto1 = MenuDto.builder().quantity(1).name("menu1").build();
        MenuDto menuDto2 = MenuDto.builder().quantity(2).name("menu2").build();
        MenuDto menuDto3 = MenuDto.builder().quantity(3).name("menu3").build();
        MenuDto menuDto4 = MenuDto.builder().quantity(10).name("menu1").build();

        userRepository.save(user);
        ownerRepository.save(owner);
        restaurant = restaurantService.register(owner.getId(), restaurantDto);
        menu1 = menuService.registerMenu(owner.getId(), restaurant.getId(), menuDto1);
        menu2 = menuService.registerMenu(owner.getId(), restaurant.getId(), menuDto2);
        menu3 = menuService.registerMenu(owner.getId(), restaurant.getId(), menuDto3);
    }

    @Test
    @DisplayName("정상적으로 장바구니에 메뉴 등록")
    public void addMenuInBasket() {
        basketService.addMenuInBasket(user.getId(), restaurant.getId(), new BasketMenu(menu1.getId(), 1));
        basketService.addMenuInBasket(user.getId(), restaurant.getId(), new BasketMenu(menu2.getId(), 2));
        basketService.addMenuInBasket(user.getId(), restaurant.getId(), new BasketMenu(menu3.getId(), 3));

        List<BasketMenu> basketMenus = basketService.findBasketMenus(user.getId(), restaurant.getId());

        assertThat(basketMenus.size()).isEqualTo(3);
        assertThat(basketMenus.get(0).getMenuId()).isEqualTo(menu1.getId());
        assertThat(basketMenus.get(1).getMenuId()).isEqualTo(menu2.getId());
        assertThat(basketMenus.get(2).getMenuId()).isEqualTo(menu3.getId());
    }

    @Test
    @DisplayName("재고보다 더 많은 수량을 장바구니에 담을 경우 예외 반환")
    public void addMenuInBasketOverStuck() {
        Assertions.assertThrows(CustomException.class, () -> {
            basketService.addMenuInBasket(user.getId(), restaurant.getId(), new BasketMenu(menu1.getId(), 5));
        });
    }

    @Test
    @DisplayName("장바구니 비우기")
    public void clearMenuInBasket() {
        basketService.addMenuInBasket(user.getId(), restaurant.getId(), new BasketMenu(menu1.getId(), 1));
        basketService.addMenuInBasket(user.getId(), restaurant.getId(), new BasketMenu(menu2.getId(), 2));
        basketService.addMenuInBasket(user.getId(), restaurant.getId(), new BasketMenu(menu3.getId(), 3));

        basketService.clearBasket(user.getId(), restaurant.getId());

        Assertions.assertThrows(CustomException.class, () -> basketService.findBasketMenus(user.getId(), restaurant.getId()));
    }

    @Test
    @DisplayName("정상적인 메뉴 삭제")
    public void deleteMenuInBasket() {
        basketService.addMenuInBasket(user.getId(), restaurant.getId(), new BasketMenu(menu1.getId(), 1));
        basketService.addMenuInBasket(user.getId(), restaurant.getId(), new BasketMenu(menu2.getId(), 2));
        basketService.addMenuInBasket(user.getId(), restaurant.getId(), new BasketMenu(menu3.getId(), 3));

        basketService.deleteMenuInBasket(user.getId(), restaurant.getId(), 0);

        List<BasketMenu> basketMenus = basketService.findBasketMenus(user.getId(), restaurant.getId());

        assertThat(basketMenus.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("잘못된 인덱스로 메뉴 삭제시 예외")
    public void deleteMenuInBasketWrongIndex() {
        basketService.addMenuInBasket(user.getId(), restaurant.getId(), new BasketMenu(menu1.getId(), 1));

        Assertions.assertThrows(CustomException.class, () -> {
            basketService.deleteMenuInBasket(user.getId(), restaurant.getId(), 2);
        });
    }

    @Test
    @DisplayName("정상적인 메뉴 수정")
    public void updateMenuInBasket() {
        basketService.addMenuInBasket(user.getId(), restaurant.getId(), new BasketMenu(menu1.getId(), 1));
        basketService.addMenuInBasket(user.getId(), restaurant.getId(), new BasketMenu(menu2.getId(), 2));

        basketService.updateMenuInBasket(user.getId(), restaurant.getId(), 1, new BasketMenu(menu2.getId(), 1));

        List<BasketMenu> basketMenus = basketService.findBasketMenus(user.getId(), restaurant.getId());

        assertThat(basketMenus.get(1).getQuantity()).isEqualTo(1);
        assertThat(basketMenus.get(1).getMenuId()).isEqualTo(menu2.getId());
    }

    @Test
    @DisplayName("잘못된 메뉴 수정 -재고")
    public void updateMenuInBasketWithWrongQuantity() {
        basketService.addMenuInBasket(user.getId(), restaurant.getId(), new BasketMenu(menu1.getId(), 1));
        basketService.addMenuInBasket(user.getId(), restaurant.getId(), new BasketMenu(menu2.getId(), 2));

        Assertions.assertThrows(CustomException.class, () -> {
            basketService.updateMenuInBasket(user.getId(), restaurant.getId(), 1, new BasketMenu(menu2.getId(), 11));
        });
    }

    @Test
    @DisplayName("잘못된 메뉴 수정 -menuId")
    public void updateMenuInBasketWithWrongMenuID() {
        basketService.addMenuInBasket(user.getId(), restaurant.getId(), new BasketMenu(menu1.getId(), 1));
        basketService.addMenuInBasket(user.getId(), restaurant.getId(), new BasketMenu(menu2.getId(), 2));

        Assertions.assertThrows(CustomException.class, () -> {
            basketService.updateMenuInBasket(user.getId(), restaurant.getId(), 0, new BasketMenu(menu2.getId(), 1));
        });
    }

    @Test
    @DisplayName("정상적으로 BasketMenu 조회")
    public void getBasketMenuByIndex() {
        basketService.addMenuInBasket(user.getId(), restaurant.getId(), new BasketMenu(menu1.getId(), 1));
        BasketMenu basketMenu = basketService.findBasketMenu(user.getId(), restaurant.getId(), 0);

        assertThat(basketMenu.getMenuId()).isEqualTo(menu1.getId());
        assertThat(basketMenu.getQuantity()).isEqualTo(1);
    }

    @Test
    @DisplayName("잘못된 index로 BasketMenu 조회시 예외")
    public void getBasketMenuByIndexWrong() {
        Assertions.assertThrows(CustomException.class, () -> {
            BasketMenu basketMenu = basketService.findBasketMenu(user.getId(), restaurant.getId(), 0);
        });
    }
}