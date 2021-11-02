package setung.delivery.service.order;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import setung.delivery.domain.basket.BasketMenu;
import setung.delivery.domain.menu.Menu;
import setung.delivery.domain.menu.MenuDto;
import setung.delivery.domain.order.RequestOrder;
import setung.delivery.domain.owner.Owner;
import setung.delivery.domain.restaurant.Restaurant;
import setung.delivery.domain.restaurant.RestaurantDto;
import setung.delivery.domain.user.User;
import setung.delivery.exception.CustomException;
import setung.delivery.repository.OrderMenuRepository;
import setung.delivery.repository.OrderRepository;
import setung.delivery.repository.OwnerRepository;
import setung.delivery.repository.UserRepository;
import setung.delivery.service.Menu.MenuService;
import setung.delivery.service.basket.BasketService;
import setung.delivery.service.restaurant.RestaurantService;
import setung.delivery.service.user.UserService;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    OrderService orderService;
    @Autowired
    OrderMenuRepository orderMenuRepository;
    @Autowired
    BasketService basketService;
    @Autowired
    MenuService menuService;
    @Autowired
    RestaurantService restaurantService;
    @Autowired
    OwnerRepository ownerRepository;
    @Autowired
    UserRepository userRepository;

    User user;
    Owner owner;
    Restaurant restaurant;
    Menu menu1;
    Menu menu2;

    @BeforeEach
    void beforeEach() {
        user = User.builder().build();
        owner = Owner.builder().build();
        RestaurantDto restaurantDto = RestaurantDto.builder().build();
        MenuDto menuDto1 = MenuDto.builder().price(10000).quantity(10).name("menu1").build();
        MenuDto menuDto2 = MenuDto.builder().price(1000).quantity(5).name("menu2").build();

        userRepository.save(user);
        ownerRepository.save(owner);
        restaurant = restaurantService.register(owner.getId(), restaurantDto);
        menu1 = menuService.registerMenu(owner.getId(), restaurant.getId(), menuDto1);
        menu2 = menuService.registerMenu(owner.getId(), restaurant.getId(), menuDto2);
    }

    @Test
    @DisplayName("정상적으로 주문")
    void order() {
        BasketMenu basketMenu1 = BasketMenu.builder().quantity(2).menuId(menu1.getId()).build();
        BasketMenu basketMenu2 = BasketMenu.builder().quantity(2).menuId(menu2.getId()).build();
        basketService.addMenuInBasket(user.getId(), restaurant.getId(), basketMenu1);
        basketService.addMenuInBasket(user.getId(), restaurant.getId(), basketMenu2);

        RequestOrder requestOrder = RequestOrder.builder().address("어딘가").restaurantId(restaurant.getId()).build();

        orderService.order(user.getId(), requestOrder);

        Menu findMenu1 = menuService.findByIdAndRestaurantId(this.menu1.getId(), restaurant.getId());
        Menu findMenu2 = menuService.findByIdAndRestaurantId(this.menu2.getId(), restaurant.getId());

        assertThat(findMenu1.getQuantity()).isEqualTo(8);
        assertThat(findMenu2.getQuantity()).isEqualTo(3);
        assertThrows(CustomException.class, () -> basketService.findBasketMenus(user.getId(), restaurant.getId()));
    }

    @Test
    @DisplayName("재고보다 많은 수량을 주문 시 예외 발생")
    void orderWithWrongQuantity() {
        BasketMenu basketMenu1 = BasketMenu.builder().quantity(2).menuId(menu1.getId()).build();
        basketService.addMenuInBasket(user.getId(), restaurant.getId(), basketMenu1);

        RequestOrder requestOrder = RequestOrder.builder().address("어딘가").restaurantId(restaurant.getId()).build();

        menu1 = menuService.findByIdAndRestaurantId(menu1.getId(), restaurant.getId());
        updateQuantity();

        assertThrows(CustomException.class, () -> orderService.order(user.getId(), requestOrder));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    private void updateQuantity() {
        menu1.updateQuantity(0);
    }
}