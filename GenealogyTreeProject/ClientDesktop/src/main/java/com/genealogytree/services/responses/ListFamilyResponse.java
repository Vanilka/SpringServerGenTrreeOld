package com.genealogytree.services.responses;

import com.genealogytree.domain.GTX_Family;

import java.util.List;

/**
 * Created by vanilka on 11/11/2016.
 */
public class ListFamilyResponse extends ServerResponse {

    List<GTX_Family> listFamily;

    public ListFamilyResponse() {
        this(null);
    }

    public ListFamilyResponse(List<GTX_Family> list) {
        super(ResponseStatus.OK);
        this.listFamily = list;
    }

    public List<GTX_Family> getListFamily() {
        return listFamily;
    }

    public void setListFamily(List<GTX_Family> listFamily) {
        this.listFamily = listFamily;
    }
}
