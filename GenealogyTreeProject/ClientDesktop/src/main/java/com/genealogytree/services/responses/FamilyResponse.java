package com.genealogytree.services.responses;

import com.genealogytree.domain.GTX_Family;

/**
 * Created by vanilka on 19/11/2016.
 */
public class FamilyResponse extends  ServerResponse{

    GTX_Family family;

    public FamilyResponse(GTX_Family bean) {
        super(ServerResponse.ResponseStatus.OK);
        this.family = bean;
    }

    public GTX_Family getFamily() {
        return family;
    }

    public void setFamily(GTX_Family bean) {
        this.family = bean;
    }


}
