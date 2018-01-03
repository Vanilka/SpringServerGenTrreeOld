package gentree.client.visualization.elements;

import gentree.client.desktop.domain.Member;
import gentree.client.visualization.controls.CircleEmbleme;
import gentree.client.visualization.elements.configuration.AutoCleanable;
import gentree.client.visualization.elements.configuration.ContextProvider;
import gentree.client.visualization.elements.configuration.ElementsConfig;
import gentree.common.configuration.enums.Age;
import gentree.common.configuration.enums.Gender;
import gentree.common.configuration.enums.Race;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**
 * Created by Martyna SZYMKOWIAK on 20/07/2017.
 */
public class FamilyMember extends FamilyMemberCard implements AutoCleanable {

    private final static int MEMBER_WIDTH = 140;
    private final static int MEMBER_HEIGHT = 220;
    private final static ElementsConfig ec = ElementsConfig.INSTANCE;
    private final static String pathfxml = "/layout/elements/family.member.fxml";
    private static ContextProvider CONTEXT_PROVIDER_PROPERTY;
    private ChangeListener<? super ContextProvider> contextListener = this::contextChanged;


    @FXML
    private CircleEmbleme GENDER_IMG;

    @FXML
    private CircleEmbleme RACE_IMG;

    @FXML
    private CircleEmbleme AGE_IMG;


    public FamilyMember(Member member) {
        super(member, pathfxml);
        initialize();
        resize(MEMBER_WIDTH, MEMBER_HEIGHT);
    }

    public FamilyMember() {
        this(null);
    }

    public static void setContextProviderProperty(ContextProvider contextProviderProperty) {
        CONTEXT_PROVIDER_PROPERTY = contextProviderProperty;
    }

    private void initialize() {

        this.setOnMouseEntered(this::mouseEnterEvent);

        this.setOnMouseExited(this::mouseExitedEvent);

        if (CONTEXT_PROVIDER_PROPERTY != null) {
            this.setOnContextMenuRequested(this::handleContextMenuRequest);
            this.setOnMouseClicked(this::HandleMouseClicked);
        }
    }


    @Override
    protected void fillComponents(Member member) {
        super.fillComponents(member);
        if (member == null) {
            GENDER_IMG.setImgPath(ec.getFlePathOfGender(Gender.M));
            RACE_IMG.setImgPath(ec.getFilePathOfRace(Race.HUMAIN));
            AGE_IMG.setImgPath(ec.getFilePathOfAge(Age.YOUNG_ADULT));
        } else {
            GENDER_IMG.setImgPath(ec.getFlePathOfGender(member.getGender()));
            RACE_IMG.setImgPath(ec.getFilePathOfRace(member.getRace()));
            AGE_IMG.setImgPath(ec.getFilePathOfAge(member.getAge()));
        }
    }

    @Override
    public void clean() {
        super.clean();
       // CONTEXT_PROVIDER_PROPERTY.removeListener(contextListener);
        this.setOnContextMenuRequested(null);
        this.setOnMouseClicked(null);

        setMember(null);
        contextListener = null;

    }


    private FamilyMember returnThis() {
        return this;
    }

    private void contextChanged(ObservableValue<? extends ContextProvider> observable, ContextProvider oldValue, ContextProvider newValue) {
        if (newValue == null) {
            this.setOnContextMenuRequested(null);
            this.setOnMouseClicked(null);
        } else {
            this.setOnContextMenuRequested(event -> newValue.showSimContextMenu(returnThis(), event));
            this.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && getMember() != null) {
                    newValue.showInfoSim(getMember());
                }
            });
        }
    }

    private void mouseEnterEvent(MouseEvent t) {
        setStrokeColor(Color.valueOf("#64bf37"));
    }

    private void mouseExitedEvent(MouseEvent t) {
        setStrokeColor(Color.WHITE);
    }

    private void setStrokeColor(Color color) {
        rectangleFond.setStroke(color);
        rectanglePhotoFond.setStroke(color);
    }

    private void HandleMouseClicked(MouseEvent event) {
        if (event.getClickCount() == 2 && getMember() != null) {
            CONTEXT_PROVIDER_PROPERTY.showInfoSim(getMember());
        }
    }

    private void handleContextMenuRequest(ContextMenuEvent event) {
        CONTEXT_PROVIDER_PROPERTY.showSimContextMenu(returnThis(), event);
    }
}
