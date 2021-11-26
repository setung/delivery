package setung.delivery.domain.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import setung.delivery.domain.order.model.OrderMenu;
import setung.delivery.domain.order.repository.OrderMenuRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderMenuService {

    private final OrderMenuRepository orderMenuRepository;

    public void save(OrderMenu orderMenu) {
        orderMenuRepository.save(orderMenu);
    }

    public List<OrderMenu> findByOrderId(long orderId) {
        return orderMenuRepository.findByOrderId(orderId);
    }
}
