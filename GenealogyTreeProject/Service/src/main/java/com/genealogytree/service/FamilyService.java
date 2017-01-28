package com.genealogytree.service;

import com.genealogytree.ExceptionManager.exception.NotFoundFamilyException;
import com.genealogytree.repository.entity.modules.administration.GT_User;
import com.genealogytree.repository.entity.modules.tree.GT_Family;

import java.util.List;

public interface FamilyService {


    List<GT_Family> getAllFamilies();

    List<GT_Family> getFamillies(GT_User user);

    GT_Family addFamily(GT_Family family);

    GT_Family updateFamily(GT_Family family);

    GT_Family getFamily(Long id) throws NotFoundFamilyException;

    boolean exist(GT_Family family);


}
