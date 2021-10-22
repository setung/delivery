package setung.delivery.domain.menu;

import lombok.*;
import setung.delivery.domain.restaurant.Restaurant;

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
