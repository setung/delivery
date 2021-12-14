package setung.delivery.domain.order.aop;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import setung.delivery.controller.order.dto.OrderDto;
import setung.delivery.domain.order.model.Order;
import setung.delivery.utils.firebase.FirestoreUtil;

@Aspect
@Component
@RequiredArgsConstructor
public class OrderAspect {

    private final FirestoreUtil firestoreUtil;

    @Around("@annotation(setung.delivery.domain.order.aop.SaveOrderToFirestore)")
    public Object saveOrderToFirestore(ProceedingJoinPoint joinPoint) throws Throwable {
        Order order = (Order) joinPoint.proceed();
        saveOrderToFirestore(order);
        return order;
    }

    private void saveOrderToFirestore(Order order) {
        OrderDto orderDto = OrderDto.builder()
                .id(order.getId())
                .status(order.getOrderStatus())
                .address(order.getAddress())
                .build();

        firestoreUtil.insertData(getOrderCollectionName(order.getRestaurant().getId()),
                getOrderDocumentName(order.getId()), orderDto);
    }

    private String getOrderCollectionName(long restaurantId) {
        return "restaurant_" + restaurantId;
    }

    private String getOrderDocumentName(long orderId) {
        return "order_" + orderId;
    }
}
