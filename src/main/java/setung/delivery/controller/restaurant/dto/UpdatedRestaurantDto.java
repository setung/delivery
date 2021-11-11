package setung.delivery.controller.restaurant.dto;

import lombok.*;
import setung.delivery.domain.restaurant.model.RestaurantCategory;

import java.time.LocalTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdatedRestaurantDto {

    private String name;
    private String address;
    private String tel;
    private String status;
    private LocalTime openAt;
    private LocalTime closeAt;
    private RestaurantCategory category;

}
