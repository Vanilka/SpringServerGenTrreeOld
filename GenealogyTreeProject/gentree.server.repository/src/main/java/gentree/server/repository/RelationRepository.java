package gentree.server.repository;

import gentree.server.domain.entity.RelationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Martyna SZYMKOWIAK on 18/10/2017.
 */
@Repository
public interface RelationRepository extends JpaRepository<RelationEntity, Long> {

    List<RelationEntity> findAllByFamilyId(Long id);
}
