package com.genealogytree.client.desktop.service;

import com.genealogytree.client.desktop.domain.GTX_Family;
import com.genealogytree.client.desktop.domain.GTX_Member;
import com.genealogytree.client.desktop.domain.GTX_Relation;
import com.genealogytree.client.desktop.service.responses.ServiceResponse;

/**
 * Created by Martyna SZYMKOWIAK on 25/03/2017.
 */
public interface FamilyService {

    GTX_Family getCurrentFamily();
    void setCurrentFamily(GTX_Family family);

    ServiceResponse addMember(GTX_Member member);

    ServiceResponse addRelation(GTX_Relation relation);

    ServiceResponse updateFamilyName(String newFamilyName);


}
