package gentree.server.repository;

import gentree.server.domain.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Martyna SZYMKOWIAK on 18/10/2017.
 */
@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

    MemberEntity findMemberById(Long id);
}
