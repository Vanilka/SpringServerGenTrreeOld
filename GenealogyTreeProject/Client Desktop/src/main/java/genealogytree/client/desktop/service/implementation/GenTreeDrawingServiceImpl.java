package genealogytree.client.desktop.service.implementation;

import genealogytree.client.desktop.configuration.ContextGT;
import genealogytree.client.desktop.controllers.implementation.custom.GTPanelSim;
import genealogytree.client.desktop.controllers.implementation.custom.tree_elements.*;
import genealogytree.client.desktop.domain.GTX_Member;
import genealogytree.client.desktop.domain.GTX_Relation;
import genealogytree.client.desktop.service.GenTreeDrawingService;
import genealogytree.domain.enums.RelationType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martyna SZYMKOWIAK on 31/03/2017.
 */
public class GenTreeDrawingServiceImpl implements GenTreeDrawingService {

    ContextGT context = ContextGT.getInstance();
    AnchorPane common;
    private int nodeCounter;

    public GenTreeDrawingServiceImpl() {
        nodeCounter = 1;

    }


    @Override
    public void startDraw(HBox box) {

        reset();
        /*
            Retrieve all NODES from RelationList
            NODE = Relation where SimLeft == null and SimRight == null
         */
        List<GTNode> nodes = findNodes();
        box.getChildren().addAll(nodes);

        nodes.forEach(node -> {
            node.getRootRelation().getChildren().forEach(child -> {
                GTLeaf leaf = new GTLeaf(child);
                GTPanelChild panelChild = new GTPanelChild(leaf, null);
                try {
                    populatePanel(panelChild, null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                node.getContentHbox().getChildren().add(panelChild);
            });
        });
    }


    public void populatePanel(GTPanelChild child, GTPanelSim parent) throws Exception {

        /*
         * Search all relations where  (GTPanelChild) child is simLeft or simRight
         */
        List<GTX_Relation> relations = context.getService().getCurrentFamily().getRelationsList()
                .filtered(r -> ((r.getSimLeft() != null) && r.getSimLeft().equals(child.getLeaf().getMember()))
                        || r.getSimRight() != null && r.getSimRight().equals(child.getLeaf().getMember()));

        if (!verifyIsActiveCount(relations)) {
            // throw new Exception("More that ONE relation Active !");
        }

        /*

         */

        child.setPanelSingle(new GTPanelSignle(new GTLeaf(child.getLeaf().getMember()), child));


        for (GTX_Relation relation : relations) {
            GTPanelSim panelSim;

            if (relation.getSimLeft() == null || relation.getSimRight() == null) {
                panelSim = generatePanelCurrent(relation, child.getLeaf().getMember());
                ((GTPanelSignle) panelSim).setParentPanel(child);
                child.setPanelSingle(((GTPanelSignle) panelSim));

            } else if (relation.getSimLeft() != null && relation.getSimRight() != null
                    && relation.getType() != RelationType.NEUTRAL && relation.isActive()) {
                panelSim = generatePanelCurrent(relation, child.getLeaf().getMember());
                ((GTPanelCouple) panelSim).setParentPanel(child);
                child.setPanelCouple(((GTPanelCouple) panelSim));
            } else {
                panelSim = generatePanelEx(relation, child.getLeaf().getMember());
                ((GTPanelEx) panelSim).setParentPanel(child);
                child.getPanelsExList().add(((GTPanelEx) panelSim));
            }
            relation.getChildren().forEach(c -> {
                GTLeaf leaf = new GTLeaf(c);
                GTPanelChild panelChild = new GTPanelChild(leaf, panelSim);
                try {
                    populatePanel(panelChild, null);
                    panelSim.setGTPanelChild(panelChild);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

    }

    /**
     * Function to generating graphical represntation of relation CURRENT
     *
     * @param relation
     * @param sim
     * @return
     */
    private GTPanelCurrent generatePanelCurrent(GTX_Relation relation, GTX_Member sim) {

        if (relation.getSimLeft() != null && relation.getSimLeft().equals(sim)) {
            if (relation.getSimRight() == null) {
                return new GTPanelSignle(new GTLeaf(sim), null);
            } else {
                return new GTPanelCouple(new GTLeaf(sim), new GTLeaf(relation.getSimRight()), new GTRelationType(relation.getType(), relation.isActive()));
            }
        }

        if (relation.getSimRight() != null && relation.getSimRight().equals(sim)) {
            if (relation.getSimLeft() == null) {
                return new GTPanelSignle(new GTLeaf(sim), null);
            } else {
                return new GTPanelCouple(new GTLeaf(sim), new GTLeaf(relation.getSimLeft()), new GTRelationType(relation.getType(), relation.isActive()));
            }
        }
        return null;
    }

    /**
     * Function to generating graphical representation of Relation EX
     *
     * @param relation
     * @param sim
     * @return GTPanelEx
     */
    private GTPanelEx generatePanelEx(GTX_Relation relation, GTX_Member sim) {
        if (relation.getSimRight() != null && relation.getSimLeft() != null) {
            if (relation.getSimRight().equals(sim)) {
                return new GTPanelEx(new GTLeaf(sim), new GTLeaf(relation.getSimLeft()), generateRelationType(relation), null);
            }

            if (relation.getSimLeft().equals(sim)) {
                return new GTPanelEx(new GTLeaf(sim), new GTLeaf(relation.getSimRight()), generateRelationType(relation), null);
            }
        }

        return null;
    }

    /**
     *  Function generating graphical representation of Relation
     *
     * @param relation
     * @return GTRelationType
     */
    private GTRelationType generateRelationType(GTX_Relation relation) {
        return new GTRelationType(relation.getType(), relation.isActive());
    }

    /**
     *  Function to verify that the list of relation contain relation of type CURRENT
     *  CURRENT : (simLeft != null  and simRight != null and relation type != neutral and IS ACTIVE)
     *
     * @param relations
     * @return true/false
     */
    private boolean hasCurrentRelation(List<GTX_Relation> relations) {
        return (relations.stream()
                .filter(relation -> relation.getSimRight() != null)
                .filter(relation -> relation.getSimLeft() != null)
                .filter(relation -> relation.getType() != RelationType.NEUTRAL)
                .filter(GTX_Relation::isActive)
                .count() > 0);
    }

    /**
     * Function to find all roots from Relations Lists
     * <br> The function search the relation "ROOT":
     * <br> Mandatory Condition:
     * <br> SimLeft = null
     * <br> SimRight = null
     *
     * @return List of GTNode's
     */
    private List<GTNode> findNodes() {
        List<GTNode> result = new ArrayList<>();

        context.getService().getCurrentFamily().getRelationsList()
                .filtered(r -> r.getSimLeft() == null)
                .filtered(r -> r.getSimRight() == null)
                .forEach(node -> {
                    result.add(new GTNode(node, nodeCounter++));
                });
        return result;
    }


    /**
     * Function for juge for which partner in Relation the children will appartient.
     *
     * @return true or false
     */
    public boolean isStrong() {


        return false;
    }

    private void reset() {
        nodeCounter = 1;
        Long tempId = 0L;
        for (GTX_Relation gtx_relation : context.getService().getCurrentFamily().getRelationsList()) {
            gtx_relation.setId(tempId++);
        }
    }

    private boolean verifyIsActiveCount(List<GTX_Relation> list) {

        if (list.stream().filter(relation -> (relation.getType() != RelationType.NEUTRAL))
                .filter(GTX_Relation::isActive).count() > 1) {
            return false;
        }
        return true;
    }
}
