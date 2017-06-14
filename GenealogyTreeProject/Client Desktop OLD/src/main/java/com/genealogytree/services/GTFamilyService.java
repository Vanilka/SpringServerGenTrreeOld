package genealogytree.services;

import genealogytree.application.GenealogyTreeContext;
import genealogytree.domain.GTX_Family;
import genealogytree.domain.GTX_Member;
import genealogytree.domain.GTX_Relation;
import genealogytree.services.responses.ServerResponse;
import javafx.beans.property.ObjectProperty;

/**
 * Created by vanilka on 22/11/2016.
 */
public interface GTFamilyService {

    void setContext(GenealogyTreeContext context);

    ServerResponse getProjects();

    ServerResponse addNewProject(GTX_Family familyBean);

    ServerResponse updateFamily(GTX_Family family);

    ServerResponse updateFamilyName(String newName);

    ServerResponse addNewMember(GTX_Member member);

    ServerResponse addNewRelation(GTX_Relation relation);

    ServerResponse updateRelation(GTX_Relation relation);

    GTX_Family getCurrentFamily();

    void setCurrentFamily(GTX_Family family);

    ObjectProperty<GTX_Family> currentFamilyProperty();


}
