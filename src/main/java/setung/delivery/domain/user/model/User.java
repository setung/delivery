package setung.delivery.domain.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import setung.delivery.domain.BaseEntity;
import setung.delivery.controller.user.dto.UserDto;
import setung.delivery.utils.geo.LatLonData;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String tel;
    private String address;
    private String password;

    private Double lon;
    private Double lat;

    public User(UserDto user) {
        id = user.getId();
        name = user.getName();
        email = user.getEmail();
        tel = user.getTel();
        address = user.getAddress();
        password = user.getPassword();
    }

    public User updateUser(UserDto userDto) {
        this.name = userDto.getName();
        this.address = userDto.getAddress();
        this.password = userDto.getPassword();
        return this;
    }

    public User setLatLon(LatLonData latLon) {
        this.lon = latLon.getLon();
        this.lat = latLon.getLat();
        return this;
    }
}
