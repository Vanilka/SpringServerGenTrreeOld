package gentree.client.desktop.service.implementation;

import gentree.client.desktop.controllers.tree_elements.FamilyGroup;
import gentree.client.desktop.controllers.tree_elements.FamilyMember;
import gentree.client.desktop.controllers.tree_elements.panels.PanelChild;
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

    private int nodeCounter;

    @Override
    public void startDraw(HBox box) {

        reset();
        List<FamilyGroup> groups = findGroups();
        box.getChildren().addAll(groups);

        groups.forEach(group -> {
            group.getRootRelation().getChildren().forEach( child -> {
                PanelChild panelChild = new PanelChild(child);
                group.getContentHbox().getChildren().add(panelChild);
            });
        });
    }

    private List<FamilyGroup> findGroups() {
        List<FamilyGroup> result = new ArrayList<>();
        context.getService().getCurrentFamily().getRelations()
                .filtered(r -> r.getLeft() == null)
                .filtered(r -> r.getRight() == null)
                .forEach(root -> result.add(new FamilyGroup(root,  nodeCounter++)));
        return result;
    }

    private boolean isStrong() {
        return false;
    }


    private void reset() {
        nodeCounter = 1;
    }


}
