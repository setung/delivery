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
    @Column(name = "menu_image_id")
    private String id;

    private String fileName;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

}
