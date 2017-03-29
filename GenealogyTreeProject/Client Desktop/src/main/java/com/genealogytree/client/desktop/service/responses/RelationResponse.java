package com.genealogytree.client.desktop.service.responses;


import com.genealogytree.client.desktop.domain.GTX_Relation;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by vanilka on 31/12/2016.
 */
@Setter
@Getter
public class RelationResponse extends ServiceResponse {

    GTX_Relation relation;

    public RelationResponse(GTX_Relation relation) {
        super(ResponseStatus.OK);
        this.relation = relation;
    }


    @Override
    public String toString() {
        return "RelationResponse{" +
                "relation=" + relation +
                "} " + super.toString();
    }
}
