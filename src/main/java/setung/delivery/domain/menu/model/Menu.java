package setung.delivery.domain.menu.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import setung.delivery.domain.BaseEntity;
import setung.delivery.controller.menu.dto.MenuDto;
import setung.delivery.domain.restaurant.model.Restaurant;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Menu extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "menu_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    private String name;
    private int price;
    private int quantity;

    @Enumerated(EnumType.STRING)
    private MenuCategory category;

    public Menu(MenuDto menuDto) {
        id = menuDto.getId();
        restaurant = new Restaurant(menuDto.getRestaurant());
        name = menuDto.getName();
        price = menuDto.getPrice();
        quantity = menuDto.getQuantity();
        category = menuDto.getCategory();
    }

    public void updateQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void updateMenu(MenuDto menuDto) {
        name = menuDto.getName();
        price = menuDto.getPrice();
        quantity = menuDto.getQuantity();
        category = menuDto.getCategory();
    }
}
