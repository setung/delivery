package setung.delivery.domain.owner.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import setung.delivery.domain.BaseEntity;
import setung.delivery.controller.owner.dto.OwnerDto;
import setung.delivery.domain.restaurant.model.Restaurant;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Owner extends BaseEntity {

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
    @JsonIgnore
    List<Restaurant> restaurants;

    public Owner(OwnerDto owner) {
        id = owner.getId();
        name = owner.getName();
        email = owner.getEmail();
        address = owner.getAddress();
        password = owner.getPassword();
        tel = owner.getTel();
    }

    public Owner updateOwner(OwnerDto ownerDto) {
        name = ownerDto.getName();
        email = ownerDto.getEmail();
        address = ownerDto.getAddress();
        password = ownerDto.getPassword();
        tel = ownerDto.getTel();
        return this;
    }
}
