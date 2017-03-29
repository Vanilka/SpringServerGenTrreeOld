package com.genealogytree.service;

import com.genealogytree.ExceptionManager.exception.*;
import com.genealogytree.persist.entity.modules.tree.FamilyEntity;
import com.genealogytree.persist.entity.modules.tree.MemberEntity;
import com.genealogytree.persist.entity.modules.tree.RelationsEntity;

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
