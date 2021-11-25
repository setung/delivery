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

    public UserDto(User user) {
        id = user.getId();
        name = user.getName();
        email = user.getEmail();
        tel = user.getTel();
        address = user.getAddress();
        password = user.getPassword();
        createdAt = user.getCreatedAt();
        updatedAt = user.getUpdatedAt();
    }

}
