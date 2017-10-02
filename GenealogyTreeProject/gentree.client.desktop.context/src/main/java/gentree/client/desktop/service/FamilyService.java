package gentree.client.desktop.service;

import gentree.client.desktop.domain.Family;
import gentree.client.desktop.domain.Member;
import gentree.client.desktop.domain.Relation;
import gentree.client.desktop.domain.enums.RelationType;
import gentree.client.desktop.responses.ServiceResponse;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.ObservableList;

import java.util.List;

/**
 * Created by Martyna SZYMKOWIAK on 01/07/2017.
 */
public interface FamilyService {

    Family getCurrentFamily();

    void setCurrentFamily(Family family);

    ReadOnlyObjectProperty<Family> familyProperty();

    ObservableList<Member> findAllRootMembers();

    ServiceResponse addMember(Member member);

    ServiceResponse addRelation(Relation relation);

    ServiceResponse addRelation(Member left, Member right, RelationType type, boolean active);

    ServiceResponse moveChildrenToNewRelation(Relation to, List<Member> children);

    ServiceResponse updateRelation(Relation relation);

    ServiceResponse moveChildFromRelation(Member m, Relation oldRelation, Relation newRelation);

    Relation findRelation(Member left, Member right);

    boolean isAscOf(Member grain, Member sim);

    boolean isDescOf(Member grain, Member sim);

}
