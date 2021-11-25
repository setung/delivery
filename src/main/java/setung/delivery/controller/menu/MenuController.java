package setung.delivery.controller.menu;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import setung.delivery.argumentresolver.LoginOwnerId;
import setung.delivery.controller.menu.dto.MenuDto;
import setung.delivery.domain.menu.service.MenuService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/restaurant/{restaurantId}/menus")
@AllArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @PostMapping
    public MenuDto registerMenu(@LoginOwnerId long ownerId,
                                @PathVariable long restaurantId,
                                @RequestBody MenuDto menuDto) {
        return new MenuDto(menuService.registerMenu(ownerId, restaurantId, menuDto));
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

    @GetMapping
    public List<MenuDto> getMenus(@PathVariable long restaurantId) {
        return menuService.findAllByRestaurantId(restaurantId).stream().map(menu -> new MenuDto(menu))
                .collect(Collectors.toList());
    }
}
