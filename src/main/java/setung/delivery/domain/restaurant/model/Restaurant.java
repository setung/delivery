package setung.delivery.domain.restaurant.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import setung.delivery.domain.BaseEntity;
import setung.delivery.domain.menu.model.Menu;
import setung.delivery.domain.owner.model.Owner;
import setung.delivery.controller.restaurant.dto.RestaurantDto;
import setung.delivery.controller.restaurant.dto.UpdatedRestaurantDto;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Restaurant extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "restaurant_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private Owner owner;

    private String name;
    private String address;
    private String tel;
    private String status;
    private LocalTime openAt;
    private LocalTime closeAt;

    @Enumerated(EnumType.STRING)
    private RestaurantCategory category;

    @JsonIgnore
    @OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY)
    private List<Menu> menus;

    public Restaurant(RestaurantDto restaurantDto) {
        id = restaurantDto.getId();
        owner = new Owner(restaurantDto.getOwner());
        name = restaurantDto.getName();
        address = restaurantDto.getAddress();
        tel = restaurantDto.getTel();
        status = restaurantDto.getStatus();
        openAt = restaurantDto.getOpenAt();
        closeAt = restaurantDto.getCloseAt();
        category = restaurantDto.getCategory();
    }

    public void update(UpdatedRestaurantDto dto) {
        name = dto.getName();
        address = dto.getAddress();
        tel = dto.getTel();
        status = dto.getStatus();
        openAt = dto.getOpenAt();
        closeAt = dto.getCloseAt();
        category = dto.getCategory();
    }
}
