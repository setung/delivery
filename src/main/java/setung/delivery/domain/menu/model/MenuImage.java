package setung.delivery.domain.menu.model;

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
public class MenuImage extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "menu_image_id")
    private Long id;

    @Column(unique = true, length = 50)
    private String fileId;
    private String fileName;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

}
