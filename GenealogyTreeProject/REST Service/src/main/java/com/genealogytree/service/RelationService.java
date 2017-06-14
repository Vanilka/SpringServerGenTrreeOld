package genealogytree.service;

import genealogytree.ExceptionManager.exception.*;
import genealogytree.persist.entity.modules.tree.FamilyEntity;
import genealogytree.persist.entity.modules.tree.MemberEntity;
import genealogytree.persist.entity.modules.tree.RelationsEntity;

import java.util.List;

/**
 * Created by vanilka on 30/12/2016.
 */
public interface RelationService {

    List<RelationsEntity> getRelations(FamilyEntity family);

    RelationsEntity getRelation(Long id);

    RelationsEntity addRelation(RelationsEntity relation) throws IncorrectSex, NoValidMembers, TooManyNullFields, IncorrectStatus, IntegrationViolation;

    RelationsEntity getRelationBySimLeftAndSimRight(MemberEntity simLeft, MemberEntity simRight);

}
