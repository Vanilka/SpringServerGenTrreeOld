package com.genealogytree.services.responses;

import com.genealogytree.domain.beans.FamilyBean;

import java.util.List;

/**
 * Created by vanilka on 11/11/2016.
 */
public class ListFamilyResponse extends ServerResponse {

    List<FamilyBean> listFamily;

    public ListFamilyResponse() {
        this(null);
    }

    public ListFamilyResponse(List<FamilyBean> list) {
        super(ResponseStatus.OK);
        this.listFamily = list;
    }

    public List<FamilyBean> getListFamily() {
        return listFamily;
    }

    public void setListFamily(List<FamilyBean> listFamily) {
        this.listFamily = listFamily;
    }
}
