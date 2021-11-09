package setung.delivery.domain.rider;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RiderDto {

    private Long id;
    private String email;
    private String tel;
    private String name;
    private String address;
    private String password;

    public Rider toRider() {
        return Rider.builder()
                .id(id)
                .email(email)
                .tel(tel)
                .name(name)
                .address(address)
                .password(password)
                .build();
    }
}
