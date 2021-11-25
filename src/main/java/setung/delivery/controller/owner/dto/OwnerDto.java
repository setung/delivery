package setung.delivery.controller.owner.dto;

import lombok.*;
import setung.delivery.domain.owner.model.Owner;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OwnerDto {

    private Long id;
    private String name;
    private String email;
    private String address;
    private String password;
    private String tel;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public OwnerDto(Owner owner) {
        id = owner.getId();
        name = owner.getName();
        email = owner.getEmail();
        address = owner.getAddress();
        password = owner.getPassword();
        tel = owner.getTel();
        createdAt = owner.getCreatedAt();
        updatedAt = owner.getUpdatedAt();
    }
}