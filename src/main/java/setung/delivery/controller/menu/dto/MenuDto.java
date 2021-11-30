package setung.delivery.controller.menu.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import setung.delivery.controller.restaurant.dto.RestaurantDto;
import setung.delivery.domain.menu.model.Menu;
import setung.delivery.domain.menu.model.MenuCategory;
import setung.delivery.domain.menu.model.MenuImage;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuDto {

    private Long id;
    @JsonIgnore
    private RestaurantDto restaurant;
    private String name;
    private int price;
    private int quantity;
    private MenuCategory category;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<MenuImage> images;

    public MenuDto(Menu menu) {
        id = menu.getId();
        restaurant = new RestaurantDto(menu.getRestaurant());
        name = menu.getName();
        price = menu.getPrice();
        quantity = menu.getQuantity();
        category = menu.getCategory();
        createdAt = menu.getCreatedAt();
        updatedAt = menu.getUpdatedAt();
        images = menu.getImages();
    }

}
