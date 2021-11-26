package setung.delivery.controller.restaurant.specification;

import org.springframework.data.jpa.domain.Specification;
import setung.delivery.domain.restaurant.model.Restaurant;
import setung.delivery.domain.restaurant.model.RestaurantCategory;

public class RestaurantSpecification {

    public static Specification<Restaurant> equalCategory(RestaurantCategory category) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("category"), category);
    }
}
