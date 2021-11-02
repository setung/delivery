package setung.delivery.service.order;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import setung.delivery.domain.basket.BasketMenu;
import setung.delivery.domain.menu.Menu;
import setung.delivery.domain.order.*;
import setung.delivery.domain.restaurant.Restaurant;
import setung.delivery.domain.user.User;
import setung.delivery.exception.CustomException;
import setung.delivery.exception.ErrorCode;
import setung.delivery.repository.OrderMenuRepository;
import setung.delivery.repository.OrderRepository;
import setung.delivery.service.Menu.MenuService;
import setung.delivery.service.basket.BasketService;
import setung.delivery.service.restaurant.RestaurantService;
import setung.delivery.service.user.UserService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMenuRepository orderMenuRepository;
    private final BasketService basketService;
    private final MenuService menuService;
    private final RestaurantService restaurantService;
    private final UserService userService;

    public void order(long userId, RequestOrder requestOrder) {
        long restaurantId = requestOrder.getRestaurantId();
        List<BasketMenu> basketMenus = basketService.findBasketMenus(userId, restaurantId);
        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);
        User user = userService.findUserById(userId);
        int totalPrice = 0;

        Order order = Order.builder()
                .orderStatus(OrderStatus.BEFORE_PAYMENT)
                .address(requestOrder.getAddress())
                .restaurant(restaurant)
                .user(user)
                .build();
        orderRepository.save(order);

        // 장바구니에 있는 BasketMenu 순회
        for (BasketMenu basketMenu : basketMenus) {
            Menu menu = menuService.findByIdAndRestaurantId(basketMenu.getMenuId(), restaurantId);
            totalPrice += menu.getPrice() * basketMenu.getQuantity();

            // 재고 확인
            if (menu.getQuantity() < basketMenu.getQuantity())
                throw new CustomException(ErrorCode.BAD_REQUEST_MENU, menu.getName() + " 재고를 확인해 주세요");

            OrderMenu orderMenu = OrderMenu.builder()
                    .order(order)
                    .menu(menu)
                    .quantity(basketMenu.getQuantity())
                    .build();
            orderMenuRepository.save(orderMenu);

            // 메뉴 수량 업데이트
            menu.updateQuantity(menu.getQuantity() - basketMenu.getQuantity());
        }

        basketService.clearBasket(userId, restaurantId); // 주문 완료후 장바구니 삭제
        order.updateTotalPrice(totalPrice);
    }

    public Order findOrderById(long userId, long orderId) {
        Order order = orderRepository.findByOrderIdAndUserId(orderId, userId);

        if (order == null)
            throw new CustomException(ErrorCode.NOT_FOUND_ORDER);

        return order;
    }

    public Page<Order> findOrders(long userId, Pageable pageable) {
        return orderRepository.findOrderByUserId(userId, pageable);
    }

    public Page<Order> findOrders(long userId, OrderStatus orderStatus, Pageable pageable) {
        return orderRepository.findOrderByUserIdAndOrderStatus(userId, orderStatus, pageable);
    }
}
