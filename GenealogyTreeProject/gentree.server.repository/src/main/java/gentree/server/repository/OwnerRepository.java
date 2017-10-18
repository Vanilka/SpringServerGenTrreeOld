package gentree.server.repository;

import gentree.server.domain.entity.OwnerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Martyna SZYMKOWIAK on 16/10/2017.
 */
@Repository
public interface OwnerRepository extends JpaRepository<OwnerEntity, Long> {

    OwnerEntity findByLogin(String login);
}
