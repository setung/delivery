package setung.delivery.domain.restaurant;

import lombok.*;

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
