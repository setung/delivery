package setung.delivery.domain.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import setung.delivery.domain.restaurant.Restaurant;
import setung.delivery.domain.user.User;

import java.time.LocalDateTime;
import java.util.List;

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
