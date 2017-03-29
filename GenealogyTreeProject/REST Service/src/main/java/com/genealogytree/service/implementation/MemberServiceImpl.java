package com.genealogytree.service.implementation;

import com.genealogytree.ExceptionManager.exception.NotFoundFamilyException;
import com.genealogytree.persist.entity.modules.tree.FamilyEntity;
import com.genealogytree.persist.entity.modules.tree.ImagesEntity;
import com.genealogytree.persist.entity.modules.tree.MemberEntity;
import com.genealogytree.repository.MemberRepository;
import com.genealogytree.service.FamilyService;
import com.genealogytree.service.ImagesService;
import com.genealogytree.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by vanilka on 23/11/2016.
 */
@Service
@Transactional
@ComponentScan("com.genealogytree")
public class MemberServiceImpl implements MemberService {

    @Autowired
    MemberRepository repository;

    @Autowired
    ImagesService imagesService;

    @Autowired
    FamilyService familyService;

    @Override
    public List<MemberEntity> getMembers(FamilyEntity family) throws NotFoundFamilyException {

        if (familyService.exist(family) == false) {
            throw new NotFoundFamilyException();
        }
        List<MemberEntity> list = repository.findAllMembersByFamily(family);
        return list;
    }

    @Override
    public MemberEntity getMember(Long id) {
        return null;
    }

    @Override
    public MemberEntity addMember(MemberEntity member) {

        if (member.getImage() != null) {
            member.getImage().setName(ImagesEntity.generateName());
        }

        MemberEntity newMember = repository.save(member);
        return newMember;
    }

    @Override
    public MemberEntity deleteMember(MemberEntity member) {

        this.repository.delete(member);

        return member;
    }

    @Override
    public boolean exist(MemberEntity memberEntity) {
        return repository.exists(memberEntity.getId());
    }
}

