package com.genealogytree.services.responses;

import com.genealogytree.domain.GTX_Member;

import java.util.List;

/**
 * Created by vanilka on 26/12/2016.
 */
public class ListMemberResponse extends ServerResponse{


    List<GTX_Member> listMember;

    public ListMemberResponse() {
        this(null);
    }

    public ListMemberResponse(List<GTX_Member> list) {
        super(ResponseStatus.OK);
        this.listMember = list;
    }

    public List<GTX_Member> getListMember() {
        return listMember;
    }

    public void setListFMember(List<GTX_Member> listMember) {
        this.listMember = listMember;
    }
}
