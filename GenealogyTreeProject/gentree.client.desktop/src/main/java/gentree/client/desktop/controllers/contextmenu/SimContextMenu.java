package gentree.client.desktop.controllers.contextmenu;

import gentree.client.desktop.configuration.enums.FilesFXML;
import gentree.client.desktop.controllers.screen.DialogAddParentsToMemberController;
import gentree.client.desktop.controllers.screen.DialogAddSpouseController;
import gentree.client.desktop.domain.Member;
import gentree.client.desktop.service.GenTreeContext;
import gentree.client.desktop.service.ScreenManager;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.ContextMenuEvent;

/**
 * Created by Martyna SZYMKOWIAK on 20/07/2017.
 */
public class SimContextMenu extends ContextMenu {

    GenTreeContext context = GenTreeContext.INSTANCE;
    ScreenManager sm = ScreenManager.INSTANCE;
    Member member;
    private MenuItem itemAddParents = new MenuItem("AddParents");
    private MenuItem itemAddSiblings = new MenuItem("AddSpouse");
    private MenuItem itemAddChildren = new MenuItem("AddChildren");
    private MenuItem itemDelete = new MenuItem("Delete");


    public SimContextMenu() {
        super();
        initItems();
        this.getItems().addAll(itemAddParents, itemAddSiblings, itemAddChildren, itemDelete);
    }

    public void show(Member m, Node node, ContextMenuEvent event) {
        member = m;
        show(node, event.getScreenX(), event.getScreenY());

    }

    private void initItems() {
        initItemAddParents();
        initItemAddSpouse();
        initItemDelete();
    }

    private void initItemAddParents() {

        itemAddParents.setOnAction(event -> sm.showNewDialog(new DialogAddParentsToMemberController(), member, FilesFXML.DIALOG_ADD_PARENTS_TO_MEMBER));
    }

    private void initItemAddSpouse() {
        itemAddSiblings.setOnAction(event -> sm.showNewDialog(new DialogAddSpouseController(), member, FilesFXML.DIALOG_ADD_SPOUSE_TO_MEMBER));
    }

    private void initItemAddChildren() {
        //    itemAddChildren.setOnAction(event -> sm.showNewDialog(new DialogAddChildrenController(), member.getMember(), FilesFXML.DIALOG_ADD_CHILDREN));
    }

    private void initItemDelete() {
        itemDelete.setOnAction(event -> context.getService().deleteMember(member));
    }

}
