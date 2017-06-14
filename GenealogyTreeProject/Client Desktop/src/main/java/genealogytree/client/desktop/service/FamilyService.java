package genealogytree.client.desktop.service;

import genealogytree.client.desktop.domain.GTX_Family;
import genealogytree.client.desktop.domain.GTX_Member;
import genealogytree.client.desktop.domain.GTX_Relation;
import genealogytree.client.desktop.service.responses.ServiceResponse;

/**
 * Created by Martyna SZYMKOWIAK on 25/03/2017.
 */
public interface FamilyService {

    GTX_Family getCurrentFamily();
    void setCurrentFamily(GTX_Family family);

    ServiceResponse addMember(GTX_Member member);

    ServiceResponse updateMember(GTX_Member member, GTX_Member updated);

    ServiceResponse addRelation(GTX_Relation relation);

    ServiceResponse updateFamilyName(String newFamilyName);


}
