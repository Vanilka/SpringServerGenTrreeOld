package gentree.client.desktop.service.implementation;

import gentree.client.desktop.controllers.tree_elements.FamilyGroup;
import gentree.client.desktop.controllers.tree_elements.panels.*;
import gentree.client.desktop.domain.Member;
import gentree.client.desktop.domain.Relation;
import gentree.client.desktop.domain.enums.RelationType;
import gentree.client.desktop.service.GenTreeContext;
import gentree.client.desktop.service.GenTreeDrawingService;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martyna SZYMKOWIAK on 06/07/2017.
 */
public class GenTreeDrawingServiceImpl implements GenTreeDrawingService {

    GenTreeContext context = GenTreeContext.INSTANCE;

    private final HBox box;

    private int nodeCounter;

    public GenTreeDrawingServiceImpl(HBox box) {
        this.box = box;
    }

    @Override
    public void startDraw() {
        reset();
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

    private void populateChild(PanelChild panelChild) {
        List<Relation> relationsList = context.getService().getCurrentFamily().getRelations()
                .filtered(r -> (r.getLeft() != null && r.getLeft().equals(panelChild.getMember()))
                        || (r.getRight() != null && r.getRight().equals(panelChild.getMember())));


        /*
            Panel Single will allways created
         */
        panelChild.setPanelSingle(new PanelSingle(panelChild.getMember(), panelChild));

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
            populateRelationPanel(relation, panel);
        }
    }

    private void populateRelationPanel(Relation relation, SubBorderPane panel) {

        relation.getChildren().forEach(c -> {
            PanelChild panelChild = new PanelChild(c, panel);
            populateChild(panelChild);
            ((RelationPane) panel ).addChild(panelChild);
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
            return generateSupPanel(sim, relation.getRight(), relation.getType(), relation.getActive());
        }

        if (relation.getRight() != null && relation.getRight().equals(sim)) {
            return generateSupPanel(sim, relation.getLeft(), relation.getType(), relation.getActive());
        }
        return null;
    }

    private SubBorderPane generateSupPanel(Member root, Member other, RelationType type, boolean isActive) {
        if (other == null) {
            return new PanelSingle(root);
        } else {
            if (isActive && type != RelationType.NEUTRAL) {
                return new PanelRelationCurrent(other, type);
            } else {
                return new PanelRelationEx(other, type);
            }
        }
    }


    private boolean isStrong() {
        return false;
    }


    private void reset() {
        box.getChildren().clear();
        nodeCounter = 1;
    }


}
