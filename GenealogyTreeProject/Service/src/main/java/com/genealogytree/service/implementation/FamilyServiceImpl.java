package com.genealogytree.service.implementation;

import com.genealogytree.ExceptionManager.exception.NotFoundFamilyException;
import com.genealogytree.repository.FamilyRepository;
import com.genealogytree.repository.entity.modules.administration.GT_User;
import com.genealogytree.repository.entity.modules.tree.GT_Family;
import com.genealogytree.service.FamilyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@ComponentScan("com.genealogytree")
public class FamilyServiceImpl implements FamilyService {

    @Autowired
    FamilyRepository repository;

    public List<GT_Family> getAllFamilies() {
        List<GT_Family> list = this.repository.findAll();
        return list;
    }

    @Override
    public List<GT_Family> getFamillies(GT_User user) {
        List<GT_Family> List = this.repository.findAllFamillyByOwner(user);
        return List;
    }

    public GT_Family addFamily(GT_Family family) {

        return this.repository.save(family);
    }

    @Override
    public GT_Family updateFamily(GT_Family family) {
        return this.repository.saveAndFlush(family);
    }

    @Override
    public GT_Family getFamily(Long id) throws NotFoundFamilyException{
        GT_Family family = this.repository.findOne(id);
        if(family == null) {
            throw  new NotFoundFamilyException();
        }
        return family;
    }

    @Override
    public boolean exist(GT_Family family) {
        return this.repository.exists(family.getId());
    }

}
