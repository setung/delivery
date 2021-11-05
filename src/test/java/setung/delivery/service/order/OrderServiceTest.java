package setung.delivery.service.order;

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
import setung.delivery.domain.order.Order;
import setung.delivery.domain.order.OrderStatus;
import setung.delivery.domain.order.RequestOrder;
import setung.delivery.domain.owner.Owner;
import setung.delivery.domain.restaurant.Restaurant;
import setung.delivery.domain.restaurant.RestaurantDto;
import setung.delivery.domain.user.User;
import setung.delivery.exception.CustomException;
import setung.delivery.repository.OrderMenuRepository;
import setung.delivery.repository.OwnerRepository;
import setung.delivery.repository.UserRepository;
import setung.delivery.service.Menu.MenuService;
import setung.delivery.service.basket.BasketService;
import setung.delivery.service.restaurant.RestaurantService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @Test
    @DisplayName("정상적으로 주문을 승인한다.")
    public void approveOrder() {
        BasketMenu basketMenu1 = BasketMenu.builder().quantity(2).menuId(menu1.getId()).build();
        basketService.addMenuInBasket(user.getId(), restaurant.getId(), basketMenu1);
        RequestOrder requestOrder = RequestOrder.builder().address("어딘가").restaurantId(restaurant.getId()).build();

        orderService.order(user.getId(), requestOrder);

        List<Order> content = orderService.findOrders(user.getId(), null).getContent();
        Order order = content.get(0);

        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.ORDER_REQUEST);
        orderService.approveOrder(owner.getId(), restaurant.getId(), order.getId());
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.ORDER_APPROVAL);
    }

    @Test
    @DisplayName("특정 주문 상태에 주문 승인시 예외를 발생한다.")
    public void approveOrderWrongOrderStatus() {
        BasketMenu basketMenu1 = BasketMenu.builder().quantity(2).menuId(menu1.getId()).build();
        basketService.addMenuInBasket(user.getId(), restaurant.getId(), basketMenu1);
        RequestOrder requestOrder = RequestOrder.builder().address("어딘가").restaurantId(restaurant.getId()).build();

        orderService.order(user.getId(), requestOrder);

        List<Order> content = orderService.findOrders(user.getId(), null).getContent();
        Order order = content.get(0);

        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.ORDER_REQUEST);
        order.updateOrderStatus(OrderStatus.DELIVERY_COMPLETE);

        assertThrows(CustomException.class, () -> orderService.approveOrder(owner.getId(), restaurant.getId(), order.getId()));
    }

    @Test
    @DisplayName("정상적으로 주문을 거절한다.")
    public void refuseOrder() {
        BasketMenu basketMenu1 = BasketMenu.builder().quantity(2).menuId(menu1.getId()).build();
        basketService.addMenuInBasket(user.getId(), restaurant.getId(), basketMenu1);
        RequestOrder requestOrder = RequestOrder.builder().address("어딘가").restaurantId(restaurant.getId()).build();

        orderService.order(user.getId(), requestOrder);

        List<Order> content = orderService.findOrders(user.getId(), null).getContent();
        Order order = content.get(0);

        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.ORDER_REQUEST);
        orderService.refuseOrder(owner.getId(), restaurant.getId(), order.getId());
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.ORDER_CANCEL);
    }

    @Test
    @DisplayName("특정 주문 상태에 주문 거절시 예외를 발생한다.")
    public void refuseOrderWrongOrderStatus() {
        BasketMenu basketMenu1 = BasketMenu.builder().quantity(2).menuId(menu1.getId()).build();
        basketService.addMenuInBasket(user.getId(), restaurant.getId(), basketMenu1);
        RequestOrder requestOrder = RequestOrder.builder().address("어딘가").restaurantId(restaurant.getId()).build();

        orderService.order(user.getId(), requestOrder);

        List<Order> content = orderService.findOrders(user.getId(), null).getContent();
        Order order = content.get(0);

        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.ORDER_REQUEST);
        order.updateOrderStatus(OrderStatus.DELIVERY_COMPLETE);

        assertThrows(CustomException.class, () -> orderService.refuseOrder(owner.getId(), restaurant.getId(), order.getId()));
    }

    @Test
    @DisplayName("정상적으로 주문 취소")
    public void cancelOrder() {
        BasketMenu basketMenu1 = BasketMenu.builder().quantity(2).menuId(menu1.getId()).build();
        basketService.addMenuInBasket(user.getId(), restaurant.getId(), basketMenu1);
        RequestOrder requestOrder = RequestOrder.builder().address("어딘가").restaurantId(restaurant.getId()).build();

        orderService.order(user.getId(), requestOrder);

        List<Order> content = orderService.findOrders(user.getId(), null).getContent();
        Order order = content.get(0);

        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.ORDER_REQUEST);
        orderService.cancelOrder(user.getId() , order.getId());
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.ORDER_CANCEL);
    }

    @Test
    @DisplayName("특정 주문 상태에 주문 취소시 예외를 발생한다.")
    public void cancelOrderWrongOrderStatus() {
        BasketMenu basketMenu1 = BasketMenu.builder().quantity(2).menuId(menu1.getId()).build();
        basketService.addMenuInBasket(user.getId(), restaurant.getId(), basketMenu1);
        RequestOrder requestOrder = RequestOrder.builder().address("어딘가").restaurantId(restaurant.getId()).build();

        orderService.order(user.getId(), requestOrder);

        List<Order> content = orderService.findOrders(user.getId(), null).getContent();
        Order order = content.get(0);

        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.ORDER_REQUEST);
        order.updateOrderStatus(OrderStatus.DELIVERY_COMPLETE);

        assertThrows(CustomException.class, () -> orderService.cancelOrder(user.getId(), order.getId()));
    }
}