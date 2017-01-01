package com.genealogytree.service;

import com.genealogytree.ExceptionManager.exception.IncorrectSex;
import com.genealogytree.ExceptionManager.exception.TooManyNullFields;
import com.genealogytree.repository.entity.modules.tree.GT_Family;
import com.genealogytree.repository.entity.modules.tree.GT_Member;
import com.genealogytree.repository.entity.modules.tree.GT_Relations;

import java.util.List;

/**
 * Created by vanilka on 30/12/2016.
 */
public interface RelationService {

    List<GT_Relations> getRelations(GT_Family family);

    GT_Relations getRelation(Long id);

    GT_Relations addRelation(GT_Relations relation) throws TooManyNullFields, IncorrectSex;

    GT_Relations getRelationBySimLeftAndSimRight(GT_Member simLeft, GT_Member simRight);

}
