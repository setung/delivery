package setung.delivery.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import setung.delivery.argumentresolver.LoginUserId;
import setung.delivery.domain.order.Order;
import setung.delivery.domain.order.OrderDto;
import setung.delivery.domain.order.OrderStatus;
import setung.delivery.domain.order.RequestOrder;
import setung.delivery.service.order.OrderService;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public void order(@LoginUserId long userId, @RequestBody RequestOrder requestOrder) {
        orderService.order(userId, requestOrder);
    }

    @GetMapping("/{orderId}")
    public OrderDto findOrderById(@LoginUserId long userId, @PathVariable long orderId) {
        return orderService.findOrderById(userId, orderId).toOrderDto();
    }

    @GetMapping
    public Page<OrderDto> findAll(@LoginUserId long userId, @RequestParam(required = false) OrderStatus orderStatus, Pageable pageable) {
        if (orderStatus == null) {
            return orderService.findOrders(userId, pageable).map(Order::toOrderDto);
        } else {
            return orderService.findOrders(userId, orderStatus, pageable).map(Order::toOrderDto);
        }
    }
}
