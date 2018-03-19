package gentree.server.service;

import gentree.exception.NotExistingRelationException;
import gentree.server.domain.entity.MemberEntity;
import gentree.server.domain.entity.RelationEntity;

import java.util.List;

/**
 * Created by Martyna SZYMKOWIAK on 18/10/2017.
 */
public interface RelationService {

    RelationEntity addNewRelation(RelationEntity relation);

    RelationEntity updateRelation(RelationEntity relation) throws NotExistingRelationException;

    RelationEntity addNewBornRelation(MemberEntity memberEntity);

    List<RelationEntity> findAllRelationsByFamilyId(Long id);

    RelationEntity deleteRelation(RelationEntity relation);

    RelationEntity findRelationBySimLeftAndSimRight(MemberEntity simLeft, MemberEntity simRight);

    RelationEntity findRelationById(Long id);

    void removeOrphans(Long familyID);


    List<RelationEntity> findBornRelations(MemberEntity child);

    void saveandflush();

    void forcedeleteRelation(RelationEntity relation);
}
