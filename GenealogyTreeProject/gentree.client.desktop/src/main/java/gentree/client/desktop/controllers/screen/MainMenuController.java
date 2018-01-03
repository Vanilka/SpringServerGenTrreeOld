package gentree.client.desktop.controllers.screen;

import gentree.client.desktop.configuration.enums.AppLanguage;
import gentree.client.desktop.configuration.enums.FilesFXML;
import gentree.client.desktop.configuration.messages.Keys;
import gentree.client.desktop.configuration.messages.LogMessages;
import gentree.client.desktop.controllers.FXMLBorderPane;
import gentree.client.desktop.controllers.FXMLController;
import gentree.client.desktop.domain.Family;
import gentree.client.desktop.service.FamilyService;
import gentree.client.desktop.service.implementation.GenTreeLocalService;
import gentree.client.desktop.service.implementation.GenTreeOnlineService;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.util.Callback;
import lombok.extern.log4j.Log4j2;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by Martyna SZYMKOWIAK on 01/07/2017.
 */
@Log4j2
public class MainMenuController implements Initializable, FXMLController, FXMLBorderPane {

    private final SnapshotParameters snapshotParameters = new SnapshotParameters();

    @FXML
    private MenuBar MENU_BAR;

    @FXML
    private Menu MENU_FILE;

    @FXML
    private Menu MENU_PROJECT;

    @FXML
    private MenuItem MENU_ITEM_CONNECT_TO_SERVER;

    @FXML
    private MenuItem MENU_ITEM_CLOSE;

    @FXML
    private MenuItem MENU_ITEM_NEW_PROJECT;

    @FXML
    private MenuItem MENU_ITEM_OPEN_PROJECT;

    @FXML
    private MenuItem MENU_ITEM_SAVE_PROJECT_AS;

    @FXML
    private MenuItem MENU_ITEM_EXPORT_IMAGE;

    @FXML
    private MenuItem MENU_ITEM_CLOSE_PROJECT;

    @FXML
    private SeparatorMenuItem SEPARATOR_1;

    @FXML
    private SeparatorMenuItem SEPARATOR_2;

    @FXML
    private SeparatorMenuItem SEPARATOR_3;

    @FXML
    private ComboBox<AppLanguage> languageChooser;

    @FXML
    private ObjectProperty<ResourceBundle> languageBundle = new SimpleObjectProperty<>();
    private ChangeListener<? super AppLanguage> languageSelectedListener = this::languageSelectedChange;
    private ChangeListener<? super ResourceBundle> languageListener = this::languageChange;
    private ChangeListener<? super Family> familyListener = this::familyChange;
    private ChangeListener<? super FamilyService> serviceListener = this::serviceChanged;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        log.trace(LogMessages.MSG_CTRL_INITIALIZATION);
        this.languageBundle.setValue(resources);
        initMenuItemsVisibility();
        initFamilyServiceListener();
        this.setCellFactoryToComboBox();
        this.languageChooser.setItems(FXCollections.observableArrayList(AppLanguage.values()));
        this.languageChooser.getSelectionModel()
                .select(AppLanguage.valueOf(languageBundle.getValue().getString(Keys.LANGUAGE)));
        this.languageBundle.bind(context.getBundle());
        addLanguageListener();

