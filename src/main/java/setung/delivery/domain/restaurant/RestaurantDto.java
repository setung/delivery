package setung.delivery.domain.restaurant;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import setung.delivery.domain.owner.Owner;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantDto {

    private Long id;

    @JsonIgnore
    private Owner owner;
    private String name;
    private String address;
    private String tel;
    private String status;
    private LocalTime openAt;
    private LocalTime closeAt;
    private RestaurantCategory category;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Restaurant toRestaurant() {
        return Restaurant.builder()
                .owner(owner)
                .name(name)
                .address(address)
                .tel(tel)
                .status(status)
                .openAt(openAt)
                .closeAt(closeAt)
                .category(category)
                .build();
    }
}
