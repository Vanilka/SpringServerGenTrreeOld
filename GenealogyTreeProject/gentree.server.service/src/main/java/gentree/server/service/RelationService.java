package gentree.server.service;

import gentree.server.domain.entity.MemberEntity;
import gentree.server.domain.entity.RelationEntity;

import java.util.List;

/**
 * Created by Martyna SZYMKOWIAK on 18/10/2017.
 */
public interface RelationService {

    RelationEntity addNewRelation(RelationEntity relation);

    RelationEntity updateRelation(RelationEntity relation);

    RelationEntity addNewBornRelation(MemberEntity memberEntity);

    List<RelationEntity> findAllRelationsByFamilyId(Long id);

    RelationEntity deleteRelation(RelationEntity relation);

    RelationEntity findRelationBysimLeftAndsimRight(MemberEntity simLeft, MemberEntity simRight);

    void removeOrphans(Long familyID);


}
