package setung.delivery.domain.menu.service;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import setung.delivery.controller.menu.dto.MenuDto;
import setung.delivery.controller.restaurant.dto.RestaurantDto;
import setung.delivery.domain.menu.model.Menu;
import setung.delivery.domain.menu.model.MenuImage;
import setung.delivery.domain.menu.repository.MenuImageRepository;
import setung.delivery.domain.menu.repository.MenuRepository;
import setung.delivery.domain.restaurant.model.Restaurant;
import setung.delivery.domain.restaurant.repository.RestaurantRepository;
import setung.delivery.exception.CustomException;
import setung.delivery.exception.ErrorCode;
import setung.delivery.utils.firebase.FirebaseStorageUtil;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class MenuService {

    private final MenuRepository menuRepository;
    private final RestaurantRepository restaurantRepository;
    private final FirebaseStorageUtil firebaseStorageUtil;
    private final MenuImageRepository menuImageRepository;

    public Menu registerMenu(long ownerId, long restaurantId, MenuDto menuDto) {
        Restaurant restaurant = restaurantRepository.findByIdAndOwnerId(restaurantId, ownerId);

        if (restaurant == null)
            throw new CustomException(ErrorCode.NOT_FOUND_RESTAURANT);

        menuDto.setRestaurant(new RestaurantDto(restaurant));

        return menuRepository.save(new Menu(menuDto));
    }

    public Menu findByIdAndRestaurantId(long menuId, long restaurantId) {
        Menu menu = menuRepository.findByIdAndRestaurantId(menuId, restaurantId);

        if (menu == null)
            throw new CustomException(ErrorCode.NOT_FOUND_MENU);

        return menu;
    }

    public void deleteAllMenu(long ownerId, long restaurantId) {
        Restaurant restaurant = restaurantRepository.findByIdAndOwnerId(restaurantId, ownerId);

        if (restaurant == null)
            throw new CustomException(ErrorCode.NOT_FOUND_RESTAURANT);

        menuRepository.deleteByRestaurantId(restaurantId);
    }

    public void deleteMenu(long ownerId, long restaurantId, long menuId) {
        Restaurant restaurant = restaurantRepository.findByIdAndOwnerId(restaurantId, ownerId);

        if (restaurant == null)
            throw new CustomException(ErrorCode.NOT_FOUND_RESTAURANT);

        menuRepository.deleteById(menuId);
    }

    @Cacheable(key = "#restaurantId", value = "findAllByRestaurantId")
    public List<Menu> findAllByRestaurantId(long restaurantId) {
        return menuRepository.findAllByRestaurantId(restaurantId);
    }

    public void updateMenu(long ownerId, long restaurantId, long menuId, MenuDto updatedMenu) {
        Restaurant restaurant = restaurantRepository.findByIdAndOwnerId(restaurantId, ownerId);
        Menu menu = menuRepository.findByIdAndRestaurantId(menuId, restaurantId);

        if (restaurant == null) throw new CustomException(ErrorCode.NOT_FOUND_RESTAURANT);
        if (menu == null) throw new CustomException(ErrorCode.NOT_FOUND_MENU);

        menu.updateMenu(updatedMenu);
    }

    public void registerMenuImages(long ownerId, long restaurantId, long menuId, List<MultipartFile> multipartFiles) {
        if (multipartFiles.isEmpty() || multipartFiles == null)
            throw new CustomException(ErrorCode.BAD_REQUEST_STORAGE);

        Restaurant restaurant = restaurantRepository.findByIdAndOwnerId(restaurantId, ownerId);
        Menu menu = menuRepository.findByIdAndRestaurantId(menuId, restaurantId);

        if (restaurant == null) throw new CustomException(ErrorCode.NOT_FOUND_RESTAURANT);
        if (menu == null) throw new CustomException(ErrorCode.NOT_FOUND_MENU);

        List<FirebaseStorageUtil.SavedFile> savedFiles = firebaseStorageUtil.saveFile(multipartFiles);

        for (FirebaseStorageUtil.SavedFile savedFile : savedFiles) {
            MenuImage menuImage = MenuImage.builder()
                    .id(savedFile.getId())
                    .fileName(savedFile.getFileName())
                    .menu(menu)
                    .build();

            menuImageRepository.save(menuImage);
        }
    }

    public List<MenuImage> findMenuImagesByMenu(long restaurantId, long menuId) {
        Menu menu = findByIdAndRestaurantId(menuId, restaurantId);
        List<MenuImage> menuImages = menuImageRepository.findByMenuId(menu.getId());
        return menuImages;
    }

    public Menu findMenuById(long menuId) {
        return menuRepository.findById(menuId).orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_MENU));
    }

    public void deleteMenuImage(long ownerId, long restaurantId, long menuId, String menuImageId) {
        Restaurant restaurant = restaurantRepository.findByIdAndOwnerId(restaurantId, ownerId);
        Menu menu = menuRepository.findByIdAndRestaurantId(menuId, restaurantId);

        firebaseStorageUtil.delete(menuImageId);
        menuImageRepository.deleteById(menuImageId);
    }
}
