package setung.delivery.domain.menu;

import lombok.*;
import setung.delivery.domain.restaurant.Restaurant;

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
