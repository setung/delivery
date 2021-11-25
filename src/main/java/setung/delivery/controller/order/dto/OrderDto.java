package setung.delivery.controller.order.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import setung.delivery.controller.restaurant.dto.RestaurantDto;
import setung.delivery.controller.user.dto.UserDto;
import setung.delivery.domain.order.model.Order;
import setung.delivery.domain.order.model.OrderStatus;
import setung.delivery.domain.owner.model.Owner;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

    private Long id;
    @JsonIgnore
    private UserDto user;
    @JsonIgnore
    private RestaurantDto restaurant;
    private OrderStatus status;
    private String address;
    private int totalPrice;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public OrderDto(Order order) {
        id = order.getId();
        user = new UserDto(order.getUser());
        restaurant = new RestaurantDto(order.getRestaurant());
        status = order.getOrderStatus();
        address = order.getAddress();
        totalPrice = order.getTotalPrice();
        createdAt = order.getCreatedAt();
        updatedAt = order.getUpdatedAt();
    }

}
