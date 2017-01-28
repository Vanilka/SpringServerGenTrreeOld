package com.genealogytree.domain.wrappers;

import com.genealogytree.domain.beans.MemberBean;
import com.genealogytree.domain.beans.RelationBean;

/**
 * Created by vanilka on 04/01/2017.
 */
public class addMemberWrapper {

    private MemberBean member;
    private RelationBean relation;

    public addMemberWrapper() {
        this(null, null);
    }

    public addMemberWrapper(MemberBean member, RelationBean relation) {
        this.member = member;
        this.relation = relation;
    }

    /*
     *      GETTERS
     */

    public MemberBean getMember() {
        return member;
    }

    public RelationBean getRelation() {
        return relation;
    }


    /*
     *      SETTERS
     */

    public void setMember(MemberBean member) {
        this.member = member;
    }

    public void setRelation(RelationBean relation) {
        this.relation = relation;
    }


    @Override
    public String toString() {
        return "addMemberWrapper{" +
                "member=" + member +
                ", relation=" + relation +
                '}';
    }
}
