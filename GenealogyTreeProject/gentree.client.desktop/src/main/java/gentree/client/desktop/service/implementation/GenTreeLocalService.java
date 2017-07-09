package gentree.client.desktop.service.implementation;

import gentree.client.desktop.configurations.messages.LogMessages;
import gentree.client.desktop.domain.Family;
import gentree.client.desktop.domain.Member;
import gentree.client.desktop.domain.Relation;
import gentree.client.desktop.service.FamilyService;
import gentree.client.desktop.service.responses.MemberResponse;
import gentree.client.desktop.service.responses.ServiceResponse;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

/**
 * Created by Martyna SZYMKOWIAK on 01/07/2017.
 */
@Log4j2
public class GenTreeLocalService implements FamilyService {

    private ObjectProperty<Family> currentFamily;

    {
        currentFamily = new SimpleObjectProperty<>();
    }

    /*
        Functions
     */

    @Override
    public ServiceResponse addMember(Member member) {
        log.info(LogMessages.MSG_MEMBER_ADD_NEW, member);
            getCurrentFamily().addMember(member);
        return new MemberResponse(member);
    }


    /*
        LISTENERS
     */

    private void setMemberListener() {
        getCurrentFamily().getMembers().addListener((ListChangeListener<Member>) c -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    c.getAddedSubList().forEach(member -> {
                        log.info(LogMessages.MSG_MEMBER_ADD_NEW, member);
                    });
                } else if (c.wasPermutated()) {
                    for (int i = c.getFrom(); i < c.getTo(); ++i) {
                        //permutate
                        System.out.println("permutated");
                    }
                } else if (c.wasUpdated()) {
                    //update item
                    System.out.println("UpdateItem Member");
                } else if (c.wasRemoved()) {
                    System.out.println("Removed " + c.getRemoved().toArray().toString());

                } else {
                }
            }
        });
    }

    private void setRelationListener() {
        getCurrentFamily().getRelations().addListener((ListChangeListener<Relation>) c -> {
            while (c.next()) {
                if (c.wasAdded()) {
/*                    c.getAddedSubList().forEach(relation -> {
                        guard.addObserverTo(relation);
                        if (relation.getId() == null || relation.getId() <= 0) {
                            relation.setId(incrementRelation());
                        } else {
                            idRelation = idRelation < relation.getId() ? relation.getId() : idRelation;
                        }
                        log.info(LogMessages.MSG_RELATION_ADD_NEW, relation);
                    });*/
                } else if (c.wasPermutated()) {
                    for (int i = c.getFrom(); i < c.getTo(); ++i) {
                        //permutate
                        System.out.println(" Relation permutated");
                    }
                } else if (c.wasUpdated()) {
                    //update item
                    System.out.println(" Relation UpdateItem");
                } else if (c.wasRemoved()) {
                    System.out.println("Relation removed" + c.getRemoved().toString());
                } else {
                }
            }
        });
    }

    /*
        GETTERS
     */

    @Override
    public Family getCurrentFamily() {
        return currentFamily.get();
    }

    public ObjectProperty<Family> currentFamilyProperty() {
        return currentFamily;
    }

    /*
        SETTERS
     */

    public void setCurrentFamily(Family currentFamily) {
        this.currentFamily.set(currentFamily);
    }
}

