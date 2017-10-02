package gentree.client.visualization.service.implementation;

import gentree.client.desktop.configuration.common.messages.LogMessages;
import gentree.client.desktop.service.FamilyContext;
import gentree.client.visualization.gustave.panels.*;
import gentree.client.desktop.domain.Member;
import gentree.client.desktop.domain.Relation;
import gentree.client.desktop.domain.enums.RelationType;
import gentree.client.desktop.service.GenTreeDrawingService;
import gentree.client.visualization.elements.FamilyGroup;
import gentree.exception.NotUniqueBornRelationException;
import javafx.scene.layout.HBox;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martyna SZYMKOWIAK on 06/07/2017.
 */
@Log4j2
public class GenTreeDrawingServiceImpl implements GenTreeDrawingService {

    private final HBox box;
    private static FamilyContext context;
    private int nodeCounter;
    private Long idReference;

    {
        idReference = 0L;
    }

    public GenTreeDrawingServiceImpl(HBox box) {
        this.box = box;
    }

    @Override
    public void startDraw() {
        reset();
        /*
            Find Roots ( simLeft = null and simRight = null
         */
        List<FamilyGroup> groups = findGroups();
        box.getChildren().addAll(groups);

        groups.forEach(group -> {
            group.getRootRelation().getChildren().forEach(child -> {
                PanelChild panelChild = new PanelChild(child, null);
                populateChild(panelChild);
                group.getContentHbox().getChildren().add(panelChild);
            });
        });
    }

    /**
     * AddRelations for Panel Child
     *
     * @param panelChild
     */
    private void populateChild(PanelChild panelChild) {
        List<Relation> relationsList = context.getService().getCurrentFamily().getRelations()
                .filtered(r -> (r.getLeft() != null && r.getLeft().equals(panelChild.getMember()))
                        || (r.getRight() != null && r.getRight().equals(panelChild.getMember())));


        /*
            Panel Single will allways created
         */
        panelChild.setPanelSingle(new PanelSingle(panelChild.getMember(), null, panelChild));

        /*
            Checking relations
         */
        for (Relation relation : relationsList) {
            SubBorderPane panel = generateSubPanel(relation, panelChild.getMember());

            if (panel instanceof PanelSingle) {
                panelChild.setPanelSingle((PanelSingle) panel);
            } else if (panel instanceof PanelRelationCurrent) {
                panelChild.setPanelRelationCurrent((PanelRelationCurrent) panel);

            } else if (panel instanceof PanelRelationEx) {
                panelChild.addRelationsEx((PanelRelationEx) panel);
            } else {

            }

            /*
                Populate Children Panels
             */
            if (isStrong(relation, panelChild.getMember())) {
                populateRelationPanel(relation, panel);
            }
        }
    }


    private void populateRelationPanel(Relation relation, SubBorderPane panel) {

        relation.getChildren().forEach(c -> {
            PanelChild panelChild = new PanelChild(c, panel);
            populateChild(panelChild);
            ((RelationPane) panel).addChild(panelChild);
        });


    }

    private List<FamilyGroup> findGroups() {
        List<FamilyGroup> result = new ArrayList<>();
        context.getService().getCurrentFamily().getRelations()
                .filtered(r -> r.getLeft() == null)
                .filtered(r -> r.getRight() == null)
                .forEach(root -> result.add(new FamilyGroup(root, nodeCounter++)));

        return result;
    }


    /**
     * Function to generating graphical represntation of relation CURRENT
     *
     * @param relation
     * @param sim
     * @return
     */
    private SubBorderPane generateSubPanel(Relation relation, Member sim) {

        if (relation.getLeft() != null && relation.getLeft().equals(sim)) {
            return generateSupPanel(sim, relation.getRight(), relation);
        }

        if (relation.getRight() != null && relation.getRight().equals(sim)) {
            return generateSupPanel(sim, relation.getLeft(), relation);
        }
        return null;
    }

    /**
     * Function generating Relation Panel of type :
     * a) Single
     * b) Relation Current
     * c) Relation Ex
     * depending the params
     *
     * @param root
     * @param other
     * @param relation
     * @return
     */
    private SubBorderPane generateSupPanel(Member root, Member other, Relation relation) {
        if (other == null) {
            return new PanelSingle(root, relation);
        } else {
            if (relation.getActive() && relation.getType() != RelationType.NEUTRAL) {
                Relation bornRelation = findBornRelation(other);
                if(bornRelation != null && !bornRelation.isRoot() && bornRelation.getReferenceNumber() == 0) {
                    bornRelation.setReferenceNumber(++idReference);
                }
                return new PanelRelationCurrent(other, relation, bornRelation);
            } else {
                return new PanelRelationEx(other, relation);
            }
        }
    }

    /**
     * Strong regles :
     * a) If relation is Active  Father is Strong
     * b) If relation is Not Active  Mather is Strong
     *
     * @param relation
     * @param member
     * @return
     */
    private boolean isStrong(Relation relation, Member member) {
        return whoIsStrong(relation).equals(member);
    }

    /**
     * Strong regles :
     * a) If relation is Active  Father is Strong
     * b) If relation is Not Active  Mather is Strong
     *
     * @param relation
     * @return
     */
    private Member whoIsStrong(Relation relation) {
        if (relation.getLeft() != null || relation.getRight() != null) {
            if (relation.getLeft() == null) {
                return relation.getRight();
            } else if (relation.getRight() == null) {
                return relation.getLeft();
            } else if (relation.getActive()) {
                return relation.getRight();
            } else {
                relation.getLeft();
            }
        } else {
            return null;
        }
        return null;
    }


    private void reset() {
        box.getChildren().clear();
        nodeCounter = 1;
        context.getService().getCurrentFamily().getRelations()
                .filtered(r -> r.getReferenceNumber() > 0)
                .forEach(r -> r.setReferenceNumber(null));
        idReference = 0L;
    }

    private Relation findBornRelation(Member m) {
        try {
            return context.getService().getCurrentFamily().findBornRelation(m);
        } catch (NotUniqueBornRelationException e) {
            log.error(LogMessages.MSG_ERROR_BORN, m);
            return null;
        }
    }

    public static void setContext(FamilyContext context) {
        GenTreeDrawingServiceImpl.context = context;
    }
}
