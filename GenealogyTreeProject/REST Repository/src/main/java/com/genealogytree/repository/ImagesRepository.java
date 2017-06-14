package genealogytree.repository;

import genealogytree.persist.entity.modules.tree.ImagesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by vanilka on 27/12/2016.
 */
@Repository
public interface ImagesRepository extends JpaRepository<ImagesEntity, Long> {


}
