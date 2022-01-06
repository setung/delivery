package setung.delivery.domain.rider.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import setung.delivery.domain.BaseEntity;
import setung.delivery.domain.order.model.Order;
import setung.delivery.controller.rider.dto.RiderDto;
import setung.delivery.utils.geo.LatLonData;

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
    private Double lon;
    private Double lat;
    private Double deliveryRange;

    @JsonIgnore
    @OneToMany(mappedBy = "rider", fetch = FetchType.LAZY)
    List<Order> orders = new ArrayList<>();

    public Rider(RiderDto riderDto) {
        id = riderDto.getId();
        email = riderDto.getEmail();
        tel = riderDto.getTel();
        name = riderDto.getName();
        address = riderDto.getAddress();
        password = riderDto.getPassword();
        deliveryRange = riderDto.getDeliveryRange();
    }

    public Rider setLatLon(LatLonData latLon) {
        this.lon = latLon.getLon();
        this.lat = latLon.getLat();
        return this;
    }
}
