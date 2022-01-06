package setung.delivery.controller.rider.dto;

import lombok.*;
import setung.delivery.domain.rider.model.Rider;

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
    private Double deliveryRange;

    public RiderDto(Rider rider) {
        id = rider.getId();
        email = rider.getEmail();
        tel = rider.getTel();
        name = rider.getName();
        address = rider.getAddress();
        password = rider.getPassword();
        deliveryRange = rider.getDeliveryRange();
    }
}
