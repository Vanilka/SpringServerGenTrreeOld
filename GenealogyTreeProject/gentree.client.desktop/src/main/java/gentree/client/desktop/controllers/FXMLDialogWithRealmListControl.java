package gentree.client.desktop.controllers;

import gentree.client.desktop.configuration.Realm;
import javafx.collections.ObservableList;

/**
 * Created by Martyna SZYMKOWIAK on 22/10/2017.
 */
public interface FXMLDialogWithRealmListControl extends FXMLDialogController {

    void setList(ObservableList<Realm> list);
}
