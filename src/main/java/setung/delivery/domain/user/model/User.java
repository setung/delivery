package setung.delivery.domain.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import setung.delivery.domain.BaseEntity;
import setung.delivery.controller.user.dto.UserDto;

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
}
