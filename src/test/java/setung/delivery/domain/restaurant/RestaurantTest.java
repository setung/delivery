package setung.delivery.domain.restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import setung.delivery.domain.owner.model.Owner;
import setung.delivery.domain.restaurant.model.Restaurant;
import setung.delivery.domain.restaurant.model.RestaurantCategory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class RestaurantTest {

    @PersistenceContext
    EntityManager em;

    @Test
    public void saveRestaurant() {
        //given
        Owner owner = Owner.builder()
                .name("jsh")
                .email("jsh@test.com")
                .password("1234")
                .tel("000-0000-0000")
                .build();

        Restaurant restaurant = Restaurant.builder()
                .name("한식당")
                .address("서울시 강남구 어딘가")
                .category(RestaurantCategory.KOREAN)
                .openAt(LocalTime.of(9, 0))
                .closeAt(LocalTime.of(22, 0))
                .tel("00-000-0000")
                .owner(owner)
                .build();

        Restaurant restaurant2 = Restaurant.builder()
                .name("중식당")
                .address("서울시 강남구 어딘가")
                .category(RestaurantCategory.CHINESE)
                .openAt(LocalTime.of(9, 0))
                .closeAt(LocalTime.of(22, 0))
                .tel("00-000-0000")
                .owner(owner)
                .build();

        //when
        em.persist(owner);
        em.persist(restaurant);
        em.persist(restaurant2);

        em.flush();
        em.clear();

        Restaurant findRestaurant = em.find(Restaurant.class, restaurant.getId());
        Owner findOwner = em.find(Owner.class, owner.getId());
        List<Restaurant> restaurants = findOwner.getRestaurants();
        //then
        assertThat(findRestaurant).isNotNull();
        assertThat(findRestaurant.getName()).isEqualTo(restaurant.getName());
        assertThat(restaurants.size()).isEqualTo(2);
        assertThat(restaurants.get(0).getName()).isEqualTo(findRestaurant.getName());

    }
}