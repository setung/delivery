package setung.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import setung.delivery.domain.order.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
