package setung.delivery.domain.restaurant;

import lombok.*;
import setung.delivery.domain.owner.Owner;

import java.time.LocalTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantDto {

    private Long id;
    private Owner owner;
    private String name;
    private String address;
    private String tel;
    private String status;
    private LocalTime openAt;
    private LocalTime closeAt;
    private RestaurantCategory category;

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
