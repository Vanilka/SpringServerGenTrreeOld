package com.genealogytree.client.desktop.service.implementation;

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


    {
        family = new SimpleObjectProperty<>();
        idMember = 0L;
        idRelation = 0L;
    }

    private Long incrementMember() {
        return idMember++;
    }

    private Long incrementRelation() {
        return idRelation++;
    }

    @Override
    public void setCurrentFamily(GTX_Family family) {
        log.trace(LogMessages.MSG_FAMILY_SERVICE_CURRENT_FAMILY, family);
        this.family.setValue(family);
        setRelationListener();
        setMemberListener();
    }

    @Override
    public GTX_Family getCurrentFamily() {
        return family.getValue();
    }


    @Override
    public ServiceResponse updateFamilyName(String newFamilyName) {
        getCurrentFamily().setName(newFamilyName);
        return new FamilyResponse(getCurrentFamily());
    }

    @Override
    public ServiceResponse addMember(GTX_Member member) {
        log.trace(LogMessages.MSG_MEMBER_ADD_NEW, member);
        getCurrentFamily().addMember(member);
        getCurrentFamily().addRelation(new GTX_Relation(null, null, member));
        return new MemberResponse(member);
    }

    @Override
    public ServiceResponse addRelation(GTX_Relation relation) {
        log.trace(LogMessages.MSG_RELATION_ADD_NEW, relation);
        // Remove others born relation for member
        log.trace(LogMessages.MSG_RELATION_VERIF_EXIST_BORN);
        for(GTX_Member member : relation.getChildren()) {
            log.trace(LogMessages.MSG_RELATION_VERIF_EXIST_BORN_FOR, member);
            GTX_Relation bornRelation = getCurrentFamily().getBornRelation(member);
            log.trace(LogMessages.MSG_RELATION_BORN, relation);
            if(bornRelation.getChildren().size() > 1)  {
                bornRelation.getChildren().remove(member);
            } else {
                getCurrentFamily().getGtx_relations().remove(bornRelation);
            }
        }

        GTX_Relation existing = exist(relation);
        if(existing != null) {
            for (GTX_Member member : relation.getChildren()) {
                if (! existing.getChildren().contains(member)) {
                    existing.getChildren().add(member);
                }
            }
            return new RelationResponse(existing);
        }

        getCurrentFamily().getGtx_relations().add(relation);
        return new RelationResponse(relation);
    }


    private GTX_Relation exist(GTX_Relation relation) {
        if(relation.getSimLeft() == null && relation.getSimRight() == null) {
            return null;
        }

        for(GTX_Relation r : getCurrentFamily().getGtx_relations()) {
            if(r.compareLeftRight(relation)) {
                return r;
            }
        }

        return null;
    }


    private void setMemberListener() {
        getCurrentFamily().getGtx_membersList().addListener((ListChangeListener<GTX_Member>) c -> {
            while (c.next()) {
                if(c.wasAdded()) {
                    c.getAddedSubList().forEach(member -> {
                        member.setId(incrementMember());
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
                } else {
                }
            }
        });
    }

    private void  setRelationListener() {
        getCurrentFamily().getGtx_relations().addListener((ListChangeListener<GTX_Relation>) c -> {
            while (c.next()) {
                if(c.wasAdded()) {
                    c.getAddedSubList().forEach(relation -> {
                        relation.setId(incrementRelation());
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
                } else {
                }
            }
        });
    }
}
