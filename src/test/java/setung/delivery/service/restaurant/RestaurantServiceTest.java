package setung.delivery.service.restaurant;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import setung.delivery.domain.menu.model.Menu;
import setung.delivery.controller.menu.dto.MenuDto;
import setung.delivery.domain.owner.model.Owner;
import setung.delivery.domain.restaurant.model.Restaurant;
import setung.delivery.controller.restaurant.dto.RestaurantDto;
import setung.delivery.controller.restaurant.dto.UpdatedRestaurantDto;
import setung.delivery.domain.restaurant.service.RestaurantService;
import setung.delivery.domain.user.model.User;
import setung.delivery.domain.owner.repository.OwnerRepository;
import setung.delivery.domain.user.repository.UserRepository;
import setung.delivery.domain.menu.service.MenuService;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class RestaurantServiceTest {

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
        RestaurantDto restaurantDto = RestaurantDto.builder().build();
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
    public void deleteRestaurant() {
        Restaurant beforeRestaurant = restaurantService.findRestaurantById(restaurant.getId());
        assertThat(beforeRestaurant).isNotNull();

        restaurantService.deleteRestaurant(owner.getId(), restaurant.getId());

        List<Menu> menus = menuService.findAllByRestaurantId(restaurant.getId());
        Restaurant afterRestaurant = restaurantService.findRestaurantById(restaurant.getId());

        assertThat(afterRestaurant).isNull();
        assertThat(menus.size()).isEqualTo(0);
    }

    @Test
    public void updateRestaurant() {
        Restaurant restaurant = Restaurant.builder()
                .name("한식집")
                .tel("000-0000-0000")
                .build();

        UpdatedRestaurantDto updatedRestaurantDto = UpdatedRestaurantDto.builder()
                .name("일식집")
                .tel("111-1111-1111")
                .build();

        restaurant.update(updatedRestaurantDto);

        Assertions.assertThat(restaurant.getName()).isEqualTo(updatedRestaurantDto.getName());
        Assertions.assertThat(restaurant.getTel()).isEqualTo(updatedRestaurantDto.getTel());
    }
}
