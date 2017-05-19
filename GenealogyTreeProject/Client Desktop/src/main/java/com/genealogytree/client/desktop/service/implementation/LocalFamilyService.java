package com.genealogytree.client.desktop.service.implementation;

import com.genealogytree.client.desktop.configuration.helper.ActiveRelationGuard;
import com.genealogytree.client.desktop.configuration.messages.LogMessages;
import com.genealogytree.client.desktop.domain.GTX_Family;
import com.genealogytree.client.desktop.domain.GTX_Member;
import com.genealogytree.client.desktop.domain.GTX_Relation;
import com.genealogytree.client.desktop.service.FamilyService;
import com.genealogytree.client.desktop.service.responses.FamilyResponse;
import com.genealogytree.client.desktop.service.responses.MemberResponse;
import com.genealogytree.client.desktop.service.responses.RelationResponse;
import com.genealogytree.client.desktop.service.responses.ServiceResponse;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import java.util.Comparator;

/**
 * Created by Martyna SZYMKOWIAK on 25/03/2017.
 */
@Getter
@Setter
@Log4j2
public class LocalFamilyService implements FamilyService {

    private Long idMember;
    private Long idRelation;
    private ObjectProperty<GTX_Family> family;
    private ActiveRelationGuard guard;


    {
        log.trace(LogMessages.MSG_SERVICE_INITIALIZATION);
        family = new SimpleObjectProperty<>();
        setFamilyListener();
        idMember = 0L;
        idRelation = 0L;
    }

    private Long incrementMember() {
        return ++idMember;
    }

    private Long incrementRelation() {
        return ++idRelation;
    }

    @Override
    public GTX_Family getCurrentFamily() {
        return family.getValue();
    }

    @Override
    public void setCurrentFamily(GTX_Family family) {
        log.trace(LogMessages.MSG_FAMILY_SERVICE_CURRENT_FAMILY, family);
        this.family.setValue(family);

        if (family.getMembersList().size() > 0) {
            idMember = family.getMembersList().stream().max(Comparator.comparingLong(GTX_Member::getId)).get().getId();
        }

        if (family.getRelationsList().size() > 0) {
            idRelation = family.getRelationsList().stream().max(Comparator.comparingLong(GTX_Relation::getId)).get().getId();
        }
        setMemberListener();
        setRelationListener();

    }

    @Override
    public ServiceResponse updateFamilyName(String newFamilyName) {
        getCurrentFamily().setName(newFamilyName);
        return new FamilyResponse(getCurrentFamily());
    }

    @Override
    public ServiceResponse addMember(GTX_Member member) {
        log.info(LogMessages.MSG_MEMBER_ADD_NEW, member);
        getCurrentFamily().addMember(member);
        getCurrentFamily().addRelation(new GTX_Relation(null, null, member));
        return new MemberResponse(member);
    }

    @Override
    public ServiceResponse updateMember(GTX_Member member, GTX_Member updated) {
        log.info(LogMessages.MSG_MEMBER_UPDATE, member);
        GTX_Member.insertValues(member, updated);
        return new MemberResponse(member);
    }

    @Override
    public ServiceResponse addRelation(GTX_Relation relation) {
        log.trace(LogMessages.MSG_RELATION_ADD_NEW, relation);
        // Remove others born relation for member
        log.trace(LogMessages.MSG_RELATION_VERIF_EXIST_BORN);


        if (relation.getSimLeft() != null && relation.getSimRight() != null && relation.isActive()) {
            getCurrentFamily().getRelationsList()
                    .filtered(r -> r.getSimLeft() != null)
                    .filtered(r -> r.getSimRight() != null)
                    .filtered(r -> (r.getSimRight().equals(relation.getSimRight()) || r.getSimLeft().equals(relation.getSimLeft())))
                    .forEach(r -> r.setActive(false));
        }

        for (GTX_Member member : relation.getChildren()) {
            log.trace(LogMessages.MSG_RELATION_VERIF_EXIST_BORN_FOR, member);
            GTX_Relation bornRelation = getCurrentFamily().getBornRelation(member);
            log.trace(LogMessages.MSG_RELATION_BORN, relation);
            if (bornRelation.getChildren().size() > 1) {
                bornRelation.getChildren().remove(member);
            } else {
                getCurrentFamily().getRelationsList().remove(bornRelation);
            }
        }


        GTX_Relation existing = exist(relation);
        if (existing != null) {
            for (GTX_Member member : relation.getChildren()) {
                if (!existing.getChildren().contains(member)) {
                    existing.getChildren().add(member);
                }
            }
            existing.setType(relation.getType());
            existing.setActive(relation.isActive());


            return new RelationResponse(existing);
        }

        getCurrentFamily().getRelationsList().add(relation);

        return new RelationResponse(relation);
    }


    private GTX_Relation exist(GTX_Relation relation) {
        if (relation.getSimLeft() == null && relation.getSimRight() == null) {
            return null;
        }

        for (GTX_Relation r : getCurrentFamily().getRelationsList()) {
            if (r.compareLeftRight(relation)) {
                return r;
            }
        }
        return null;
    }

    private void setFamilyListener() {
        this.family.addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                guard = new ActiveRelationGuard(newValue.getRelationsList());
            }
        });
    }

    private void setMemberListener() {
        getCurrentFamily().getMembersList().addListener((ListChangeListener<GTX_Member>) c -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    c.getAddedSubList().forEach(member -> {
                        if (member.getId() <= 0) {
                            member.setId(incrementMember());
                        } else {
                            idMember = idMember < member.getId() ? member.getId() : idMember;
                        }
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
        getCurrentFamily().getRelationsList().addListener((ListChangeListener<GTX_Relation>) c -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    c.getAddedSubList().forEach(relation -> {
                        guard.addObserverTo(relation);
                        if (relation.getId() == null || relation.getId() <= 0) {
                            relation.setId(incrementRelation());
                        } else {
                            idRelation = idRelation < relation.getId() ? relation.getId() : idRelation;
                        }
                        log.info(LogMessages.MSG_RELATION_ADD_NEW, relation);
                    });
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
}
