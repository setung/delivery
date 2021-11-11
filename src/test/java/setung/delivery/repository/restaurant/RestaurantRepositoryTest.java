package setung.delivery.repository.restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import setung.delivery.domain.owner.model.Owner;
import setung.delivery.domain.restaurant.model.Restaurant;
import setung.delivery.domain.restaurant.model.RestaurantCategory;
import setung.delivery.domain.restaurant.repository.RestaurantRepository;
import setung.delivery.domain.owner.repository.OwnerRepository;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class RestaurantRepositoryTest {

    @Autowired
    RestaurantRepository restaurantRepository;
    @Autowired
    OwnerRepository ownerRepository;

    @Test
    public void findByIdAndOwnerID() {
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

        ownerRepository.save(owner);
        restaurantRepository.save(restaurant);

        //when
        Restaurant findRestaurant = restaurantRepository.findByIdAndOwnerId(restaurant.getId(), owner.getId());

        //then
        assertThat(findRestaurant).isNotNull();
        assertThat(findRestaurant.getOwner()).isEqualTo(owner);
        assertThat(findRestaurant).isEqualTo(restaurant);

    }
}