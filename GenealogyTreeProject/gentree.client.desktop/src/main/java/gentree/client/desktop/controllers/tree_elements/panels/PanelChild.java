package gentree.client.desktop.controllers.tree_elements.panels;

import gentree.client.desktop.domain.Member;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Martyna SZYMKOWIAK on 20/07/2017.
 *
 * Panel Child extends BorderPane
 * Left: Hbox of PanelsEx
 * Center: Panel Single
 * Right: Panel Current
 */
@Getter
@Setter
public class PanelChild extends SubBorderPane {

    private final PanelSingle panelSingle;
    private final PanelRelationCurrent panelRelationCurrent;


    {
        panelSingle = new PanelSingle();
        panelRelationCurrent = new PanelRelationCurrent();
    }

    public PanelChild(Member m) {
        setCenter(panelSingle);
        setLeft(panelRelationCurrent);
        viewInit(panelSingle, Color.DARKBLUE);
        viewInit(panelRelationCurrent, Color.DARKKHAKI);
        panelSingle.addMember(m);
    }


    private void viewInit(BorderPane pane, Color color) {
        pane.setBorder(new Border(new BorderStroke(color,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
    }

    private void viewInit(StackPane pane) {
        pane.setBorder(new Border(new BorderStroke(Color.DARKCYAN,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
    }


}
