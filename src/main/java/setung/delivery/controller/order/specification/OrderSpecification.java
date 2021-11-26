package setung.delivery.controller.order.specification;

import org.springframework.data.jpa.domain.Specification;
import setung.delivery.domain.order.model.Order;
import setung.delivery.domain.order.model.OrderStatus;

public class OrderSpecification {

    public static Specification<Order> equalOrderStatus(OrderStatus orderStatus) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("orderStatus"), orderStatus);
    }

    public static Specification<Order> equalUserId(long userId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("user").get("id"), userId);
    }
}
