package gentree.client.visualization.elements;

import gentree.client.desktop.domain.Member;
import gentree.client.visualization.elements.configuration.AutoCleanable;
import gentree.client.visualization.elements.configuration.ImageFiles;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.io.IOException;

/**
 * Created by Martyna SZYMKOWIAK on 20/07/2017.
 */
public class FamilyMemberCard extends AnchorPane implements AutoCleanable {

    private final static String pathfxml = "/layout/elements/family.member.card.fxml";

    private final static int MEMBER_WIDTH = 133;
    private final static int MEMBER_HEIGHT = 188;

    @FXML
    protected ImageView photoSim;
    @FXML
    protected Rectangle rectangleFond;
    @FXML
    protected Rectangle rectanglePhotoFond;

    private Rectangle clip;

    @FXML
    private AnchorPane leafAnchorPane;
    @FXML
    private Label surnameSim;
    @FXML
    private Label nameSim;
    @FXML
    private Label bornameSim;
    @FXML
    private Polygon deathCord;


    private ObjectProperty<Member> member;
    private ChangeListener<Object> listener = this::objectChange;

    private ChangeListener<? super Member> memberListener = this::memberChange;

    {
        member = new SimpleObjectProperty<>();
        clip = new Rectangle();
        //deathCord = initPolygon();

    }

    public FamilyMemberCard(Member member) {
        this(member, pathfxml);
    }

    public FamilyMemberCard(Member member, String path) {
        super();
        initialize(path);
        this.member.addListener(memberListener);
        this.member.setValue(member);
        resize(MEMBER_WIDTH, MEMBER_HEIGHT);
    }

    public FamilyMemberCard() {
        this(null);
    }

    protected static void fillShapeWithBackgroundd(Shape shape, String imgPath) {
        Image img = new Image(imgPath);
        shape.setFill(new ImagePattern(img));

    }

    private Polygon initPolygon() {
        return new Polygon(
                0.0, 40.0,
                00, 60.0,
                60.0, 0.0,
                40.0, 0.0);
    }

    private void initialize(String path) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(path));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
            fillComponents(null);
        } catch (IOException exception) {
            exception.printStackTrace();
            throw new RuntimeException(exception);
        }
        initClip();
    }

    protected void fillComponents(Member member) {
        if (member == null) {
            nameSim.setText("???");
            surnameSim.setText("???");
            photoSim.setImage(new Image(ImageFiles.GENERIC_MALE.toString()));
        } else {
            nameSim.setText(member.getName());
            surnameSim.setText(member.getSurname());
            if (member.getBornname() != null && !member.getBornname().equals("") && !member.getBornname().equals(member.getSurname())) {
                bornameSim.setText("(" + member.getBornname() + ")");
            } else {
                bornameSim.setText("");
            }
            setImage(member.getPhoto(), !member.isAlive());
            deathCord.setVisible(!member.isAlive());
        }
    }

    public void setImage(String path, Boolean shouldBeInGayScale) {
        try {
            photoSim.setImage(new Image(path));
            if (shouldBeInGayScale) {
                setGrayScaleToImgView(photoSim);
            } else {
                photoSim.setEffect(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            photoSim.setImage(new Image(ImageFiles.NO_NAME_MALE.toString()));
        }

/*        Rectangle clip = new Rectangle(
                photoSim.getFitWidth(), photoSim.getFitHeight()
        );*/


    }

    @Override
    public void clean() {
        this.member.removeListener(memberListener);
        this.member.get().getProperties().forEach(p -> p.removeListener(listener));
        this.member.setValue(null);
        photoSim.setImage(null);
        memberListener = null;
    }

    private void initClip() {
        clip.widthProperty().bind(photoSim.fitWidthProperty());
        clip.heightProperty().bind(photoSim.fitHeightProperty());
        clip.setArcWidth(200);
        clip.setArcHeight(200);
        photoSim.setClip(clip);
    }

    public void setGrayScaleToImgView(ImageView view) {
        ColorAdjust desaturate = new ColorAdjust();
        desaturate.setSaturation(-1);
        view.setEffect(desaturate);
    }

    public ObjectProperty<Member> memberProperty() {
        return member;
    }

    public Member getMember() {
        return member.getValue();
    }

    public void setMember(Member member) {
        this.member.set(member);
    }

    private FamilyMemberCard returnThis() {
        return this;
    }

    private void memberChange(ObservableValue<? extends Member> observable, Member oldValue, Member newValue) {
        if (oldValue != null) {
            oldValue.getProperties().forEach(p -> p.removeListener(listener));
        }
        newValue.getProperties().forEach(p -> p.addListener(listener));

        fillComponents(newValue);
    }

    private void objectChange(ObservableValue<?> obs, Object oldValue, Object newValue) {
        fillComponents(member.get());
    }
}
