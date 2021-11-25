package setung.delivery.service.Menu;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import setung.delivery.domain.menu.model.Menu;
import setung.delivery.domain.menu.model.MenuCategory;
import setung.delivery.controller.menu.dto.MenuDto;
import setung.delivery.domain.menu.service.MenuService;
import setung.delivery.domain.restaurant.model.Restaurant;
import setung.delivery.exception.CustomException;
import setung.delivery.domain.menu.repository.MenuRepository;
import setung.delivery.domain.restaurant.repository.RestaurantRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class MockMenuServiceTest {

    @Mock
    MenuRepository menuRepository;

    @Mock
    RestaurantRepository restaurantRepository;

    @InjectMocks
    MenuService menuService;

    @Test
    @DisplayName("정상적인 메뉴 등록")
    public void registerMenu() {
        //given
        Restaurant restaurant = Restaurant.builder().build();

        MenuDto menuDto = MenuDto.builder()
                .name("된장찌개")
                .price(1000)
                .quantity(5)
                .category(MenuCategory.MAIN)
                .build();

        //when
        when(restaurantRepository.findByIdAndOwnerId(any(Long.class),any(Long.class))).thenReturn(restaurant);
        when(menuRepository.save(any())).thenReturn(new Menu(menuDto));

        Menu savedMenu = menuService.registerMenu(any(Long.class), any(Long.class), menuDto);

        //then
        assertThat(savedMenu.getName()).isEqualTo(menuDto.getName());
        verify(restaurantRepository, times(1)).findByIdAndOwnerId(any(Long.class),any(Long.class));
        verify(menuRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("OwnerId와 RestaurantId가 잘 못 입력시 예외 발생")
    public void registerMenuWithWrongOwnerOrRestaurant() {
        //given
        MenuDto menuDto = MenuDto.builder()
                .name("된장찌개")
                .price(1000)
                .quantity(5)
                .category(MenuCategory.MAIN)
                .build();

        //when
        when(restaurantRepository.findByIdAndOwnerId(any(Long.class),any(Long.class))).thenReturn(null);
        when(menuRepository.save(any())).thenReturn(new Menu(menuDto));

        //then
        assertThrows(CustomException.class,()->{
            menuService.registerMenu(any(Long.class), any(Long.class), menuDto);
        });
    }
}