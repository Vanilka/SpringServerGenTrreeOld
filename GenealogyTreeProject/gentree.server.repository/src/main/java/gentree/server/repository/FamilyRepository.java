package gentree.server.repository;

import gentree.server.domain.entity.FamilyEntity;
import gentree.server.domain.entity.OwnerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Martyna SZYMKOWIAK on 18/10/2017.
 */
@Repository
public interface FamilyRepository extends JpaRepository<FamilyEntity, Long> {

    FamilyEntity findFamilyById(Long id);

    List<FamilyEntity> findAllByOwner(OwnerEntity owner);
}
