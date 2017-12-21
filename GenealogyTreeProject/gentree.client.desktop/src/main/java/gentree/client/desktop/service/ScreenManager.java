package gentree.client.desktop.service;

import com.jfoenix.controls.JFXTabPane;
import gentree.client.desktop.configuration.GenTreeProperties;
import gentree.client.desktop.configuration.Realm;
import gentree.client.desktop.configuration.enums.FilesFXML;
import gentree.client.desktop.configuration.helper.BorderPaneReloadHelper;
import gentree.client.desktop.configuration.messages.AppTitles;
import gentree.client.desktop.configuration.messages.LogMessages;
import gentree.client.desktop.configuration.wrappers.PhotoMarshaller;
import gentree.client.desktop.controllers.*;
import gentree.client.desktop.controllers.contextmenu.RelationContextMenu;
import gentree.client.desktop.controllers.contextmenu.SimContextMenu;
import gentree.client.desktop.controllers.screen.*;
import gentree.client.desktop.domain.Member;
import gentree.client.desktop.domain.Relation;
import gentree.client.visualization.elements.FamilyMember;
import gentree.client.visualization.elements.RelationReference;
import gentree.client.visualization.elements.RelationTypeElement;
import gentree.client.visualization.elements.configuration.ContextProvider;
import gentree.client.visualization.elements.configuration.ElementsConfig;
import gentree.client.visualization.elements.configuration.ImageFiles;
import gentree.client.visualization.service.implementation.GenTreeDrawingServiceImpl;
import gentree.common.configuration.enums.Age;
import gentree.common.configuration.enums.Gender;
import gentree.common.configuration.enums.Race;
import gentree.common.configuration.enums.RelationType;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Shape;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;
import javafx.util.Duration;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.controlsfx.control.Notifications;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by Martyna SZYMKOWIAK on 01/07/2017.
 */
@Getter
@Setter
@Log4j2
public class ScreenManager implements ContextProvider {

    public static final ScreenManager INSTANCE = new ScreenManager();
    public static final GenTreeContext context = GenTreeContext.INSTANCE;
    public static final GenTreeProperties properties = GenTreeProperties.INSTANCE;
    private static final String PREFIX_FILE_LOAD = "file://";
    private static final FileChooser imgFileChooser = new FileChooser();
    private static final FileChooser xmlFileChooser = new FileChooser();
    private static String LAST_PATH;
    BorderPaneReloadHelper bpHelper;

    /*
        Commons controllers
     */
    private ScreenMainController screenMainController;
    private MainMenuController mainMenuController;
    private MainFooterController mainFooterController;
    private ScreenWelcomeController screenWelcomeController;
    private RootWindowController rootWindowController;
    private GenTreeDrawingService genTreeDrawingService;
    /*
        Commons Panes
     */
    private ScreenMainRightController screenMainRightController;
    private BorderPane mainWindowBorderPane;
    private Stage stage;
    private Scene scene;


    /*
        Context Menus
     */

    private SimContextMenu simContextMenu;
    private RelationContextMenu relationContextMenu;


    private ScreenManager() {
        bpHelper = new BorderPaneReloadHelper();
        LAST_PATH = System.getProperty("user.home");
    }


    public void init() {
        configureStatics();
        initRoot();
        loadFxml(mainFooterController, this.mainWindowBorderPane, FilesFXML.MAIN_FOOTER_FXML, Where.BOTTOM);
        loadFxml(mainMenuController, this.mainWindowBorderPane, FilesFXML.MAIN_MENU_FXML, Where.TOP);
        loadFxml(screenWelcomeController, this.mainWindowBorderPane, FilesFXML.SCREEN_WELCOME_FXML, Where.CENTER);

        simContextMenu = new SimContextMenu();
        relationContextMenu = new RelationContextMenu();

        this.scene = new Scene(mainWindowBorderPane);
        this.stage.setScene(this.scene);
        this.stage.setTitle(AppTitles.APP_TITLE);
        this.stage.setHeight(750);
        this.stage.setWidth(1200);
        this.stage.setMinHeight(750);
        this.stage.setMinWidth(1200);
        this.stage.show();
    }


