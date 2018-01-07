package gentree.client.visualization.service.implementation;

import gentree.client.desktop.configuration.messages.LogMessages;
import gentree.client.desktop.domain.Member;
import gentree.client.desktop.domain.Relation;
import gentree.client.desktop.service.FamilyContext;
import gentree.client.desktop.service.GenTreeDrawingService;
import gentree.client.visualization.elements.FamilyGroup;
import gentree.client.visualization.vanilla.element.VMember;
import gentree.exception.NotUniqueBornRelationException;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public void startDraw() {
        reset();
        /*
            Find Roots ( simLeft = null and simRight = null
         */

        List<FamilyGroup> groups = findGroups();
        box.getChildren().addAll(groups);

        groups.forEach(g -> {
            g.getRootRelation().getChildren().forEach(child -> {
                g.getContentHbox().getChildren().add(generateMainPane(child));

            });
        });
    }

    @Override
    public WritableImage takeScreenshot() {
        return null;
    }

    private AnchorPane generateMainPane(Member grain) {
        AnchorPane mainPane = new AnchorPane();
        mainPane.getChildren().add(generateMember(grain));
        return mainPane;
    }

    private VMember generateMember(Member m) {
        return new VMember(m);
    }

    /**
     * Function creating groups
     *
     * @return List<FamilyGroup>
     */
    private List<FamilyGroup> findGroups() {
        List<FamilyGroup> result = new ArrayList<>();
        context.getService().getCurrentFamily().getRelations()
                .filtered(r -> r.getLeft() == null)
                .filtered(r -> r.getRight() == null)
                .forEach(root -> result.add(new FamilyGroup(root, nodeCounter++)));

        return result;
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
}
