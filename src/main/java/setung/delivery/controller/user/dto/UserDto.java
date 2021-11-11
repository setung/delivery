package setung.delivery.controller.user.dto;

import lombok.*;
import setung.delivery.domain.user.model.User;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;
    private String name;
    private String email;
    private String tel;
    private String address;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public User toUser() {
        User user = User.builder()
                .name(this.name)
                .email(this.email)
                .tel(this.tel)
                .address(this.address)
                .password(this.password)
                .build();

        return user;
    }

}
