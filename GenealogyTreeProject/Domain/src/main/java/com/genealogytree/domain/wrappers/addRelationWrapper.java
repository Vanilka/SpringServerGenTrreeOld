package com.genealogytree.domain.wrappers;

import com.genealogytree.domain.beans.RelationBean;

import java.util.List;

/**
 * Created by vanilka on 05/01/2017.
 */
public class addRelationWrapper {

    private RelationBean relation;
    private List<RelationBean> toUpdateRelations;
    private List<RelationBean> toDeleteRelations;

    addRelationWrapper() {
        this(null, null, null);
    }

    addRelationWrapper(RelationBean relation,
                       List<RelationBean> toUpdateRelations,
                       List<RelationBean> toDeleteRelations) {
        this.relation = relation;
        this.toUpdateRelations = toUpdateRelations;
        this.toDeleteRelations = toDeleteRelations;
    }

    public void  addToUpdateRelation(RelationBean relation) {
        this.toUpdateRelations.add(relation);
    }

    public void  addToDeleteRelation(RelationBean relation) {
        this.toDeleteRelations.add(relation);
    }

    /*
     *      GETTERS
     */

    public RelationBean getRelation() {
        return relation;
    }

    public List<RelationBean> getToUpdateReliations() {
        return toUpdateRelations;
    }

    public List<RelationBean> getToDelateRelations() {
        return toDeleteRelations;
    }

    /*
     *      SETTERS
     */

    public void setRelation(RelationBean relation) {
        this.relation = relation;
    }

    public void setToUpdateReliations(List<RelationBean> toUpdateReliations) {
        this.toUpdateRelations = toUpdateReliations;
    }

    public void setToDelateRelations(List<RelationBean> toDelateRelations) {
        this.toDeleteRelations = toDelateRelations;
    }
}

