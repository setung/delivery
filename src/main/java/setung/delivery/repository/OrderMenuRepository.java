package setung.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import setung.delivery.domain.order.OrderMenu;

public interface OrderMenuRepository extends JpaRepository<OrderMenu, Long> {
}
