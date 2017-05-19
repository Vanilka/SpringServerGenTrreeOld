package com.genealogytree.client.desktop.controllers.implementation.custom.tree_elements;

import com.genealogytree.client.desktop.GenealogyTree;
import com.genealogytree.client.desktop.configuration.enums.ImageFiles;
import com.genealogytree.client.desktop.domain.GTX_Member;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.List;

/**
 * Created by vanilka on 03/01/2017.
 */
@Getter
@Setter
public class GTLeaf extends AnchorPane {

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

    private ObjectProperty<GTX_Member> member;
    private ChangeListener<Object> listener = ((obs, oldValue, newValue) -> fillComponents(member.get()));
    {
        member = new SimpleObjectProperty<>();

    }

    public GTLeaf(GTX_Member member) {
        super();
        initialize();
        this.member.addListener(getChangeMemberListener());
        this.member.setValue(member);
        resize(255,145);

    }

    public GTLeaf() {
        this(null);
    }

    private void initialize() {
        FXMLLoader fxmlLoader = new FXMLLoader(GenealogyTree.class.getResource("/layout/screen/custom-controls/GTLeaf.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
            fillComponents(null);
        } catch (IOException exception) {
            exception.printStackTrace();
            throw new RuntimeException(exception);
        }

        this.setOnMouseEntered(t -> {
        rectangleFond.setStroke(Color.valueOf("#64bf37"));
        });

        this.setOnMouseExited(t -> {

            rectangleFond.setStroke(Color.TRANSPARENT);
        });
    }

    private ChangeListener<GTX_Member> getChangeMemberListener() {
        ChangeListener<GTX_Member> simListener = (observable, oldValue, newValue) -> {
            if(oldValue != null) {
                oldValue.getProperties().forEach(p -> p.removeListener(listener));
            }
            newValue.getProperties().forEach(p -> p.addListener(listener));
            fillComponents(newValue);
        };
        return simListener;
    }

    private void fillComponents(GTX_Member member) {
        if (member == null) {
            nameSim.setText("???");
            surnameSim.setText("???");
            photoSim.setImage(new Image(ImageFiles.GENERIC_MALE.toString()));
        } else {
            nameSim.setText(member.getName());
            surnameSim.setText(member.getSurname());
            if (member.getBornname()!= null && !member.getBornname().equals("") && !member.getBornname().equals(member.getSurname())) {
                bornameSim.setText("("+member.getBornname()+")");
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

    public ObjectProperty<GTX_Member> memberProperty() {
        return member;
    }

    public GTX_Member getMember() {
        return member.getValue();
    }

    public void setMember(GTX_Member member) {
        this.member.set(member);
    }


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("GTLeaf{");
        sb.append(", surnameSim=").append(surnameSim);
        sb.append(", nameSim=").append(nameSim);
        sb.append(", photoSim=").append(photoSim);
        sb.append(", member=").append(member);
        sb.append(", memberProperty=").append(memberProperty());
        sb.append('}');
        return sb.toString();
    }
}
