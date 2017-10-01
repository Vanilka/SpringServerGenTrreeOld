package gentree.client.desktop.controllers.screen;

import gentree.client.desktop.configurations.enums.FilesFXML;
import gentree.client.desktop.controllers.tree_elements.FamilyMember;
import gentree.client.desktop.service.GenTreeContext;
import gentree.client.desktop.service.ScreenManager;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.ContextMenuEvent;

/**
 * Created by Martyna SZYMKOWIAK on 20/07/2017.
 */
public class SimContextMenu extends ContextMenu {

    GenTreeContext context = GenTreeContext.INSTANCE;
    ScreenManager sm = ScreenManager.INSTANCE;
    FamilyMember member;
    private MenuItem itemAddParents = new MenuItem("AddParents");
    private MenuItem itemAddSiblings = new MenuItem("AddSpouse");
    private MenuItem itemAddChildren = new MenuItem("AddChildren");


    public SimContextMenu() {
        super();
        initItems();
        this.getItems().addAll(itemAddParents, itemAddSiblings, itemAddChildren);
    }

    public void show(FamilyMember n, ContextMenuEvent event) {
        member = n;
        show(n, event.getScreenX(), event.getScreenY());
    }

    private void initItems() {
        initItemAddParents();
        initItemAddSpouse();
    }

    private void initItemAddParents() {

        itemAddParents.setOnAction(event -> sm.showNewDialog(new DialogAddParentsToMemberController(), member.getMember(), FilesFXML.DIALOG_ADD_PARENTS_TO_MEMBER));
    }

    private void initItemAddSpouse() {
        itemAddSiblings.setOnAction(event -> sm.showNewDialog(new DialogAddSpouseController(), member.getMember(), FilesFXML.DIALOG_ADD_SPOUSE_TO_MEMBER));
    }

    private void initItemAddChildren() {
    //    itemAddChildren.setOnAction(event -> sm.showNewDialog(new DialogAddChildrenController(), member.getMember(), FilesFXML.DIALOG_ADD_CHILDREN));
    }


}
