package gentree.client.desktop.controllers.screen;

import com.jfoenix.controls.JFXTabPane;
import gentree.client.desktop.configuration.GenTreeProperties;
import gentree.client.desktop.configuration.enums.PropertiesKeys;
import gentree.client.desktop.configuration.messages.LogMessages;
import gentree.client.desktop.controllers.FXMLController;
import gentree.client.desktop.controllers.FXMLTab;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.util.Callback;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.configuration2.Configuration;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

/**
 * Created by Martyna SZYMKOWIAK on 02/07/2017.
 */
@Getter
@Setter
@Log4j2
public class TabOpenExistingProjectController implements Initializable, FXMLController, FXMLTab {

    private Configuration config = GenTreeProperties.INSTANCE.getConfiguration();

    @FXML
    private ObjectProperty<ResourceBundle> languageBundle = new SimpleObjectProperty<>();

    @FXML
    private ComboBox<Path> PROJECT_CHOICE_COMBOBOX;


    private Tab tab;
    private JFXTabPane tabPane;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        log.trace(LogMessages.MSG_CTRL_INITIALIZATION);
        languageBundle.setValue(resources);
        setCellFactoryToProjectComboBox();
        populateComboBox();

        log.trace(LogMessages.MSG_CTRL_INITIALIZED);

    }

    @Override
    public void setTabAndTPane(JFXTabPane tabPane, Tab tab) {
        this.tabPane = tabPane;
        this.tab = tab;
    }


    private void populateComboBox() {
        ObservableList<Path> list = FXCollections.observableArrayList();
        try {
            Files.newDirectoryStream(
                    Paths.get(config.getString(PropertiesKeys.PARAM_DIR_PROJECT_NAME)),
                    path -> (path.toFile().isFile() && path.toFile().getName().contains(".xml")))
                    .forEach((list::add));
        } catch (IOException e) {
            e.printStackTrace();
        }
        PROJECT_CHOICE_COMBOBOX.setItems(list);
    }

    private void setCellFactoryToProjectComboBox() {
        PROJECT_CHOICE_COMBOBOX.setCellFactory(getCustomPathListCallback());
        PROJECT_CHOICE_COMBOBOX.setButtonCell(getCustomPathListCallback().call(null));

    }


    private Callback<ListView<Path>, ListCell<Path>> getCustomPathListCallback() {

        Callback<ListView<Path>, ListCell<Path>> callback = new Callback<ListView<Path>, ListCell<Path>>() {
            @Override
            public ListCell<Path> call(ListView<Path> param) {
                final ListCell<Path> pathCell = new ListCell<Path>() {
                    @Override
                    public void updateItem(Path item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item.toFile().getName());
                        }
                    }

                };
                return pathCell;
            }
        };
        return callback;
    }

    public Path getSelectedFile() {
        return PROJECT_CHOICE_COMBOBOX.getSelectionModel().getSelectedItem();
    }


}
