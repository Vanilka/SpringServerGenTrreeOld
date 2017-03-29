package com.genealogytree.domain.wrappers;

import com.genealogytree.domain.dto.RelationDTO;

import java.util.List;

/**
 * Created by vanilka on 05/01/2017.
 */
public class GT_addRelationWrapper {

    private RelationDTO relation;
    private List<RelationDTO> toUpdateRelations;
    private List<RelationDTO> toDeleteRelations;

    GT_addRelationWrapper() {
        this(null, null, null);
    }

    GT_addRelationWrapper(RelationDTO relation,
                          List<RelationDTO> toUpdateReliations,
                          List<RelationDTO> toDeleteRelations) {
        this.relation = relation;
        this.toUpdateRelations = toUpdateReliations;
        this.toDeleteRelations = toDeleteRelations;
    }


    public void addToUpdateRelation(RelationDTO relation) {
        this.toUpdateRelations.add(relation);
    }

    public void addToDeleteRelation(RelationDTO relation) {
        this.toDeleteRelations.add(relation);
    }



    /*
     *      GETTERS
     */

    public RelationDTO getRelation() {
        return relation;
    }

    public void setRelation(RelationDTO relation) {
        this.relation = relation;
    }

    public List<RelationDTO> getToUpdateRelations() {
        return toUpdateRelations;
    }

    /*
     *      SETTERS
     */

    public void setToUpdateRelations(List<RelationDTO> toUpdateRelations) {
        this.toUpdateRelations = toUpdateRelations;
    }

    public List<RelationDTO> getToDeleteRelations() {
        return toDeleteRelations;
    }

    public void setToDeleteRelations(List<RelationDTO> toDeleteRelations) {
        this.toDeleteRelations = toDeleteRelations;
    }
}
