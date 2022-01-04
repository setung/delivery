package setung.delivery.domain.menu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import setung.delivery.domain.menu.model.MenuImage;

import java.util.List;

@Repository
public interface MenuImageRepository extends JpaRepository<MenuImage, String> {
    List<MenuImage> findByMenuId(long menuId);

    void deleteByFileId(String fileId);
}
