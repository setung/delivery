package setung.delivery.controller.restaurant.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import setung.delivery.controller.owner.dto.OwnerDto;
import setung.delivery.domain.restaurant.model.Restaurant;
import setung.delivery.domain.restaurant.model.RestaurantCategory;

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
    private OwnerDto owner;
    private String name;
    private String address;
    private String tel;
    private String status;
    private LocalTime openAt;
    private LocalTime closeAt;
    private RestaurantCategory category;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public RestaurantDto(Restaurant restaurant) {
        id = restaurant.getId();
        owner = new OwnerDto(restaurant.getOwner());
        address = restaurant.getAddress();
        tel = restaurant.getTel();
        status = restaurant.getStatus();
        openAt = restaurant.getOpenAt();
        closeAt = restaurant.getCloseAt();
        category = restaurant.getCategory();
        createdAt = restaurant.getCreatedAt();
        updatedAt = getUpdatedAt();
    }

}
