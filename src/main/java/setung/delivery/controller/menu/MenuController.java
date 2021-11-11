package setung.delivery.controller.menu;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import setung.delivery.argumentresolver.LoginOwnerId;
import setung.delivery.controller.menu.dto.MenuDto;
import setung.delivery.domain.menu.service.MenuService;

@RestController
@RequestMapping("/restaurant/{restaurantId}/menus")
@AllArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @PostMapping
    public MenuDto registerMenu(@LoginOwnerId long ownerId,
                                @PathVariable long restaurantId,
                                @RequestBody MenuDto menuDto) {
        return menuService.registerMenu(ownerId, restaurantId, menuDto).toMenuDto();
    }

    @DeleteMapping
    public void deleteAllMenu(@LoginOwnerId long ownerId, @PathVariable long restaurantId) {
        menuService.deleteAllMenu(ownerId, restaurantId);
    }

    @DeleteMapping("/{menuId}")
    public void deleteAllMenu(@LoginOwnerId long ownerId, @PathVariable long restaurantId, @PathVariable long menuId) {
        menuService.deleteMenu(ownerId, restaurantId, menuId);
    }

    @PutMapping("/{menuId}")
    public void updateMenu(@LoginOwnerId long ownerId, @PathVariable long restaurantId,
                           @PathVariable long menuId, @RequestBody MenuDto menuDto) {
        menuService.updateMenu(ownerId, restaurantId, menuId, menuDto);
    }
}
