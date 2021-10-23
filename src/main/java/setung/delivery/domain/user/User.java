package setung.delivery.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

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

    public User updateUser(UserDto userDto) {
        this.name = userDto.getName();
        this.address = userDto.getAddress();
        this.password = userDto.getPassword();
        return this;
    }
}
