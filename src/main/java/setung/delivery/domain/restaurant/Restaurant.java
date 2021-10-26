package setung.delivery.domain.restaurant;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import setung.delivery.domain.BaseEntity;
import setung.delivery.domain.menu.Menu;
import setung.delivery.domain.owner.Owner;

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

    public RestaurantDto toRestaurantDto() {
        return RestaurantDto.builder()
                .id(id)
                .owner(owner)
                .name(name)
                .address(address)
                .tel(tel)
                .status(status)
                .openAt(openAt)
                .closeAt(closeAt)
                .category(category)
                .createdAt(getCreatedAt())
                .updatedAt(getUpdatedAt())
                .build();
    }
}
