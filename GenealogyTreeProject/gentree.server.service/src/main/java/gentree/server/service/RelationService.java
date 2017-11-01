package gentree.server.service;

import gentree.server.domain.entity.MemberEntity;
import gentree.server.domain.entity.RelationEntity;

import java.util.List;
import java.util.Set;

/**
 * Created by Martyna SZYMKOWIAK on 18/10/2017.
 */
public interface RelationService {

    RelationEntity addNewRelation(RelationEntity relation);

    RelationEntity addNewBornRelation(MemberEntity memberEntity);

    List<RelationEntity> findAllRelationsByFamilyId(Long id);

    RelationEntity deleteRelation(RelationEntity relation);

    void removeOrphans(Long familyID);


}
