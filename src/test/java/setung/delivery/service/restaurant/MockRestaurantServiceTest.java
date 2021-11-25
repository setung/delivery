package setung.delivery.service.restaurant;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import setung.delivery.controller.owner.dto.OwnerDto;
import setung.delivery.domain.owner.model.Owner;
import setung.delivery.domain.restaurant.model.Restaurant;
import setung.delivery.domain.restaurant.model.RestaurantCategory;
import setung.delivery.controller.restaurant.dto.RestaurantDto;
import setung.delivery.domain.restaurant.service.RestaurantService;
import setung.delivery.exception.CustomException;
import setung.delivery.domain.owner.repository.OwnerRepository;
import setung.delivery.domain.restaurant.repository.RestaurantRepository;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class MockRestaurantServiceTest {

    @Mock
    RestaurantRepository restaurantRepository;
    @Mock
    OwnerRepository ownerRepository;

    @InjectMocks
    RestaurantService restaurantService;

    @Test
    @DisplayName("정상적인 restaurant 등록")
    public void registerRestaurant() {
        //given
        Owner owner = Owner.builder()
                .name("jsh")
                .email("jsh@test.com")
                .password("1234")
                .tel("000-0000-0000")
                .id(1L)
                .build();

        RestaurantDto restaurantDto = RestaurantDto.builder()
                .name("한식당")
                .address("서울시 강남구 어딘가")
                .category(RestaurantCategory.KOREAN)
                .openAt(LocalTime.of(9, 0))
                .closeAt(LocalTime.of(22, 0))
                .owner(new OwnerDto(owner))
                .tel("00-000-0000")
                .build();

        Restaurant restaurant = new Restaurant(restaurantDto);

        when(ownerRepository.findById(any())).thenReturn(java.util.Optional.ofNullable(owner));
        when(restaurantRepository.save(any())).thenReturn(restaurant);

        //when
        Restaurant registerRestaurant = restaurantService.register(owner.getId(), restaurantDto);

        //then
        Assertions.assertThat(registerRestaurant.getName()).isEqualTo(restaurantDto.getName());
        verify(restaurantRepository, times(1)).save(any(Restaurant.class));
        verify(ownerRepository, times(1)).findById(any(Long.class));
    }

    @Test
    @DisplayName("owner가 로그인 되어 있지 않을 때 식당 등록시 예외 발생")
    public void registerRestaurantWithWrongOwner() {
        //given
        RestaurantDto restaurantDto = RestaurantDto.builder()
                .name("한식당")
                .address("서울시 강남구 어딘가")
                .category(RestaurantCategory.KOREAN)
                .openAt(LocalTime.of(9, 0))
                .closeAt(LocalTime.of(22, 0))
                .tel("00-000-0000")
                .build();

        when(ownerRepository.findById(any())).thenThrow(CustomException.class);

        assertThrows(CustomException.class, () -> restaurantService.register(1L, restaurantDto));
    }
}