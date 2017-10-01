package gentree.client.visualization.elements.configuration;

import gentree.client.visualization.elements.FamilyMember;
import gentree.client.visualization.elements.RelationTypeElement;
import javafx.scene.input.ContextMenuEvent;

public interface ContextProvider {

     void showRelationContextMenu(RelationTypeElement relationTypeElement, ContextMenuEvent event);

     void showSimContextMenu(FamilyMember familyMember, ContextMenuEvent event);
}
