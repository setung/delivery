package setung.delivery.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import setung.delivery.argumentresolver.LoginOwnerId;
import setung.delivery.domain.menu.Menu;
import setung.delivery.domain.menu.MenuDto;
import setung.delivery.service.Menu.MenuService;

@RestController
@RequestMapping("/restaurant/{restaurantId}/menus")
@AllArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @PostMapping
    public Menu registerMenu(@LoginOwnerId long ownerId,
                             @PathVariable long restaurantId,
                             @RequestBody MenuDto menuDto) {
        return menuService.registerMenu(ownerId, restaurantId, menuDto);
    }

}
