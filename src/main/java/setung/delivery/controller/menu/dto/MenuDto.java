package setung.delivery.controller.menu.dto;

import lombok.*;
import setung.delivery.domain.menu.model.Menu;
import setung.delivery.domain.menu.model.MenuCategory;
import setung.delivery.domain.restaurant.model.Restaurant;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuDto {

    private Long id;
    private Restaurant restaurant;
    private String name;
    private int price;
    private int quantity;
    private MenuCategory category;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Menu toMenu() {
        return Menu.builder()
                .restaurant(restaurant)
                .name(name)
                .price(price)
                .quantity(quantity)
                .category(category)
                .build();
    }
}
