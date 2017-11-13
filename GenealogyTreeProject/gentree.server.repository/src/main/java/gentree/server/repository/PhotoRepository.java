package gentree.server.repository;

import gentree.server.domain.entity.PhotoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Martyna SZYMKOWIAK on 06/11/2017.
 */
@Repository
public interface PhotoRepository extends JpaRepository<PhotoEntity, Long> {
}
