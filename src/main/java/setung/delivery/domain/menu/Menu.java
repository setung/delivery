package setung.delivery.domain.menu;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import setung.delivery.domain.restaurant.Restaurant;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Menu {

    @Id
    @GeneratedValue
    @Column(name = "menu_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    Restaurant restaurant;

    private String name;
    private int price;
    private int quantity;

    @Enumerated(EnumType.STRING)
    private MenuCategory category;
}
