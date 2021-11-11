package setung.delivery.domain.rider.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import setung.delivery.domain.BaseEntity;
import setung.delivery.domain.order.model.Order;
import setung.delivery.controller.rider.dto.RiderDto;

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

    public RiderDto toRiderDto() {
        return RiderDto.builder()
                .id(id)
                .email(email)
                .tel(tel)
                .name(name)
                .address(address)
                .password(password)
                .build();
    }
}
