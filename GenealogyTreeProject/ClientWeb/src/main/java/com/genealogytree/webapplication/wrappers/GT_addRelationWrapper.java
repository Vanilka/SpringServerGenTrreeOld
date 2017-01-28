package com.genealogytree.webapplication.wrappers;

import com.genealogytree.repository.entity.modules.tree.GT_Relations;

import java.util.List;

/**
 * Created by vanilka on 05/01/2017.
 */
public class GT_addRelationWrapper {

    private GT_Relations relation;
    private List<GT_Relations> toUpdateRelations;
    private List<GT_Relations> toDeleteRelations;

    GT_addRelationWrapper() {
        this(null, null, null);
    }

    GT_addRelationWrapper(GT_Relations relation,
                          List<GT_Relations> toUpdateReliations,
                          List<GT_Relations> toDeleteRelations) {
        this.relation = relation;
        this.toUpdateRelations = toUpdateReliations;
        this.toDeleteRelations = toDeleteRelations;
    }


    public void  addToUpdateRelation(GT_Relations relation) {
        this.toUpdateRelations.add(relation);
    }

    public void  addToDeleteRelation(GT_Relations relation) {
        this.toDeleteRelations.add(relation);
    }



    /*
     *      GETTERS
     */

    public GT_Relations getRelation() {
        return relation;
    }

    public List<GT_Relations> getToUpdateRelations() {
        return toUpdateRelations;
    }

    public List<GT_Relations> getToDeleteRelations() {
        return toDeleteRelations;
    }

    /*
     *      SETTERS
     */

    public void setRelation(GT_Relations relation) {
        this.relation = relation;
    }

    public void setToUpdateRelations(List<GT_Relations> toUpdateRelations) {
        this.toUpdateRelations = toUpdateRelations;
    }

    public void setToDeleteRelations(List<GT_Relations> toDeleteRelations) {
        this.toDeleteRelations = toDeleteRelations;
    }
}
