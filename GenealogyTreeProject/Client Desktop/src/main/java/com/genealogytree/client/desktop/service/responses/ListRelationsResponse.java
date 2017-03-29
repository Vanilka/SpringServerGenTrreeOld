package com.genealogytree.client.desktop.service.responses;

import com.genealogytree.client.desktop.domain.GTX_Relation;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by vanilka on 02/01/2017.
 */
@Setter
@Getter
public class ListRelationsResponse extends ServiceResponse {

    List<GTX_Relation> listRelations;

    public ListRelationsResponse() {
        this(null);
    }

    public ListRelationsResponse(List<GTX_Relation> list) {
        super(ResponseStatus.OK);
        this.listRelations = list;
    }


    @Override
    public String toString() {
        return "ListRelationsResponse{" +
                "listRelations=" + listRelations +
                "} " + super.toString();
    }
}
