package setung.delivery.service.Menu;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import setung.delivery.domain.menu.model.Menu;
import setung.delivery.controller.menu.dto.MenuDto;
import setung.delivery.domain.menu.service.MenuService;
import setung.delivery.domain.owner.model.Owner;
import setung.delivery.domain.restaurant.model.Restaurant;
import setung.delivery.controller.restaurant.dto.RestaurantDto;
import setung.delivery.domain.user.model.User;
import setung.delivery.domain.owner.repository.OwnerRepository;
import setung.delivery.domain.user.repository.UserRepository;
import setung.delivery.domain.restaurant.service.RestaurantService;

import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class MenuServiceTest {

    @Autowired
    OwnerRepository ownerRepository;
    @Autowired
    RestaurantService restaurantService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    MenuService menuService;

    User user;
    Owner owner;
    Restaurant restaurant;
    Menu menu1;
    Menu menu2;
    Menu menu3;

    @BeforeEach
    public void beforeEach() {
        user = User.builder().build();
        owner = Owner.builder().build();
        RestaurantDto restaurantDto = RestaurantDto.builder()
                .address("서울 강남구 강남대로94길 27").build();
        MenuDto menuDto1 = MenuDto.builder().quantity(1).name("menu1").build();
        MenuDto menuDto2 = MenuDto.builder().quantity(2).name("menu2").build();
        MenuDto menuDto3 = MenuDto.builder().quantity(3).name("menu3").build();

        userRepository.save(user);
        ownerRepository.save(owner);
        restaurant = restaurantService.register(owner.getId(), restaurantDto);
        menu1 = menuService.registerMenu(owner.getId(), restaurant.getId(), menuDto1);
        menu2 = menuService.registerMenu(owner.getId(), restaurant.getId(), menuDto2);
        menu3 = menuService.registerMenu(owner.getId(), restaurant.getId(), menuDto3);
    }

    @Test
    public void deleteMenu() {
        List<Menu> beforeMenus = menuService.findAllByRestaurantId(restaurant.getId());

        assertThat(beforeMenus.size()).isEqualTo(3);

        menuService.deleteMenu(owner.getId(), restaurant.getId(), menu1.getId());

        List<Menu> afterMenus = menuService.findAllByRestaurantId(restaurant.getId());

        assertThat(afterMenus.size()).isEqualTo(2);
    }

    @Test
    public void deleteMenuById() {
        menuService.deleteMenu(owner.getId(), restaurant.getId(), menu1.getId());

        List<Menu> menus = menuService.findAllByRestaurantId(restaurant.getId());

        assertThat(menus.size()).isEqualTo(2);
        assertThat(menus.get(0).getName()).isEqualTo("menu2");
        assertThat(menus.get(1).getName()).isEqualTo("menu3");
    }

    @Test
    public void updatedMenu() {
        MenuDto updatedMenu = MenuDto.builder().name("updatedMenu1").build();
        menuService.updateMenu(owner.getId(), restaurant.getId(), menu1.getId(), updatedMenu);

        Menu findMenu = menuService.findByIdAndRestaurantId(menu1.getId(), restaurant.getId());

        Assertions.assertThat(findMenu.getName()).isEqualTo(updatedMenu.getName());
    }

}
