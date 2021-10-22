package setung.delivery.domain.owner;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import setung.delivery.domain.restaurant.Restaurant;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Owner {

    @Id
    @GeneratedValue
    @Column(name = "owner_id")
    private Long id;
    private String name;

    @Column(unique = true)
    private String email;
    private String address;
    private String password;

    @Column(unique = true)
    private String tel;

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    List<Restaurant> restaurants;

}
