package gentree.server.repository;

import gentree.server.domain.entity.MemberEntity;
import gentree.server.domain.entity.RelationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Martyna SZYMKOWIAK on 18/10/2017.
 */
@Repository
public interface RelationRepository extends JpaRepository<RelationEntity, Long> {

    List<RelationEntity> findAllByFamilyId(Long id);

    List<RelationEntity> findByLeftAndRight(MemberEntity left, MemberEntity right);

    @Query("select rel from RelationEntity rel where rel.left.id=:idLeft and rel.right.id=:idRight" )
    List<RelationEntity> findByLeftAndRight(@Param("idLeft") Long idLeft, @Param("idRight") Long idRight);


    @Query("select rel from RelationEntity rel  where  :child member of rel.children")
    List<RelationEntity> findBornRelatons(@Param("child") MemberEntity child);
}
