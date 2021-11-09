package setung.delivery.domain.rider;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import setung.delivery.domain.BaseEntity;
import setung.delivery.domain.order.Order;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Rider extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "rider_id")
    private Long id;

    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String tel;

    private String name;
    private String address;
    private String password;

    @JsonIgnore
    @OneToMany(mappedBy = "rider", fetch = FetchType.LAZY)
    List<Order> orders = new ArrayList<>();
}
