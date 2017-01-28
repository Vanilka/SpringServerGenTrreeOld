package com.genealogytree.service;

import com.genealogytree.ExceptionManager.exception.NotFoundFamilyException;
import com.genealogytree.repository.entity.modules.tree.GT_Family;
import com.genealogytree.repository.entity.modules.tree.GT_Member;

import java.util.List;

/**
 * Created by vanilka on 23/11/2016.
 */
public interface MemberService {

    public List<GT_Member> getMembers(GT_Family family) throws NotFoundFamilyException;

    public  GT_Member getMember(Long id);

    public GT_Member addMember(GT_Member member);

    public GT_Member deleteMember(GT_Member member);
}
