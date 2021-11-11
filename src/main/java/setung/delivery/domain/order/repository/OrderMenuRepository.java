package setung.delivery.domain.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import setung.delivery.domain.order.model.OrderMenu;

public interface OrderMenuRepository extends JpaRepository<OrderMenu, Long> {
}
