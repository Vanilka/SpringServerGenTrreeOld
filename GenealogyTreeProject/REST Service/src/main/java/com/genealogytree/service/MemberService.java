package com.genealogytree.service;

import com.genealogytree.ExceptionManager.exception.NotFoundFamilyException;
import com.genealogytree.persist.entity.modules.tree.FamilyEntity;
import com.genealogytree.persist.entity.modules.tree.MemberEntity;

import java.util.List;

/**
 * Created by vanilka on 23/11/2016.
 */
public interface MemberService {

    public List<MemberEntity> getMembers(FamilyEntity family) throws NotFoundFamilyException;

    public MemberEntity getMember(Long id);

    public MemberEntity addMember(MemberEntity member);

    public MemberEntity deleteMember(MemberEntity member);

    public boolean exist(MemberEntity memberEntity);
}