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

    @Around("@annotation(setung.delivery.domain.order.aop.SaveOrderToFirestoreForRestaurant)")
    public Object saveOrderToFirestoreForRestaurant(ProceedingJoinPoint joinPoint) throws Throwable {
        Order order = (Order) joinPoint.proceed();
        String collectionName = "restaurant_" + order.getRestaurant().getId();
        saveOrderToFirestore(collectionName, order);
        return order;
    }

    @Around("@annotation(setung.delivery.domain.order.aop.SaveOrderToFirestoreForUser)")
    public Object saveOrderToFirestoreForUser(ProceedingJoinPoint joinPoint) throws Throwable {
        Order order = (Order) joinPoint.proceed();
        String collectionName = "user_" + order.getUser().getId();
        saveOrderToFirestore(collectionName, order);
        return order;
    }

    private void saveOrderToFirestore(String collectionName, Order order) {
        OrderDto orderDto = OrderDto.builder()
                .id(order.getId())
                .status(order.getOrderStatus())
                .address(order.getAddress())
                .build();

        firestoreUtil.insertData(collectionName,
                getOrderDocumentName(order.getId()), orderDto);
    }

    private String getOrderDocumentName(long orderId) {
        return "order_" + orderId;
    }
}
