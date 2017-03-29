package com.genealogytree.services.implementation;

import com.genealogytree.application.GenealogyTreeContext;
import com.genealogytree.domain.GTX_Family;
import com.genealogytree.domain.GTX_Member;
import com.genealogytree.domain.GTX_Relation;
import com.genealogytree.services.GTFamilyService;
import com.genealogytree.services.responses.FamilyResponse;
import com.genealogytree.services.responses.MemberResponse;
import com.genealogytree.services.responses.RelationResponse;
import com.genealogytree.services.responses.ServerResponse;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by vanilka on 22/11/2016.
 */
public class GTFamilyServiceOffline implements GTFamilyService {

    private static final Logger LOG = LogManager.getLogger(GTFamilyServiceOffline.class);

    private GenealogyTreeContext context;

    private ObjectProperty<GTX_Family> currentFamily;

    private Long counterMemberID;

    private Long counterRelationID;

    {
        currentFamily = new SimpleObjectProperty<>();
        counterMemberID = 0L;
        counterRelationID = 0L;
    }


    public GTFamilyServiceOffline() {
        this(null);
    }

    public GTFamilyServiceOffline(GenealogyTreeContext context) {
        this.context = context;
    }

    @Override
    public ServerResponse getProjects() {
        ServerResponse result = null;
        try {

        } catch (Exception e) {
        }

        return result;
    }

    @Override
    public ServerResponse addNewProject(GTX_Family familyBean) {
        ServerResponse result = null;
        try {

        } catch (Exception e) {
        }
        return result;
    }

    @Override
    public ServerResponse updateFamily(GTX_Family family) {
        return null;
    }

    @Override
    public ServerResponse updateFamilyName(String newName) {

        this.currentFamily.get().setName(newName);
        return new FamilyResponse(currentFamily.get());
    }

    @Override
    public ServerResponse addNewMember(GTX_Member member) {
        member.setId(++counterMemberID);
        this.currentFamily.get().getGtx_membersList().add(member);
        addNewRelation(new GTX_Relation(null, null, member));

        return new MemberResponse(member);
    }

    @Override
    public ServerResponse addNewRelation(GTX_Relation relation) {

        // Verify existing relation with same simLeft and simRight
        setInfoLog("addNewRelation: parameter  -> " + relation.toString());

        relation.getChildren().forEach(this::deleteParentalRelationOfMember);

        GTX_Relation r = findRelationInList(relation);

        setInfoLog("addNewRelation: search relation by SimLeft Right result  -> " + (r != null ? r.toString() : null));

        if (r == null) {
            relation.setId(++counterRelationID);
            this.currentFamily.get().getGtx_relations().add(relation);
            setInfoLog("addNewRelation: relation added  -> " + relation.toString());
        } else {
            mergeRelations(r, relation);
        }

        return new RelationResponse(relation);
    }

    @Override
    public ServerResponse updateRelation(GTX_Relation relation) {
        ObservableList<GTX_Relation> list = getCurrentFamily().getGtx_relations();

        list = list.filtered(p -> p.getId() == relation.getId());

        if (list.size() > 0) {
            mergeRelations(list.get(0), relation);
        }
        return new RelationResponse(list.get(0));
    }

    @Override
    public GTX_Family getCurrentFamily() {
        return this.currentFamily.getValue();
    }

    @Override
    public void setCurrentFamily(GTX_Family family) {
        this.currentFamily.setValue(family);
    }

    @Override
    public ObjectProperty<GTX_Family> currentFamilyProperty() {
        return currentFamily;
    }


    public void setContext(GenealogyTreeContext context) {
        this.context = context;
    }

    /*
     *   CHECKS
     */

    public GTX_Relation findRelationInList(GTX_Relation relation) {


        ObservableList<GTX_Relation> list;
        if (relation.getSimLeft() == null && relation.getSimRight() == null) {
            return null;
        } else if (relation.getSimLeft() == null && relation.getSimLeft() != null) {
            list = this.currentFamily.get().getGtx_relations()
                    .filtered(p -> p.getSimRight() != null)
                    .filtered(p -> p.getSimRight().equals(relation.getSimRight()));
        } else if (relation.getSimLeft() != null && relation.getSimRight() == null) {
            list = this.currentFamily.get().getGtx_relations()
                    .filtered(n -> n.getSimLeft() != null)
                    .filtered(p -> p.getSimLeft().equals(relation.getSimLeft()));
        } else {
            list = this.currentFamily.get().getGtx_relations()
                    .filtered(p -> p.getSimLeft() != null && p.getSimRight() != null)
                    .filtered(p -> p.getSimLeft().equals(relation.getSimLeft())
                            && p.getSimRight().equals(relation.getSimRight()));
        }

        if (list.size() != 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    /*
    *  FUNCTIONS
     */

    private void mergeRelations(GTX_Relation target, GTX_Relation source) {
        setInfoLog("mergeRelations: parameter target -> " + target.toString());
        setInfoLog("mergeRelations: parameter source -> " + target.toString());
        target.setIsActive(source.isIsActive());
        target.setType(source.getType());

        target.setSimLeft(source.getSimLeft());
        target.setSimRight(source.getSimRight());

        if (source.getChildren().size() > 0) {
            source.getChildren().stream().filter(child -> !target.getChildren().contains(child)).forEach(child -> {
                target.getChildren().add(child);
            });
        }
    }

    private void deleteParentalRelationOfMember(GTX_Member member) {
        setInfoLog("deleteParentalRelationOfMember: parameter -> " + member.toString());
        List<GTX_Relation> relations = new LinkedList<>();

        relations.addAll(getCurrentFamily().getGtx_relations()
                .filtered(p -> p.getChildren() != null || p.getChildren().size() != 0)
                .filtered(p -> p.getChildren().contains(member)));

        setInfoLog("deleteParentalRelationOfMember: Relations Found -> " + relations.size() + " " + relations.toString());


        if (relations.size() > 0) {
            for (GTX_Relation relation : relations) {
                if (relation.getSimLeft() == null && relation.getSimRight() == null) {
                    System.out.println("sim left right are null");
                    getCurrentFamily().getGtx_relations().remove(relation);
                } else {
                    if (relation.getChildren().size() == 1) {
                        System.out.println("Children  = 1");
                        getCurrentFamily().getGtx_relations().remove(relation);
                    } else {
                        System.out.println("Children  >1");
                        relation.getChildren().remove(member);
                    }

                }
            }

        }
    }

    private void setInfoLog(String msg) {
        msg = this.getClass().getSimpleName() + ": " + msg;
        LOG.info(msg);
        System.out.println("INFO:  " + msg);
    }

    private void setErrorLog(String msg) {
        msg = this.getClass().getSimpleName() + ": " + msg;
        LOG.error(msg);
        System.out.println("ERROR:  " + msg);
    }
}


