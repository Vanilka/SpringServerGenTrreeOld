package com.genealogytree.client.desktop.service.implementation;

import com.genealogytree.client.desktop.controllers.implementation.PaneOnlineApplicationChoiceController;
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
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Martyna SZYMKOWIAK on 25/03/2017.
 */
@Getter
@Setter
public class LocalFamilyService implements FamilyService {

    ObjectProperty<GTX_Family> family;

    {
        family = new SimpleObjectProperty<>();
    }

    @Override
    public void setCurrentFamily(GTX_Family family) {
        this.family.setValue(family);
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
        getCurrentFamily().addMember(member);
        getCurrentFamily().addRelation(new GTX_Relation(null, null, member));
        return new MemberResponse(member);
    }
    @Override
    public ServiceResponse addRelation(GTX_Relation relation) {

        // Remove others born relation for member
        for(GTX_Member member : relation.getChildren()) {
            GTX_Relation bornRelation = getCurrentFamily().getBornRelation(member);
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
}
