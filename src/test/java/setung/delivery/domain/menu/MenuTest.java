package setung.delivery.domain.menu;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import setung.delivery.domain.restaurant.Restaurant;
import setung.delivery.domain.restaurant.RestaurantCategory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MenuTest {

    @PersistenceContext
    EntityManager em;

    @Test
    public void menuTest() {
        Restaurant restaurant = Restaurant.builder()
                .name("한식당")
                .address("서울시 강남구 어딘가")
                .category(RestaurantCategory.KOREAN)
                .openAt(LocalTime.of(9, 0))
                .closeAt(LocalTime.of(22, 0))
                .tel("00-000-0000")
                .build();
        em.persist(restaurant);

        Menu menu = Menu.builder()
                .name("된장찌개")
                .price(1000)
                .quantity(5)
                .category(MenuCategory.MAIN)
                .restaurant(restaurant)
                .build();
        em.persist(menu);

        Menu menu2 = Menu.builder()
                .name("김치찌개")
                .price(2000)
                .quantity(15)
                .category(MenuCategory.MAIN)
                .restaurant(restaurant)
                .build();
        em.persist(menu2);

        em.flush();
        em.clear();

        Restaurant findRestaurant = em.find(Restaurant.class, restaurant.getId());
        Menu findMenu = em.find(Menu.class, menu.getId());
        Menu findMenu2 = em.find(Menu.class, menu2.getId());

        assertThat(findRestaurant.getMenus()).contains(findMenu,findMenu2);
        assertThat(menu.getId()).isEqualTo(findMenu.getId());
        assertThat(menu2.getId()).isEqualTo(findMenu2.getId());

    }

}