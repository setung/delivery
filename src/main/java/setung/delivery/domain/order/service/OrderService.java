package setung.delivery.domain.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import setung.delivery.domain.basket.model.BasketMenu;
import setung.delivery.domain.basket.service.BasketService;
import setung.delivery.domain.menu.model.Menu;
import setung.delivery.domain.menu.service.MenuService;
import setung.delivery.domain.order.aop.SaveOrderToFirestoreForRestaurant;
import setung.delivery.domain.order.aop.SaveOrderToFirestoreForUser;
import setung.delivery.domain.order.model.Order;
import setung.delivery.domain.order.model.OrderMenu;
import setung.delivery.domain.order.model.OrderStatus;
import setung.delivery.domain.order.repository.OrderRepository;
import setung.delivery.domain.restaurant.model.Restaurant;
import setung.delivery.domain.restaurant.service.RestaurantService;
import setung.delivery.domain.user.model.User;
import setung.delivery.domain.user.service.UserService;
import setung.delivery.exception.CustomException;
import setung.delivery.exception.ErrorCode;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMenuService orderMenuService;
    private final BasketService basketService;
    private final MenuService menuService;
    private final RestaurantService restaurantService;
    private final UserService userService;

    @SaveOrderToFirestoreForRestaurant
    public Order order(long userId, long restaurantId) {
        List<BasketMenu> basketMenus = basketService.findBasketMenus(userId, restaurantId);
        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);
        User user = userService.findUserById(userId);
        int totalPrice = 0;

        Order order = Order.builder()
                .orderStatus(OrderStatus.ORDER_REQUEST)
                .address(user.getAddress())
                .restaurant(restaurant)
                .user(user)
                .build();

        order = orderRepository.save(order);

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
            orderMenuService.save(orderMenu);

            // 메뉴 수량 업데이트
            menu.updateQuantity(menu.getQuantity() - basketMenu.getQuantity());
        }

        basketService.clearBasket(userId, restaurantId); // 주문 완료후 장바구니 삭제
        order.updateTotalPrice(totalPrice);

        return order;
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

    public Page<Order> findOrders(Specification<Order> spec, Pageable pageable) {
        return orderRepository.findAll(spec, pageable);
    }

    @SaveOrderToFirestoreForRestaurant
    @SaveOrderToFirestoreForUser
    public Order approveOrder(long ownerId, long restaurantId, long orderId) {
        restaurantService.findRestaurantByIdAndOwnerId(ownerId, restaurantId);
        Order order = findByIdAndRestaurantId(orderId, restaurantId);

        if (order.getOrderStatus() != OrderStatus.ORDER_REQUEST && order.getOrderStatus() != OrderStatus.ORDER_REFUSED)
            throw new CustomException(ErrorCode.BAD_REQUEST_ORDER);

        order.updateOrderStatus(OrderStatus.ORDER_APPROVAL);

        return order;
    }

    @SaveOrderToFirestoreForRestaurant
    @SaveOrderToFirestoreForUser
    public Order refuseOrder(long ownerId, long restaurantId, long orderId) {
        restaurantService.findRestaurantByIdAndOwnerId(ownerId, restaurantId);
        Order order = findByIdAndRestaurantId(orderId, restaurantId);
        List<OrderMenu> orderMenus = orderMenuService.findByOrderId(orderId);

        if (order.getOrderStatus() != OrderStatus.ORDER_REQUEST && order.getOrderStatus() != OrderStatus.ORDER_APPROVAL)
            throw new CustomException(ErrorCode.BAD_REQUEST_ORDER);

        order.updateOrderStatus(OrderStatus.ORDER_REFUSED);

        for (OrderMenu orderMenu : orderMenus) {
            Menu menu = menuService.findByIdAndRestaurantId(orderMenu.getMenu().getId(), restaurantId);
            menu.updateQuantity(menu.getQuantity() + orderMenu.getQuantity());
        }

        return order;
    }

    public Order findByIdAndRestaurantId(long orderId, long restaurantId) {
        Order order = orderRepository.findByIdAndRestaurantId(orderId, restaurantId);

        if (order == null)
            throw new CustomException(ErrorCode.NOT_FOUND_ORDER);

        return order;
    }

    @SaveOrderToFirestoreForRestaurant
    public Order cancelOrder(long userId, long orderId) {
        Order order = orderRepository.findByOrderIdAndUserId(orderId, userId);
        List<OrderMenu> orderMenus = orderMenuService.findByOrderId(orderId);

        if (order == null)
            throw new CustomException(ErrorCode.NOT_FOUND_ORDER);
        if (order.getOrderStatus() != OrderStatus.ORDER_REQUEST)
            throw new CustomException(ErrorCode.BAD_REQUEST_ORDER);

        order.updateOrderStatus(OrderStatus.ORDER_CANCEL);

        for (OrderMenu orderMenu : orderMenus) {
            Menu menu = menuService.findByIdAndRestaurantId(orderMenu.getMenu().getId(), order.getRestaurant().getId());
            menu.updateQuantity(menu.getQuantity() + orderMenu.getQuantity());
        }

        return order;
    }

}
