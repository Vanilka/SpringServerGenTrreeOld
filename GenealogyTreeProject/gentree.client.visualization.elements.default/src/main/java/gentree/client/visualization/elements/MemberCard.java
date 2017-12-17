package gentree.client.visualization.elements;

import gentree.client.desktop.domain.Member;
import gentree.client.visualization.elements.configuration.ImageFiles;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

import java.io.IOException;

/**
 * Created by Martyna SZYMKOWIAK on 24/07/2017.
 */
public class MemberCard extends AnchorPane {

    @FXML
    private AnchorPane leafAnchorPane;

    @FXML
    private Label surnameSim;

    @FXML
    private Label nameSim;

    @FXML
    private Label bornameSim;

    @FXML
    private ImageView photoSim;

    @FXML
    private Rectangle rectangleFond;


    private ObjectProperty<Member> member;
    private ChangeListener<Object> listener = this::objectChange;

    private ChangeListener<? super Member> memberListener = this::simChanged;

    {
        member = new SimpleObjectProperty<>();

    }


    public MemberCard(Member member) {
        super();
        initialize();
        this.member.addListener(memberListener);
        this.member.setValue(member);
        resize(255, 145);

    }

    public MemberCard() {
        this(null);
    }

    private void initialize() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/layout/elements/member.card.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
            fillComponents(null);
        } catch (IOException exception) {
            exception.printStackTrace();
            throw new RuntimeException(exception);
        }
    }


    private void objectChange(ObservableValue<?> observableValue, Object oldValue, Object newValue) {
        fillComponents(member.get());
    }

    public void clean() {
        member.removeListener(memberListener);
        member.get().getProperties().forEach(p -> p.removeListener(listener));

        memberListener = null;
    }

    private void fillComponents(Member member) {
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
            setImage(member.getPhoto());
        }
    }

    public void setImage(String path) {
        try {
            photoSim.setImage(new Image(path));
        } catch (Exception e) {
            e.printStackTrace();
            photoSim.setImage(new Image(ImageFiles.NO_NAME_MALE.toString()));
        }
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

    private MemberCard returnThis() {
        return this;
    }

    private void simChanged(ObservableValue<? extends Member> observable, Member oldValue, Member newValue) {
        if (oldValue != null) {
            oldValue.getProperties().forEach(p -> p.removeListener(listener));
        }
        if (newValue != null) {
            newValue.getProperties().forEach(p -> p.addListener(listener));
        }
        fillComponents(newValue);
    }
}

