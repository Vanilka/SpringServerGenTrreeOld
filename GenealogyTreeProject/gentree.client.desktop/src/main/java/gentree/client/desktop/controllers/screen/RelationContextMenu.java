package gentree.client.desktop.controllers.screen;

import gentree.client.desktop.configurations.enums.FilesFXML;
import gentree.client.desktop.controllers.tree_elements.RelationTypeElement;
import gentree.client.desktop.service.GenTreeContext;
import gentree.client.desktop.service.ScreenManager;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.ContextMenuEvent;

/**
 * Created by Martyna SZYMKOWIAK on 08/09/2017.
 */
public class RelationContextMenu extends ContextMenu {

    GenTreeContext context = GenTreeContext.INSTANCE;
    ScreenManager sm = ScreenManager.INSTANCE;
    RelationTypeElement relation;

    private MenuItem itemChangeType = new MenuItem("ChangeType");
    private MenuItem itemAddChildren = new MenuItem("AddChildren");
    private MenuItem itemRemove = new MenuItem("RemoveRelation");

    public RelationContextMenu() {
        super();
        initItems();
        this.getItems().addAll(itemChangeType, itemAddChildren, itemRemove);
    }

    public void show(RelationTypeElement r, ContextMenuEvent event) {
        this.relation = r;
        show(r, event.getScreenX(), event.getScreenY());
    }

    private void initItems() {
        initItemAddParents();
        initItemAddChildren();
    }

    private void initItemAddParents() {

        itemChangeType.setOnAction(event -> sm.showNewDialog(new DialogAddParentsToMemberController(), relation, FilesFXML.DIALOG_ADD_PARENTS_TO_MEMBER));
    }
    https://github.com/Vanilka/GenealogyTree/tree/GenTreeV3-refactoring-placement/GenealogyTreeProject/gentree.client.desktop/src/main/java/gentree/client/desktop/controllers/tree_elements/panelshttps://github.com/Vanilka/GenealogyTree/tree/GenTreeV3-refactoring-placement/GenealogyTreeProject/gentree.client.desktop/src/main/java/gentree/client/desktop/controllers/tree_elements/panels
    private void initItemAddChildren() {
        itemAddChildren.setOnAction(event -> sm.showNewDialog(new DialogAddSpouseController(), relation, FilesFXML.DIALOG_ADD_SPOUSE_TO_MEMBER));
    }

    private void initRemoveRelation() {
        //itemRemove.setOnAction(event -> sm.showNewDialog(new DialogAddChildrenController(), member.getMember(), FilesFXML.DIALOG_ADD_CHILDREN));
    }

}
