package setung.delivery.domain.owner;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import setung.delivery.domain.BaseEntity;
import setung.delivery.domain.restaurant.Restaurant;

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

    public OwnerDto toOwnerDto() {
        return OwnerDto.builder()
                .id(id)
                .name(name)
                .email(email)
                .address(address)
                .password(password)
                .tel(tel)
                .createdAt(getCreatedAt())
                .updatedAt(getUpdatedAt())
                .build();
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
