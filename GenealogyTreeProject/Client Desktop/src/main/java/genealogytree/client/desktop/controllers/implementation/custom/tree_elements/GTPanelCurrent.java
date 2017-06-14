package genealogytree.client.desktop.controllers.implementation.custom.tree_elements;

import genealogytree.client.desktop.controllers.implementation.custom.GTPanel;
import genealogytree.client.desktop.controllers.implementation.custom.GTPanelSim;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Martyna SZYMKOWIAK on 14/05/2017.
 */
@Getter
@Setter
public class GTPanelCurrent extends GTPanelCommon implements GTPanelSim {

    protected GTPanel parentPanel;

    @Override
    public void setGTPanelChild(GTPanelChild panelChild) {

    }

    @Override
    public void setGTPanelChildList(ObservableList<GTPanelChild> panelChildList) {

    }

    public ReadOnlyObjectProperty<GTLeaf> returnLeaf() {
        return null;
    }




}
