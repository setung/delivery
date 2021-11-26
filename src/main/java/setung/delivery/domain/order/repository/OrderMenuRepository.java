package setung.delivery.domain.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import setung.delivery.domain.order.model.OrderMenu;

import java.util.List;

public interface OrderMenuRepository extends JpaRepository<OrderMenu, Long> {

    List<OrderMenu> findByOrderId(long orderId);
}
