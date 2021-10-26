package setung.delivery.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import setung.delivery.domain.restaurant.Restaurant;
import setung.delivery.domain.restaurant.RestaurantCategory;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    Restaurant findByIdAndOwnerId(long restaurantId, long ownerId);

    Page<Restaurant> findByCategory(RestaurantCategory category, Pageable pageable);
}
