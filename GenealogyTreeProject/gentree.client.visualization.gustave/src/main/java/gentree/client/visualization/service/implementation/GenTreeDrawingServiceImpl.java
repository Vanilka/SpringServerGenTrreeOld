package gentree.client.visualization.service.implementation;

import gentree.client.desktop.configuration.messages.LogMessages;
import gentree.client.desktop.domain.Member;
import gentree.client.desktop.domain.Relation;
import gentree.client.desktop.service.FamilyContext;
import gentree.client.desktop.service.GenTreeDrawingService;
import gentree.client.visualization.CleanTask;
import gentree.client.visualization.elements.FamilyGroup;
import gentree.client.visualization.gustave.panels.*;
import gentree.common.configuration.enums.RelationType;
import gentree.exception.NotUniqueBornRelationException;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * Created by Martyna SZYMKOWIAK on 06/07/2017.
 */
@Log4j2
public class GenTreeDrawingServiceImpl implements GenTreeDrawingService {

    private static FamilyContext context;
    private final HBox box;
    private int nodeCounter;
    private Long idReference;

    {
        idReference = 0L;
    }

    public GenTreeDrawingServiceImpl(HBox box) {
        this.box = box;
    }

    public static void setContext(FamilyContext context) {
        GenTreeDrawingServiceImpl.context = context;
    }


    public WritableImage takeScreenshot() {
        WritableImage image = box.snapshot(new SnapshotParameters(), null);
        return image;
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
                panelChild.setFamilyGroup(group);
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
                .stream()
                .filter(r -> (r.getLeft() != null && r.getLeft().equals(panelChild.getMember()))
                        || (r.getRight() != null && r.getRight().equals(panelChild.getMember())))
                .collect(Collectors.toList());


        /*
            Panel Single will allways created
         */
        panelChild.setPanelSingle(new PanelSingle(panelChild.getMember(), null));

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

    /**
     * Function creating groups
     *
     * @return
     */
    private List<FamilyGroup> findGroups() {
        List<FamilyGroup> result = new ArrayList<>();
        List<Relation> rootList = context.getService().getCurrentFamily().getRelations()
                .stream()
                .filter(r -> r.getLeft() == null)
                .filter(r -> r.getRight() == null)
                .collect(Collectors.toList());


        if (!rootList.isEmpty()) rootList.forEach(root ->
        {
            if (shouldBeCreated(root)) result.add(new FamilyGroup(root, nodeCounter++));
        });

        return result;
    }

    /**
     * Verify if relation root has relation with others sims and is FORT
     *
     * @param root
     * @return
     */
    private boolean shouldBeCreated(Relation root) {
        if (root.getChildren().size() > 1) return true;
        Member rootSim = root.getChildren().get(0);

        List<Relation> relations = context.getService().getCurrentFamily().getRelations().stream()
                .filter(relation -> Objects.equals(relation.getLeft(), rootSim) || Objects.equals(relation.getRight(), rootSim)).collect(Collectors.toList());

        if (relations.isEmpty()) return true;
        if (relations.size() > 1) return true;
        if (isStrong(relations.get(0), rootSim)) return true;

        return false;
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
            Relation bornRelationSpouse = findBornRelation(other);
            if (bornRelationSpouse != null && !bornRelationSpouse.isRoot() && bornRelationSpouse.getReferenceNumber() == 0) {
                bornRelationSpouse.setReferenceNumber(++idReference);
            }

            if (relation.getChildren().size() > 0 && isStrong(relation, root)) {
                relation.setReferenceNumber(++idReference);
            }
            if (relation.getActive() && relation.getType() != RelationType.NEUTRAL) {
                return new PanelRelationCurrent(other, relation, bornRelationSpouse);
            } else {
                return new PanelRelationEx(other, relation, bornRelationSpouse);
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

        Member result = whoIsStrong(relation);

        return result != null && result.equals(member);
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

        Member response = null;

        if (relation.getLeft() != null || relation.getRight() != null) {
            if (relation.getLeft() == null) {
                response = relation.getRight();
            } else if (relation.getRight() == null) {
                response = relation.getLeft();
            } else if (relation.getActive() || relation.getType() == RelationType.NEUTRAL) {
                response = relation.getRight();
            } else {
                response = relation.getLeft();
            }
        }
        return response;
    }

    private void reset() {

        cleanGenTree();

        nodeCounter = 1;
        List<Relation> list = context.getService().getCurrentFamily().getRelations()
                .stream()
                .filter(r -> r.getReferenceNumber() > 0).collect(Collectors.toList());
        list.forEach(r -> r.setReferenceNumber(null));
        idReference = 0L;
        System.gc();
    }

    private Relation findBornRelation(Member m) {
        try {
            return context.getService().getCurrentFamily().findBornRelation(m);
        } catch (NotUniqueBornRelationException e) {
            log.error(LogMessages.MSG_ERROR_BORN, m);
            return null;
        }
    }

    private void cleanGenTree() {
        ExecutorService executor = Executors.newFixedThreadPool(4);
        box.getChildren().forEach(n -> executor.submit(new CleanTask(n)));
        box.getChildren().clear();
    }
}
