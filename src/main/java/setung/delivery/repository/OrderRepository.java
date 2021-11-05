package setung.delivery.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import setung.delivery.domain.order.Order;
import setung.delivery.domain.order.OrderStatus;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o FROM Order o where o.id = :id AND o.user.id = :user")
    Order findByOrderIdAndUserId(@Param("id") long orderId, @Param("user") long userId);

    @Query("SELECT o FROM Order o where o.user.id = :user")
    Page<Order> findOrderByUserId(@Param("user") long userId, Pageable pageable);

    @Query("SELECT o FROM Order o where o.user.id = :user AND o.orderStatus = :orderStatus")
    Page<Order> findOrderByUserIdAndOrderStatus(@Param("user") long userId, @Param("orderStatus") OrderStatus orderStatus, Pageable pageable);

    @Query("SELECT o FROM Order o where o.id = :orderId AND o.restaurant.id = :restaurantId")
    Order findByIdAndRestaurantId(@Param("orderId") long orderId, @Param("restaurantId") long restaurantId);
}
