package setung.delivery.controller.menu.dto;

import lombok.*;
import setung.delivery.controller.restaurant.dto.RestaurantDto;
import setung.delivery.domain.menu.model.Menu;
import setung.delivery.domain.menu.model.MenuCategory;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuDto {

    private Long id;
    private RestaurantDto restaurant;
    private String name;
    private int price;
    private int quantity;
    private MenuCategory category;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public MenuDto(Menu menu) {
        id=menu.getId();
        restaurant = new RestaurantDto(menu.getRestaurant());
        name = menu.getName();
        price = menu.getPrice();
        quantity = menu.getQuantity();
        category = menu.getCategory();
        createdAt = menu.getCreatedAt();
        updatedAt = menu.getUpdatedAt();
    }

}
