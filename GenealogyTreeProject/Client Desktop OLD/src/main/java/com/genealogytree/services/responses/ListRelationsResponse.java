package com.genealogytree.services.responses;

import com.genealogytree.domain.GTX_Relation;

import java.util.List;

/**
 * Created by vanilka on 02/01/2017.
 */
public class ListRelationsResponse extends ServerResponse {

    List<GTX_Relation> listRelations;

    public ListRelationsResponse() {
        this(null);
    }

    public ListRelationsResponse(List<GTX_Relation> list) {
        super(ResponseStatus.OK);
        this.listRelations = list;
    }

    public List<GTX_Relation> getListRelations() {
        return listRelations;
    }

    public void setListFMember(List<GTX_Relation> listMember) {
        this.listRelations = listMember;
    }

    @Override
    public String toString() {
        return "ListRelationsResponse{" +
                "listRelations=" + listRelations +
                "} " + super.toString();
    }
}
