package genealogytree.application.fxmlcontrollers;

import genealogytree.application.FXMLTabController;
import genealogytree.application.GenealogyTreeContext;
import genealogytree.application.ScreenManager;
import genealogytree.configuration.ImageFiles;
import genealogytree.domain.GTX_Member;
import genealogytree.domain.enums.Age;
import genealogytree.domain.enums.Sex;
import genealogytree.services.responses.MemberResponse;
import genealogytree.services.responses.ServerResponse;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by vanilka on 22/11/2016.
 */
public class TabAddNewMemberPaneController implements Initializable, FXMLTabController {

    private static final Logger LOG = LogManager.getLogger(TabAddNewMemberPaneController.class);

    private ScreenManager manager;
    private GenealogyTreeContext context;

    @FXML
    private AnchorPane addNewMember;

    @FXML
    private JFXTextField simNameField;

    @FXML
    private JFXTextField simSurnameField;

    @FXML
    private JFXRadioButton simAgeBabyField;

    @FXML
    private JFXRadioButton simAgeChildField;

    @FXML
    private JFXRadioButton simAgeAdoField;

    @FXML
    private JFXRadioButton simAgeJAdultField;

    @FXML
    private JFXRadioButton simAgeAdultField;

    @FXML
    private JFXRadioButton simAgeSeniorField;

    @FXML
    private JFXRadioButton simSexMale;

    @FXML
    private JFXRadioButton simSexFemale;

    @FXML
    private JFXButton addSimConfirmButton;

    @FXML
    private JFXButton addSimCancelButton;

    @FXML
    private ImageView simPhoto;

    @FXML
    private ObjectProperty<ResourceBundle> languageBundle = new SimpleObjectProperty<>();

    private ToggleGroup toogleGroupAge;
    private ToggleGroup toogleGroupeSex;

    private Tab tab;
    private JFXTabPane tabPane;

    private String path;

    /**
     * Initializes the controller class.
     */

    {
        toogleGroupAge = new ToggleGroup();
        toogleGroupeSex = new ToggleGroup();
        path = null;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setInfoLog("Initialisation:  " + this.toString());

        this.languageBundle.setValue(rb);
        addSexListener();
        createAgeToogleGroupe();
        createSexToogleGroupe();
    }

    @FXML
    public void addSimConfirm() {
        GTX_Member member = new GTX_Member(this.simNameField.getText(), this.simSurnameField.getText(), (Age) toogleGroupAge.getSelectedToggle().getUserData(), (Sex) toogleGroupeSex.getSelectedToggle().getUserData(), path);

        ServerResponse response = this.context.getFamilyService().addNewMember(member);
        if (response instanceof MemberResponse) {
            this.tabPane.getTabs().remove(tab);
        } else {

        }
    }


    private void addSexListener() {
        toogleGroupeSex.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if (path == null) {
                    if (newValue.getUserData().equals(Sex.MALE)) {
                        simPhoto.setImage(new Image(ImageFiles.GENERIC_MALE.toString()));
                    } else {
                        simPhoto.setImage(new Image(ImageFiles.GENERIC_FEMALE.toString()));
                    }
                }
            }
        });
    }

    @FXML
    void setImage(MouseEvent event) {
        if (event.getClickCount() == 2) {
            File file = this.manager.openImageFileChooser();
            if (file != null) {
                try {
                    path = file.getAbsolutePath();
                    BufferedImage bufferedImage = ImageIO.read(file);
                    Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                    this.simPhoto.setImage(image);
                    System.out.println("Image Location : " + file.getPath());
                    System.out.println("Image Location A : " + file.getAbsolutePath());


                } catch (Exception e) {

                }
            }
        }
    }


    public void setAddSimCancel() {
        this.tabPane.getTabs().remove(tab);
    }

    private void createAgeToogleGroupe() {
        this.simAgeBabyField.setToggleGroup(this.toogleGroupAge);
        this.simAgeBabyField.setUserData(Age.BABY);

        this.simAgeChildField.setToggleGroup(this.toogleGroupAge);
        this.simAgeChildField.setUserData(Age.CHILD);

        this.simAgeAdoField.setToggleGroup(this.toogleGroupAge);
        this.simAgeAdoField.setUserData(Age.ADO);

        this.simAgeJAdultField.setToggleGroup(this.toogleGroupAge);
        this.simAgeJAdultField.setUserData(Age.YOUNG_ADULT);

        this.simAgeAdultField.setToggleGroup(this.toogleGroupAge);
        this.simAgeAdultField.setUserData(Age.ADULT);

        this.simAgeSeniorField.setToggleGroup(this.toogleGroupAge);
        this.simAgeSeniorField.setUserData(Age.SENIOR);

        this.toogleGroupAge.selectToggle(simAgeJAdultField);

    }

    private void createSexToogleGroupe() {
        this.simSexMale.setToggleGroup(this.toogleGroupeSex);
        this.simSexMale.setUserData(Sex.MALE);
        this.simSexFemale.setToggleGroup(this.toogleGroupeSex);
        this.simSexFemale.setUserData(Sex.FEMALE);

        this.toogleGroupeSex.selectToggle(simSexMale);
    }

    public Tab getTab() {
        return tab;
    }

    public void setTab(Tab tab) {
        this.tab = tab;
    }

    public JFXTabPane getTabPane() {
        return tabPane;
    }

    public void setTabPane(JFXTabPane tabPane) {
        this.tabPane = tabPane;
    }

    public void setTabAndTPane(JFXTabPane tabPane, Tab tab) {
        this.tabPane = tabPane;
        this.tab = tab;
    }


    /*
     * LISTEN LANGUAGE CHANGES
     */
    private void addLanguageListener() {
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

        this.simNameField.setPromptText(getValueFromKey("simName"));
        this.simSurnameField.setPromptText(getValueFromKey("simSurname"));

        this.simAgeBabyField.setText(getValueFromKey("simAgeBaby"));
        this.simAgeChildField.setText(getValueFromKey("simAgeChild"));
        this.simAgeAdoField.setText(getValueFromKey("simAgeAdo"));
        this.simAgeJAdultField.setText(getValueFromKey("simAgeJAdult"));
        this.simAgeAdultField.setText(getValueFromKey("simAgeAdult"));
        this.simAgeSeniorField.setText(getValueFromKey("simAgeSenior"));

        this.addSimConfirmButton.setText(getValueFromKey("button_confirm"));
        this.addSimCancelButton.setText(getValueFromKey("addSimCancel"));

        this.simSexFemale.setText(getValueFromKey("simSexF"));
        this.simSexMale.setText(getValueFromKey("simSexM"));
    }

    /*
     * GETTERS AND SETTERS
     */
    @Override
    public void setContext(GenealogyTreeContext context) {
        this.context = context;
        this.languageBundle.bind(context.getBundleProperty());
        addLanguageListener();
    }

    @Override
    public void setManager(ScreenManager manager) {
        this.manager = manager;
    }

    private void setInfoLog(String msg) {
        msg = this.getClass().getSimpleName() + ": " + msg;
        LOG.info(msg);
        System.out.println("INFO:  " + msg);
    }

    private void setErrorLog(String msg) {
        msg = this.getClass().getSimpleName() + ": " + msg;
        LOG.error(msg);
        System.out.println("ERROR:  " + msg);
    }
}
