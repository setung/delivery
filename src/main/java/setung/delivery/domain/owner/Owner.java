package setung.delivery.domain.owner;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Owner {

    @Id
    @GeneratedValue
    @Column(name = "owner_id")
    private Long id;
    private String name;

    @Column(unique = true)
    private String email;
    private String address;
    private String password;

    @Column(unique = true)
    private String tel;

}
