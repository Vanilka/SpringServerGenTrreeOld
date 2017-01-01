package com.genealogytree.service.implementation;

import com.genealogytree.ExceptionManager.exception.IncorrectSex;
import com.genealogytree.ExceptionManager.exception.TooManyNullFields;
import com.genealogytree.domain.enums.Sex;
import com.genealogytree.repository.RelationRepository;
import com.genealogytree.repository.entity.modules.tree.GT_Family;
import com.genealogytree.repository.entity.modules.tree.GT_Member;
import com.genealogytree.repository.entity.modules.tree.GT_Relations;
import com.genealogytree.service.RelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by vanilka on 30/12/2016.
 */
@Service
@Transactional
@ComponentScan("com.genealogytree")
public class RelationServiceImpl implements RelationService {

    @Autowired
    RelationRepository repository;


    @Override
    public List<GT_Relations> getRelations(GT_Family family) {
        return this.repository.findAllRelationsByFamily(family);
    }

    @Override
    public GT_Relations getRelation(Long id) {
        return null;
    }

    @Override
    public GT_Relations addRelation(GT_Relations relation) throws TooManyNullFields, IncorrectSex {
        if (!checkNullFields(relation)) {
            throw new TooManyNullFields();
        }
        if (!checkSex(relation)) {
            throw new IncorrectSex();
        }
        GT_Relations existing = getRelationBySimLeftAndSimRight(relation.getSimLeft(), relation.getSimRight());
        if(existing != null) {
            existing.setActive(relation.geActive());
            existing.setRelationType(relation.getRelationType());
            relation.getChildren().forEach(existing::addChild);
            return  existing;
        } else {
            return this.repository.save(relation);
        }
    }


    @Override
    public GT_Relations getRelationBySimLeftAndSimRight(GT_Member simLeft, GT_Member simRight) {
        return this.repository.findRelationBySimLeftAndSimRight(simLeft, simRight);
    }

    private boolean checkNullFields(GT_Relations relation) {

        if ((relation.getchildren() == null || relation.getchildren().size() == 0) && relation.getSimLeft() == null) {
            return false;
        } else if ((relation.getchildren() == null || relation.getchildren().size() == 0 ) && relation.getSimRight() == null) {
            return false;
        } else {
            return true;
        }
    }

    private boolean checkSex(GT_Relations relation) {
        if (relation.getSimLeft() != null && relation.getSimRight() != null
                && relation.getSimLeft().getSex() != relation.getSimRight().getSex()
                && (relation.getSimRight().getSex() == Sex.FEMALE
                || relation.getSimLeft().getSex() == Sex.MALE)) {
            return false;
        } else if (relation.getSimLeft() == null && relation.getSimRight().getSex() == Sex.FEMALE) {
            return false;
        } else if (relation.getSimRight() == null && relation.getSimLeft().getSex() == Sex.MALE) {
            return false;
        } else {
            return true;
        }
    }
}
