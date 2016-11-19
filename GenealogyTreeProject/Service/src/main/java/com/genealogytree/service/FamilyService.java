package com.genealogytree.service;

import com.genealogytree.repository.entity.modules.administration.GT_User;
import com.genealogytree.repository.entity.modules.tree.GT_Family;

import java.util.List;

public interface FamilyService {


    public List<GT_Family> getAllFamilies();
    public List<GT_Family> getFamillies(GT_User user);

    public GT_Family addFamily(GT_Family family);


}