        log.trace(LogMessages.MSG_CTRL_INITIALIZED);
    }




    /*
           LISTENERS
     */

    private void initMenuItemsVisibility() {
        MENU_ITEM_SAVE_PROJECT_AS.setVisible(false);
        SEPARATOR_2.setVisible(false);
        SEPARATOR_3.setVisible(false);
    }

    private void initFamilyServiceListener() {
        context.serviceProperty().addListener(serviceListener);
    }

    private void cleanListeners() {
        this.languageChooser.getSelectionModel().selectedItemProperty().removeListener(languageSelectedListener);
        this.languageBundle.removeListener(languageListener);
        context.serviceProperty().removeListener(serviceListener);
        languageBundle.unbind();
        context.getService().familyProperty().removeListener(familyListener);
    }

    private void serviceChanged(ObservableValue<? extends FamilyService> obsContext, FamilyService oldContext, FamilyService newContext) {
        if (newContext != null) {
            newContext.familyProperty().addListener(familyListener);
            if(newContext instanceof GenTreeOnlineService) {
                MENU_ITEM_SAVE_PROJECT_AS.setVisible(false);
            } else {
                MENU_ITEM_SAVE_PROJECT_AS.setVisible(true);
            }

        } else {
            changeElementsVisibility(false);

        }

        if (oldContext != null) {
            oldContext.familyProperty().removeListener(familyListener);
        }
    }

    private void familyChange(ObservableValue<? extends Family> obsFamily, Family oldFamily, Family newFamily) {
        if (newFamily == null) {
            changeElementsVisibility(false);
        } else {
            changeElementsVisibility(true);
        }
    }

    private void changeElementsVisibility(boolean value) {
        MENU_ITEM_SAVE_PROJECT_AS.setVisible(value);
        SEPARATOR_2.setVisible(value);
        SEPARATOR_3.setVisible(value);
    }


    /*
     * LISTEN LANGUAGE CHANGES
     */
    private void addLanguageListener() {
        this.languageChooser.getSelectionModel().selectedItemProperty().addListener(languageSelectedListener);
        this.languageBundle.addListener(languageListener);
    }

    private void languageSelectedChange(ObservableValue<? extends AppLanguage> observable, AppLanguage oldValue, AppLanguage newValue) {
        context.setLocale(new Locale(newValue.toString(), newValue.getCountry()));
        context.setBundle(context.getLocale());
    }

    private void languageChange(ObservableValue<? extends ResourceBundle> observable, ResourceBundle oldValue, ResourceBundle newValue) {
        reloadElements();
    }


    private String getValueFromKey(String key) {
        return this.languageBundle.getValue().getString(key);
    }

    private void reloadElements() {
        this.MENU_FILE.setText(getValueFromKey(Keys.MENU_PROGRAM));
        this.MENU_PROJECT.setText(getValueFromKey(Keys.MENU_PROJECT));
        this.MENU_ITEM_CLOSE.setText(getValueFromKey(Keys.MENU_PROGRAM_CLOSE));
        this.MENU_ITEM_CLOSE_PROJECT.setText(getValueFromKey(Keys.MENU_PROJECT_CLOSE));
        this.MENU_ITEM_NEW_PROJECT.setText(getValueFromKey(Keys.MENU_PROJECT_NEW));
        this.MENU_ITEM_OPEN_PROJECT.setText(getValueFromKey(Keys.MENU_PROJECT_OPEN));
        this.MENU_ITEM_SAVE_PROJECT_AS.setText(getValueFromKey(Keys.MENU_PROJECT_SAVE_AS));
    }

    /*
        MENU BUTTON ACTION
     */
    @FXML
    public void openProperties(ActionEvent actionEvent) {

        sm.showNewDialog(new DialogAppPropertiesController(), FilesFXML.DIALOG_APP_PROPERTIES);
    }


    @FXML
    public void closeApplication() {
        System.exit(0);
    }

    @FXML
    public void generateImage() throws IOException {

        WritableImage image = sm.getScreenMainRightController().Image();
        File file = new File("./GenTree" + LocalDateTime.now() + ".png");
        ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);

    }

    @FXML
    public void saveToXML() {
        if(context.getService() instanceof GenTreeLocalService)
        ((GenTreeLocalService) context.getService()).saveProject();
    }


    @FXML
    public void openFromXML() {

/*        File file = sc.openXMLFileChooser();
        if (file != null) {
            try {
                JAXBContext jaxbContext = JAXBContext.newInstance(GTX_Family.class);
                Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                GTX_Family customer = (GTX_Family) jaxbUnmarshaller.unmarshal(file);
                context.getService().setCurrentFamily(customer);
                sc.loadFxml(new PaneMainApplicationWindowController(), sc.getRootBorderPane(), FXMLFile.MAIN_APPLICATION_WINDOW, ScreenManager.Where.CENTER);
            } catch (JAXBException e) {
                e.printStackTrace();
            }
        }*/
    }

    @FXML
    public void closeProject() {
        sm.reloadScreenWelcomeController();
    }


    /*
        CELL FACTORY
     */

    private void setCellFactoryToComboBox() {
        class LanguageCell extends ListCell<AppLanguage> {
            {
                super.setPrefHeight(40);
                super.setPrefWidth(150);
            }

            @Override
            public void updateItem(AppLanguage item, boolean empty) {
                super.updateItem(item, empty);
                {
                    if (item != null) {
                        ImageView imv = new ImageView(item.getBadge());
                        imv.setFitWidth(30);
                        imv.setFitHeight(30);
                        setGraphic(imv);
                        setText(item.toString().toUpperCase());

                    } else {
                        setText("empty");
                    }
                }
            }
        }

        class LanguageCellFactory implements Callback<ListView<AppLanguage>, ListCell<AppLanguage>> {
            @Override
            public ListCell<AppLanguage> call(ListView<AppLanguage> param) {
                return new LanguageCell();
            }
        }

        this.languageChooser.setCellFactory(new LanguageCellFactory());
        this.languageChooser.setButtonCell(new LanguageCell());
    }
}
