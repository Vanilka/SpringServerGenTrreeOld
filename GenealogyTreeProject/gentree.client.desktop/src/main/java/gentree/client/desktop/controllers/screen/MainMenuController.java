package gentree.client.desktop.controllers.screen;

import gentree.client.desktop.configurations.enums.AppLanguage;
import gentree.client.desktop.configurations.messages.Keys;
import gentree.client.desktop.configurations.messages.LogMessages;
import gentree.client.desktop.controllers.FXMLBorderPane;
import gentree.client.desktop.controllers.FXMLController;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.util.Callback;
import lombok.extern.log4j.Log4j2;

import javax.imageio.ImageIO;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
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
    @FXML
    private MenuBar mainBar;

    @FXML
    private Menu menuFile;

    @FXML
    private Menu menuProject;

    @FXML
    private MenuItem menuItemConnectToServer;

    @FXML
    private MenuItem menuItemClose;

    @FXML
    private MenuItem menuItemNewProject;

    @FXML
    private MenuItem menuItemOpenProject;

    @FXML
    private MenuItem menuItemSaveProjectAs;

    @FXML
    private MenuItem menuItemSaveProject;

    @FXML
    private MenuItem menuItemExportImage;

    @FXML
    private MenuItem menuItemCloseProject;

    @FXML
    private ComboBox<AppLanguage> languageChooser;

    @FXML
    private ObjectProperty<ResourceBundle> languageBundle = new SimpleObjectProperty<>();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        log.trace(LogMessages.MSG_CTRL_INITIALIZATION);
        this.languageBundle.setValue(resources);

        this.setCellFactoryToComboBox();

        this.languageChooser.setItems(FXCollections.observableArrayList(AppLanguage.values()));
        this.languageChooser.getSelectionModel()
                .select(AppLanguage.valueOf(languageBundle.getValue().getString(Keys.LANGUAGE)));
        this.languageBundle.bind(context.getBundle());
        addLanguageListener();

        log.trace(LogMessages.MSG_CTRL_INITIALIZED);
    }

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
                // TODO Auto-generated method stub
                return new LanguageCell();
            }
        }

        this.languageChooser.setCellFactory(new LanguageCellFactory());
        this.languageChooser.setButtonCell(new LanguageCell());
    }


    /*
    * LISTEN LANGUAGE CHANGES
    */
    private void addLanguageListener() {

        this.languageChooser.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<AppLanguage>() {
            @Override
            public void changed(ObservableValue<? extends AppLanguage> observable, AppLanguage oldValue,
                                AppLanguage newValue) {
                context.setLocale(new Locale(newValue.toString(), newValue.getCountry()));
                context.setBundle(context.getLocale());
            }
        });

        this.languageBundle.addListener(new ChangeListener<ResourceBundle>() {
            @Override
            public void changed(ObservableValue<? extends ResourceBundle> observable, ResourceBundle oldValue,
                                ResourceBundle newValue) {
                reloadElements();
            }
        });
    }


    private String getValueFromKey(String key) {
        return this.languageBundle.getValue().getString(key);
    }

    private void reloadElements() {
        this.menuFile.setText(getValueFromKey(Keys.MENU_PROGRAM));
        this.menuProject.setText(getValueFromKey(Keys.MENU_PROJECT));
        this.menuItemClose.setText(getValueFromKey(Keys.MENU_PROGRAM_CLOSE));
        this.menuItemCloseProject.setText(getValueFromKey(Keys.MENU_PROJECT_CLOSE));
        this.menuItemNewProject.setText(getValueFromKey(Keys.MENU_PROJECT_NEW));
        this.menuItemOpenProject.setText(getValueFromKey(Keys.MENU_PROJECT_OPEN));
        this.menuItemSaveProject.setText(getValueFromKey(Keys.MENU_PROJECT_SAVE));
        this.menuItemSaveProjectAs.setText(getValueFromKey(Keys.MENU_PROJECT_SAVE_AS));
        this.menuItemSaveProjectAs.setText(getValueFromKey(Keys.MENU_PROJECT_EXPORT));
    }

    /*
        MENU BUTTON ACTION
     */
    @FXML
    public void closeApplication() {
        System.exit(0);
    }

    @FXML
    public void generateImage() throws IOException, AWTException {

       /* WritableImage image = sm.getPaneGenealogyTreeDrawController().Image();
        File file = new File("./screen/GenTree" + LocalDateTime.now() + ".png");
        ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);*/

    }

    @FXML
    public void saveToXML() {
       /* File file = sc.saveXMLFileChooser();
        if (file != null) {
            try {

                JAXBContext jaxbContext = JAXBContext.newInstance(GTX_Family.class);
                Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

                // output pretty printed
                jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                jaxbMarshaller.marshal(context.getService().getCurrentFamily(), file);

            } catch (JAXBException e) {
                e.printStackTrace();
            }
        }*/
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

}
