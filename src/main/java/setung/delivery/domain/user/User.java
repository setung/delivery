package setung.delivery.domain.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import setung.delivery.domain.BaseEntity;

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

    public UserDto toUserDto() {
        return UserDto.builder()
                .id(id)
                .name(name)
                .email(email)
                .tel(tel)
                .address(address)
                .password(password)
                .createdAt(getCreatedAt())
                .updatedAt(getUpdatedAt())
                .build();
    }

    public User updateUser(UserDto userDto) {
        this.name = userDto.getName();
        this.address = userDto.getAddress();
        this.password = userDto.getPassword();
        return this;
    }
}
