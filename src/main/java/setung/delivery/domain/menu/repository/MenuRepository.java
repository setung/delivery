package setung.delivery.domain.menu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import setung.delivery.domain.menu.model.Menu;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

    Menu findByIdAndRestaurantId(long menuId, long restaurantId);

    void deleteByRestaurantId(long restaurantId);

    List<Menu> findAllByRestaurantId(long restaurantId);
}
