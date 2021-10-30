package setung.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import setung.delivery.domain.menu.Menu;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

    Menu findByIdAndRestaurantId(long menuId, long restaurantId);
}
