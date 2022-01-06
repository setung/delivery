package setung.delivery.service.order;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import setung.delivery.controller.rider.dto.RiderDto;
import setung.delivery.controller.user.dto.UserDto;
import setung.delivery.domain.basket.model.BasketMenu;
import setung.delivery.domain.menu.model.Menu;
import setung.delivery.controller.menu.dto.MenuDto;
import setung.delivery.domain.order.model.Order;
import setung.delivery.domain.order.model.OrderStatus;
import setung.delivery.domain.order.service.OrderService;
import setung.delivery.domain.owner.model.Owner;
import setung.delivery.domain.restaurant.model.Restaurant;
import setung.delivery.controller.restaurant.dto.RestaurantDto;
import setung.delivery.domain.rider.model.Rider;
import setung.delivery.domain.rider.service.RiderLoginService;
import setung.delivery.domain.rider.service.RiderService;
import setung.delivery.domain.user.model.User;
import setung.delivery.domain.user.service.UserService;
import setung.delivery.exception.CustomException;
import setung.delivery.domain.order.repository.OrderMenuRepository;
import setung.delivery.domain.owner.repository.OwnerRepository;
import setung.delivery.domain.user.repository.UserRepository;
import setung.delivery.domain.menu.service.MenuService;
import setung.delivery.domain.basket.service.BasketService;
import setung.delivery.domain.restaurant.service.RestaurantService;

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
    UserService userService;
    @Autowired
    RiderService riderService;
    @Autowired
    RiderLoginService riderLoginService;

    User user;
    Owner owner;
    Restaurant restaurant;
    Menu menu1;
    Menu menu2;
    Rider rider1;
    Rider rider2;

    @BeforeEach
    void beforeEach() {
        user = userService.join(UserDto.builder().password("1234").address("서울 강남구 강남대로94길 27").build());
        owner = Owner.builder().build();
        RestaurantDto restaurantDto = RestaurantDto.builder().address("서울 강남구 강남대로94길 27").build();
        MenuDto menuDto1 = MenuDto.builder().price(10000).quantity(10).name("menu1").build();
        MenuDto menuDto2 = MenuDto.builder().price(1000).quantity(5).name("menu2").build();

        ownerRepository.save(owner);
        restaurant = restaurantService.register(owner.getId(), restaurantDto);
        menu1 = menuService.registerMenu(owner.getId(), restaurant.getId(), menuDto1);
        menu2 = menuService.registerMenu(owner.getId(), restaurant.getId(), menuDto2);

        rider1 = riderService.join(RiderDto.builder().email("rider1@gmail.com").password("1234")
                .address("서울 강남구 강남대로94길 27").deliveryRange(5.0).build());
        rider2 = riderService.join(RiderDto.builder().email("rider2@gmail.com").password("1234")
                .address("서울 강남구 강남대로94길 27").deliveryRange(5.0).build());
    }

    @Test
    @DisplayName("동시에 같은 주문에 배달 승인시 예외 발생")
    void duplicateRequestApproveDelivery() {
        BasketMenu basketMenu1 = BasketMenu.builder().quantity(2).menuId(menu1.getId()).build();
        BasketMenu basketMenu2 = BasketMenu.builder().quantity(2).menuId(menu2.getId()).build();
        basketService.addMenuInBasket(user.getId(), restaurant.getId(), basketMenu1);
        basketService.addMenuInBasket(user.getId(), restaurant.getId(), basketMenu2);

        Order order = orderService.order(user.getId(), restaurant.getId());
        orderService.approveOrder(owner.getId(), restaurant.getId(), order.getId());


        new Thread(()-> orderService.approveDelivery(rider1.getId(), order.getId())).start();
        new Thread(()-> orderService.approveDelivery(rider2.getId(), order.getId())).start();
        //assertThat(rider1.getOrders()).hasSize(1);
        //assertThat(rider2.getOrders()).hasSize(1);
    }

    @Test
    @DisplayName("배달 요청이 된 주문에 다른 Rider가 배달 요청시 예외 발생")
    void approveDoneDelivery() {
        BasketMenu basketMenu1 = BasketMenu.builder().quantity(2).menuId(menu1.getId()).build();
        BasketMenu basketMenu2 = BasketMenu.builder().quantity(2).menuId(menu2.getId()).build();
        basketService.addMenuInBasket(user.getId(), restaurant.getId(), basketMenu1);
        basketService.addMenuInBasket(user.getId(), restaurant.getId(), basketMenu2);

        Order order = orderService.order(user.getId(), restaurant.getId());
        orderService.approveOrder(owner.getId(), restaurant.getId(), order.getId());
        orderService.approveDelivery(rider1.getId(), order.getId());

        assertThrows(CustomException.class, () -> {
            orderService.approveDelivery(rider2.getId(), order.getId());
        });
    }

    @Test
    @DisplayName("정상적으로 Rider의 배달 요청 승인")
    void approveDelivery() {
        BasketMenu basketMenu1 = BasketMenu.builder().quantity(2).menuId(menu1.getId()).build();
        BasketMenu basketMenu2 = BasketMenu.builder().quantity(2).menuId(menu2.getId()).build();
        basketService.addMenuInBasket(user.getId(), restaurant.getId(), basketMenu1);
        basketService.addMenuInBasket(user.getId(), restaurant.getId(), basketMenu2);

        Order order = orderService.order(user.getId(), restaurant.getId());
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.ORDER_REQUEST);

        orderService.approveOrder(owner.getId(), restaurant.getId(), order.getId());
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.ORDER_APPROVAL);

        orderService.approveDelivery(rider1.getId(), order.getId());
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.IN_DELIVERY);
        assertThat(order.getRider()).isSameAs(rider1);
    }

    @Test
    @DisplayName("정상적으로 주문")
    void order() {
        BasketMenu basketMenu1 = BasketMenu.builder().quantity(2).menuId(menu1.getId()).build();
        BasketMenu basketMenu2 = BasketMenu.builder().quantity(2).menuId(menu2.getId()).build();
        basketService.addMenuInBasket(user.getId(), restaurant.getId(), basketMenu1);
        basketService.addMenuInBasket(user.getId(), restaurant.getId(), basketMenu2);

        orderService.order(user.getId(), restaurant.getId());

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

        menu1 = menuService.findByIdAndRestaurantId(menu1.getId(), restaurant.getId());
        updateQuantity();

        assertThrows(CustomException.class, () -> orderService.order(user.getId(), restaurant.getId()));
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

        orderService.order(user.getId(), restaurant.getId());

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

        orderService.order(user.getId(), restaurant.getId());

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

        orderService.order(user.getId(), restaurant.getId());

        List<Order> content = orderService.findOrders(user.getId(), null).getContent();
        Order order = content.get(0);

        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.ORDER_REQUEST);
        orderService.refuseOrder(owner.getId(), restaurant.getId(), order.getId());
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.ORDER_REFUSED);
    }

    @Test
    @DisplayName("특정 주문 상태에 주문 거절시 예외를 발생한다.")
    public void refuseOrderWrongOrderStatus() {
        BasketMenu basketMenu1 = BasketMenu.builder().quantity(2).menuId(menu1.getId()).build();
        basketService.addMenuInBasket(user.getId(), restaurant.getId(), basketMenu1);

        orderService.order(user.getId(), restaurant.getId());

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

        orderService.order(user.getId(), restaurant.getId());

        List<Order> content = orderService.findOrders(user.getId(), null).getContent();
        Order order = content.get(0);

        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.ORDER_REQUEST);
        orderService.cancelOrder(user.getId(), order.getId());
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.ORDER_CANCEL);
    }

    @Test
    @DisplayName("특정 주문 상태에 주문 취소시 예외를 발생한다.")
    public void cancelOrderWrongOrderStatus() {
        BasketMenu basketMenu1 = BasketMenu.builder().quantity(2).menuId(menu1.getId()).build();
        basketService.addMenuInBasket(user.getId(), restaurant.getId(), basketMenu1);

        orderService.order(user.getId(), restaurant.getId());

        List<Order> content = orderService.findOrders(user.getId(), null).getContent();
        Order order = content.get(0);

        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.ORDER_REQUEST);
        order.updateOrderStatus(OrderStatus.DELIVERY_COMPLETE);

        assertThrows(CustomException.class, () -> orderService.cancelOrder(user.getId(), order.getId()));
    }
}