    /**
     * Configure Singletons
     */
    private void configureStatics() {
        FamilyMember.setContextProviderProperty(this);
        RelationReference.setContextProviderProperty(this);
        RelationTypeElement.setContextProviderProperty(this);
        PhotoMarshaller.addIgnoredPaths(ImageFiles.GENERIC_FEMALE.toString(),
                ImageFiles.GENERIC_FEMALE.toString());
        Member.setDefaultFemaleLocation(ImageFiles.GENERIC_FEMALE.toString());
        Member.setDefaultMaleLocation(ImageFiles.GENERIC_MALE.toString());
        GenTreeDrawingServiceImpl.setContext(context);
    }

    private void initRoot() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(FilesFXML.ROOT_WINDOW_FXML.toString()));
        try {
            this.mainWindowBorderPane = ((BorderPane) loader.load());
            this.rootWindowController = loader.getController();
        } catch (IOException ex) {
            log.error(ex.getMessage());
            log.error(ex.getCause());
        }
    }


    public void loadFxml(FXMLPane controller, BorderPane border, FilesFXML fxml, Where where) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml.toString()), context.getBundleValue());
        try {
            AnchorPane temp = (AnchorPane) loader.load();
            controller = loader.getController();
            switch (where) {
                case TOP:
                    border.setTop(temp);
                    break;
                case CENTER:
                    bpHelper.before(border);
                    border.setCenter(temp);
                    bpHelper.after(border);
                    break;
                case BOTTOM:
                    border.setBottom(temp);
                    break;
            }

        } catch (IOException ex) {
            log.error(ex.getMessage());
            log.error(ex.getCause());

        } catch (Exception ex) {
            log.error(ex.getMessage());
            log.error(ex.getCause());
            ex.printStackTrace();
        }

    }

    public void showNewDialog(FXMLDialogController controller, FilesFXML fxml) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml.toString()), context.getBundleValue());

        try {
            Stage dialogStage = new Stage();
            AnchorPane dialogwindow = (AnchorPane) loader.load();
            controller = loader.getController();
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(this.getStage());
            Scene scene = new Scene(dialogwindow);
            dialogStage.setScene(scene);
            dialogStage.setResizable(false);
            controller.setStage(dialogStage);
            dialogStage.showAndWait();


        } catch (Exception ex) {
            log.error(ex.getMessage());
            log.error(ex.getCause());
            ex.printStackTrace();
        }
    }


    public void showNewDialog(FXMLDialogWithRealmListControl controller, ObservableList<Realm> list, FilesFXML fxml) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml.toString()), context.getBundleValue());
        try {
            Stage dialogStage = new Stage();
            AnchorPane dialogwindow = (AnchorPane) loader.load();
            controller = loader.getController();
            controller.setList(list);
            Scene scene = new Scene(dialogwindow);
            initDialogProperties(dialogStage, Modality.WINDOW_MODAL, this.getStage(), scene, false);
            controller.setStage(dialogStage);
            dialogStage.showAndWait();


        } catch (Exception ex) {
            log.error(ex.getMessage());
            log.error(ex.getCause());
            ex.printStackTrace();
        }
    }


    public void showNewDialog(FXMLDialogWithMemberController controller, Member member, FilesFXML fxml) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml.toString()), context.getBundleValue());
        try {
            Stage dialogStage = new Stage();
            AnchorPane dialogwindow = (AnchorPane) loader.load();
            controller = loader.getController();
            controller.setFather(member);
            Scene scene = new Scene(dialogwindow);
            initDialogProperties(dialogStage, Modality.WINDOW_MODAL, this.getStage(), scene, false);
            controller.setStage(dialogStage);
            dialogStage.showAndWait();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            log.error(ex.getCause());
            ex.printStackTrace();
        }
    }


    public void showNewDialog(FXMLDialogWithRelationController controller, Relation r, FilesFXML fxml) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml.toString()), context.getBundleValue());
        try {
            Stage dialogStage = new Stage();
            AnchorPane dialogwindow = (AnchorPane) loader.load();
            controller = loader.getController();
            controller.setRelation(r);
            Scene scene = new Scene(dialogwindow);
            initDialogProperties(dialogStage, Modality.WINDOW_MODAL, this.getStage(), scene, false);
            controller.setStage(dialogStage);
            dialogStage.showAndWait();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            log.error(ex.getCause());
            ex.printStackTrace();
        }
    }

    public Member showNewDialog(FXMLDialogReturningMember controller, Member member, List<Member> list, FilesFXML fxml) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml.toString()), context.getBundleValue());
        try {
            Stage dialogStage = new Stage();
            AnchorPane dialogwindow = (AnchorPane) loader.load();
            controller = loader.getController();
            controller.setMember(member);
            controller.setMemberList(list);
            Scene scene = new Scene(dialogwindow);
            initDialogProperties(dialogStage, Modality.WINDOW_MODAL, this.getStage(), scene, false);
            controller.setStage(dialogStage);
            dialogStage.showAndWait();
            return controller.getResult();

        } catch (Exception ex) {
            log.error(ex.getMessage());
            log.error(ex.getCause());
            ex.printStackTrace();
        }
        return null;
    }

    private void initDialogProperties(Stage dialogStage, Modality modality, Window owner, Scene scene, boolean resizeable) {
        dialogStage.initModality(modality);
        dialogStage.initOwner(owner);
        dialogStage.setScene(scene);
        dialogStage.setResizable(resizeable);
    }

    public FXMLPane loadFxml(FXMLPane controller, Pane pane, FilesFXML fxml) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml.toString()), context.getBundleValue());
        try {
            pane.getChildren().clear();
            pane.getChildren().addAll((Pane) loader.load());
            controller = loader.getController();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            log.error(ex.getCause());
        }
        return controller;
    }


    public FXMLPane loadFxml(FXMLPane controller, AnchorPane anchor, FilesFXML fxml) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml.toString()), context.getBundleValue());
        try {
            anchor.getChildren().clear();
            anchor.getChildren().addAll((AnchorPane) loader.load());
            controller = loader.getController();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            log.error(ex.getCause());
            ex.printStackTrace();
        }
        return controller;
    }

    public FXMLTab loadFxml(FXMLTab controller, JFXTabPane tabPane, Tab tab, FilesFXML fxml, String title) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml.toString()), context.getBundleValue());
        try {
            tab.setContent(loader.load());
            tab.setText(title);
            controller = loader.getController();
            tabPane.getTabs().add(tab);
            tabPane.getSelectionModel().select(tab);
            controller.setTabAndTPane(tabPane, tab);

        } catch (IOException ex) {
            ex.printStackTrace();
            log.error(ex.getMessage());
            log.error(ex.getCause());


        } catch (Exception ex) {
            log.error(ex.getMessage());
            log.error(ex.getCause());
        }

        return controller;
    }

    public FXMLTab loadFxml(FXMLTab controller, JFXTabPane tabPane, Tab tab, String fxml) {

        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml), context.getBundleValue());
        try {
            tab.setContent(loader.load());
            controller = loader.getController();
            controller.setTabAndTPane(tabPane, tab);

        }  catch (Exception ex) {
            log.error(ex.getMessage());
            log.error(ex.getCause());
            ex.printStackTrace();
        }

        return controller;
    }

    public FXMLPane loadAdditionalFxmltoAnchorPane(FXMLPane controller, AnchorPane anchor, FilesFXML fxml) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml.toString()), context.getBundleValue());
        try {
            anchor.getChildren().addAll((AnchorPane) loader.load());
            controller = loader.getController();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            log.error(ex.getCause());
            ex.printStackTrace();
        }
        return controller;
    }

    public void showNotification(String title, String text) {
        Notifications notificationBuilder = Notifications.create()
                .title(title)
                .text(text)
                .graphic(null)
                .hideAfter(Duration.seconds(10))
                .darkStyle()
                .position(Pos.BOTTOM_RIGHT);
        notificationBuilder.show();
    }

    public String setImageIntoShape(Shape shape) {
        File file = openImageFileChooser();
        String path = "";
        if (file != null) {
            try {
                path = PREFIX_FILE_LOAD.concat(file.getCanonicalPath());
                shape.setFill(new ImagePattern(new Image(path)));
            } catch (Exception e) {
                log.error(LogMessages.MSG_ERROR_LOAD_IMAGE);
                e.printStackTrace();
                shape.setFill(new ImagePattern(new Image(ImageFiles.NO_NAME_MALE.toString())));
            }
        }
        return path;
    }


    public String setImageIntoImageView(ImageView imv) {
        File file = openImageFileChooser();
        String path = "";
        if (file != null) {
            try {
                path = PREFIX_FILE_LOAD.concat(file.getCanonicalPath());
                imv.setImage(new Image(path));
            } catch (Exception e) {
                log.error(LogMessages.MSG_ERROR_LOAD_IMAGE);
                e.printStackTrace();
                imv.setImage(new Image(ImageFiles.NO_NAME_MALE.toString()));
            }
        }
        return path;
    }


    public File openImageFileChooser() {
        configureImageFileChooser(imgFileChooser);
        File file = imgFileChooser.showOpenDialog(stage);
        LAST_PATH = file == null ? LAST_PATH : file.getParent();
        return file;

    }

    public File openXMLFileChooser() {
        configureXmlFileChooser(xmlFileChooser);
        File file = xmlFileChooser.showOpenDialog(stage);
        LAST_PATH = file == null ? LAST_PATH : file.getParent();
        return file;
    }

    public File saveXMLFileChooser() {
        configureXmlFileChooser(xmlFileChooser);
        File file = xmlFileChooser.showSaveDialog(stage);
        LAST_PATH = file == null ? LAST_PATH : file.getParent();
        return file;
    }

    private void configureImageFileChooser(final FileChooser fileChooser) {
        fileChooser.setTitle(context.getBundleValue().getString("select_image_dialog"));
        fileChooser.setInitialDirectory(
                new File(LAST_PATH)
        );
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
    }

    private void configureXmlFileChooser(final FileChooser fileChooser) {
        fileChooser.setTitle(context.getBundleValue().getString("select_xml_dialog"));
        fileChooser.setInitialDirectory(
                new File(LAST_PATH)
        );
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("XML", "*.xml")
        );
    }

    public void reloadScreenWelcomeController() {
        loadFxml(screenWelcomeController, this.mainWindowBorderPane, FilesFXML.SCREEN_WELCOME_FXML, Where.CENTER);
        context.setService(null);
    }

    @Override
    public void showInfoSim(Member member) {
        getScreenMainController().showInfoSim(member);
    }

    @Override
    public void showInfoRelation(Relation relation) {
        getScreenMainController().showInfoRelation(relation);
    }

    @Override
    public void showSimContextMenu(FamilyMember familyMember, ContextMenuEvent event) {

        simContextMenu.show(familyMember.getMember(), familyMember, event);
    }


    public void showSimContextMenu(Member m, Node node, ContextMenuEvent event) {

        simContextMenu.show(m, node, event);
    }

    @Override
    public void showRelationContextMenu(RelationTypeElement relationTypeElement, ContextMenuEvent event) {
        relationContextMenu.show(relationTypeElement, event);
    }

    /*
            SHOW WARNINGS
     */

    public void showError(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /*
        Register Screen
     */

    public void register(ScreenMainRightController controller) {
        this.screenMainRightController = controller;
    }

    public void register(ScreenMainController controller) {
        this.screenMainController = controller;
    }

    public Callback<TableColumn.CellDataFeatures<Member, String>, ObservableValue<String>> getPhotoValueFactory() {
        Callback<TableColumn.CellDataFeatures<Member, String>, ObservableValue<String>> callback = param -> new ReadOnlyObjectWrapper<>(param.getValue().getPhoto());
        return callback;
    }


    /*
    Cell Factory
    */

    public Callback<ListView<RelationType>, ListCell<RelationType>> getCustomRelationListCell() {
        int relationWidth = 50;
        int relationHeight = 50;

        Callback<ListView<RelationType>, ListCell<RelationType>> callback = new Callback<ListView<RelationType>, ListCell<RelationType>>() {
            @Override
            public ListCell<RelationType> call(ListView<RelationType> param) {
                final ListCell<RelationType> relationCell = new ListCell<RelationType>() {
                    @Override
                    public void updateItem(RelationType item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            switch (item) {
                                case NEUTRAL:
                                    setGraphic(setGraphicToImageView(ImageFiles.RELATION_NEUTRAL.toString(), relationWidth, relationHeight));
                                    setText("");
                                    break;
                                case LOVE:
                                    setGraphic(setGraphicToImageView(ImageFiles.RELATION_LOVE.toString(), relationWidth, relationHeight));
                                    setText("");
                                    break;
                                case FIANCE:
                                    setGraphic(setGraphicToImageView(ImageFiles.RELATION_FIANCE.toString(), relationWidth, relationHeight));
                                    setText("");
                                    break;
                                case MARRIED:
                                    setGraphic(setGraphicToImageView(ImageFiles.RELATION_MARRIED.toString(), relationWidth, relationHeight));
                                    setText("");
                                    break;
                                default:
                                    setGraphic(setGraphicToImageView(ImageFiles.RELATION_NEUTRAL.toString(), relationWidth, relationHeight));
                                    setText("");
                            }
                        } else {
                            setGraphic(setGraphicToImageView(ImageFiles.RELATION_NEUTRAL.toString(), relationWidth, relationHeight));
                            setText("");
                        }
                    }

                };
                return relationCell;
            }
        };
        return callback;
    }

    public Callback<ListView<Realm>, ListCell<Realm>> getCustomRealmListCell() {
        Callback<ListView<Realm>, ListCell<Realm>> callback = new Callback<ListView<Realm>, ListCell<Realm>>() {

            @Override
            public ListCell<Realm> call(ListView<Realm> param) {
                final ListCell<Realm> realmCell = new ListCell<Realm>() {
                    @Override
                    protected void updateItem(Realm item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item.getName());
                        } else {
                            setText("");
                        }
                    }
                };
                return realmCell;
            }
        };
        return callback;
    }


    public Callback<ListView<Race>, ListCell<Race>> getRaceListCell() {
        Callback<ListView<Race>, ListCell<Race>> callback = new Callback<ListView<Race>, ListCell<Race>>() {
            @Override
            public ListCell<Race> call(ListView<Race> param) {
                final ListCell<Race> raceCell = new ListCell<Race>() {
                    @Override
                    public void updateItem(Race item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setGraphic(setGraphicToImageView(ElementsConfig.INSTANCE.getFilePathOfRace(item), 40, 40));
                            setText("");
                        } else {
                            setGraphic(setGraphicToImageView(ImageFiles.HUMAIN.toString(), 40, 40));
                            setText("");
                        }
                    }

                };
                return raceCell;
            }
        };
        return callback;

    }

    public Callback<ListView<Age>, ListCell<Age>> getAgeListCell() {
        Callback<ListView<Age>, ListCell<Age>> callback = new Callback<ListView<Age>, ListCell<Age>>() {
            @Override
            public ListCell<Age> call(ListView<Age> param) {
                final ListCell<Age> ageCell = new ListCell<Age>() {
                    @Override
                    public void updateItem(Age item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setGraphic(setGraphicToImageView(ElementsConfig.INSTANCE.getFilePathOfAge(item), 30, 40));
                            setText("");
                        } else {
                            setGraphic(setGraphicToImageView(ImageFiles.HUMAIN.toString(), 30, 40));
                            setText("");
                        }
                    }

                };
                return ageCell;
            }
        };
        return callback;

    }

    public Callback<ListView<Gender>, ListCell<Gender>> getGenderListCell() {
        Callback<ListView<Gender>, ListCell<Gender>> callback = new Callback<ListView<Gender>, ListCell<Gender>>() {
            @Override
            public ListCell<Gender> call(ListView<Gender> param) {
                final ListCell<Gender> genderCell = new ListCell<Gender>() {
                    @Override
                    public void updateItem(Gender item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setGraphic(setGraphicToImageView(ElementsConfig.INSTANCE.getFlePathOfGender(item), 30, 30));
                            setText("");
                        } else {
                            setGraphic(setGraphicToImageView(ImageFiles.HUMAIN.toString(), 30, 30));
                            setText("");
                        }
                    }

                };
                return genderCell;
            }
        };
        return callback;

    }

    private ImageView setGraphicToImageView(String path, int width, int height) {
        ImageView imv = new ImageView(path);
        imv.setFitWidth(width);
        imv.setFitHeight(height);
        return imv;
    }

    public void register(GenTreeDrawingService drawingService) {
        this.genTreeDrawingService = drawingService;
    }


    public enum Where {
        TOP,
        CENTER,
        BOTTOM
    }

}
