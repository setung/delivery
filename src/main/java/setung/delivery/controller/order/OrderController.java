package setung.delivery.controller.order;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import setung.delivery.argumentresolver.LoginOwnerId;
import setung.delivery.argumentresolver.LoginUserId;
import setung.delivery.controller.order.dto.OrderDto;
import setung.delivery.domain.order.model.OrderStatus;
import setung.delivery.domain.order.RequestOrder;
import setung.delivery.domain.order.service.OrderService;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/orders")
    public void order(@LoginUserId long userId, @RequestBody RequestOrder requestOrder) {
        orderService.order(userId, requestOrder);
    }

    @GetMapping("/users/{orderId}")
    public OrderDto findOrderById(@LoginUserId long userId, @PathVariable long orderId) {
        return new OrderDto(orderService.findOrderById(userId, orderId));
    }

    @GetMapping("/users/orders")
    public Page<OrderDto> findAll(@LoginUserId long userId, @RequestParam(required = false) OrderStatus orderStatus, Pageable pageable) {
        if (orderStatus == null) {
            return orderService.findOrders(userId, pageable).map(order -> new OrderDto(order));
        } else {
            return orderService.findOrders(userId, orderStatus, pageable).map(order -> new OrderDto(order));
        }
    }

    @PostMapping("/restaurants/{restaurantId}/orders/{orderId}/approve")
    public void approveOrder(@LoginOwnerId long ownerId, @PathVariable long restaurantId, @PathVariable long orderId) {
        orderService.approveOrder(ownerId, restaurantId, orderId);
    }

    @PostMapping("/restaurants/{restaurantId}/orders/{orderId}/refuse")
    public void refuseOrder(@LoginOwnerId long ownerId, @PathVariable long restaurantId, @PathVariable long orderId) {
        orderService.refuseOrder(ownerId, restaurantId, orderId);
    }

    @PostMapping("/orders/{orderId}/cancel")
    public void cancelOrder(@LoginUserId long userId, @PathVariable long orderId) {
        orderService.cancelOrder(userId, orderId);
    }
}