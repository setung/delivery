package setung.delivery.domain.order.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import setung.delivery.domain.BaseEntity;
import setung.delivery.controller.order.dto.OrderDto;
import setung.delivery.domain.restaurant.model.Restaurant;
import setung.delivery.domain.rider.model.Rider;
import setung.delivery.domain.user.model.User;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn(name = "rider_id")
    private Rider rider;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private String address;
    private int totalPrice;

    public void updateTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void updateOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public OrderDto toOrderDto() {
        return OrderDto.builder()
                .id(id)
                .user(user)
                .restaurant(restaurant)
                .status(orderStatus)
                .address(address)
                .totalPrice(totalPrice)
                .createdAt(getCreatedAt())
                .build();
    }
}
