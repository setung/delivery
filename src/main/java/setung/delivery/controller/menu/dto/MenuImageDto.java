package setung.delivery.controller.menu.dto;

import lombok.*;
import setung.delivery.domain.menu.model.MenuImage;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuImageDto {

    private Long id;
    private String fileName;

    public MenuImageDto(MenuImage menuImage) {
        id = menuImage.getId();
        fileName = menuImage.getFileName();
    }
}
