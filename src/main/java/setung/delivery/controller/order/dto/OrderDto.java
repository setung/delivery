package setung.delivery.controller.order.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import setung.delivery.domain.order.model.OrderStatus;
import setung.delivery.domain.restaurant.model.Restaurant;
import setung.delivery.domain.user.model.User;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

    private Long id;
    @JsonIgnore
    private User user;
    @JsonIgnore
    private Restaurant restaurant;
    private OrderStatus status;
    private String address;
    private int totalPrice;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
