package com.genealogytree.client.desktop.controllers.implementation.custom;

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
import javafx.scene.layout.AnchorPane;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

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
    private ImageView photoSim;

    private ObjectProperty<GTX_Member> member;

    {
        member = new SimpleObjectProperty<>();

    }

    public GTLeaf(GTX_Member member) {
        super();
        initialize();
        this.member.addListener(getChangeMemberListener());
        this.member.setValue(member);
        setHeight(150.0);
        setWidth(130.0);

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
    }

    private ChangeListener<GTX_Member> getChangeMemberListener() {
        ChangeListener<GTX_Member> simListener = (observable, oldValue, newValue) -> {
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
