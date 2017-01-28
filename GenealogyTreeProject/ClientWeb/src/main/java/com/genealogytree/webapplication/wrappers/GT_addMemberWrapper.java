package com.genealogytree.webapplication.wrappers;

import com.genealogytree.repository.entity.modules.tree.GT_Member;
import com.genealogytree.repository.entity.modules.tree.GT_Relations;

/**
 * Created by vanilka on 04/01/2017.
 */
public class GT_addMemberWrapper {

    private GT_Member member;
    private GT_Relations relation;

   public GT_addMemberWrapper(GT_Member member, GT_Relations relation) {
        this.member = member;
        this.relation = relation;
    }

    /*
     *      GETTERS
     */

    public GT_Member getMember() {
        return member;
    }

    public GT_Relations getRelation() {
        return relation;
    }

    /*
     *      SETTERS
     */

    public void setMember(GT_Member member) {
        this.member = member;
    }

    public void setRelation(GT_Relations relation) {
        this.relation = relation;
    }

    @Override
    public String toString() {
        return "GT_addMemberWrapper{" +
                "member=" + member +
                ", relation=" + relation +
                '}';
    }
}
