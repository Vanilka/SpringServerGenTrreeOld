package gentree.client.desktop.service;

import gentree.client.desktop.domain.Family;
import gentree.client.desktop.domain.Member;
import gentree.client.desktop.domain.Relation;
import gentree.client.desktop.service.responses.ServiceResponse;
import javafx.beans.property.ReadOnlyObjectProperty;

/**
 * Created by Martyna SZYMKOWIAK on 01/07/2017.
 */
public interface FamilyService  {

    Family getCurrentFamily();

    ReadOnlyObjectProperty<Family> familyProperty();

    void setCurrentFamily(Family family);

    ServiceResponse addMember(Member member);

    ServiceResponse addRelation(Relation relation);

    ServiceResponse updateRelation(Relation relation);

    ServiceResponse moveChildFromRelation(Member m, Relation oldRelation, Relation newRelation);

    Relation findRelation(Member left, Member right);

}
