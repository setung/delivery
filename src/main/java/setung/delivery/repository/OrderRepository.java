package setung.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import setung.delivery.domain.order.Order;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o FROM Order o where o.id = :id AND o.user.id = :user")
    Order findByOrderIdAndUserId(@Param("id") long orderId, @Param("user") long userId);

}
