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

    public Owner toOwner() {
        Owner owner = Owner.builder()
                .name(this.name)
                .email(this.email)
                .address(this.address)
                .password(this.password)
                .tel(this.tel)
                .build();
        return owner;
    }
}
