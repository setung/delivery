package setung.delivery.domain.owner;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Builder
public class Owner {

    @Id
    @GeneratedValue
    @Column(name = "owner_id")
    private Long id;
    private String name;
    private String email;
    private String address;
    private String password;
    private String tel;

}
