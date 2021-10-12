package setung.delivery.domain.user;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Builder
public class User {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;
    private String name;
    private String email;
    private String tel;
    private String address;

}
