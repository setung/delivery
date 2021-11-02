package setung.delivery.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import setung.delivery.argumentresolver.LoginUserId;
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

}
