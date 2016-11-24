package com.genealogytree.service.implementation;

import com.genealogytree.ExceptionManager.exception.NotFoundFamilyException;
import com.genealogytree.repository.FamilyRepository;
import com.genealogytree.repository.MemberRepository;
import com.genealogytree.repository.entity.modules.tree.GT_Family;
import com.genealogytree.repository.entity.modules.tree.GT_Member;
import com.genealogytree.service.FamilyService;
import com.genealogytree.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by vanilka on 23/11/2016.
 */
@Service
@Transactional
@ComponentScan("com.genealogytree")
public class MemberServiceImpl  implements MemberService {

    @Autowired
    MemberRepository repository;

    @Autowired
    FamilyService familyService;

    @Override
    public List<GT_Member> getMembers(GT_Family family) throws NotFoundFamilyException {

        if(familyService.exist(family) == false) {
            throw new NotFoundFamilyException();
        }

        List<GT_Member> list = repository.findAllMembersByFamily(family);
        return list;
    }

    @Override
    public GT_Member getMember(Long id) {
        return null;
    }

    @Override
    public GT_Member addMember(GT_Member member) {
        return repository.save(member);
    }
}
