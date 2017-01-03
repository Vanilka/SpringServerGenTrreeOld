package com.genealogytree.services.responses;

import com.genealogytree.domain.GTX_Member;

/**
 * Created by vanilka on 26/12/2016.
 */
public class MemberResponse extends  ServerResponse {

    GTX_Member member;


   public MemberResponse(GTX_Member member) {
        super(ServerResponse.ResponseStatus.OK);
        this.member = member;
    }

    public  GTX_Member getMember() {
        return this.member;
    }

    public void setMember(GTX_Member member) {
        this.member = member;
    }

    @Override
    public String toString() {
        return "MemberResponse{" +
                "member=" + member +
                "} " + super.toString();
    }
}
