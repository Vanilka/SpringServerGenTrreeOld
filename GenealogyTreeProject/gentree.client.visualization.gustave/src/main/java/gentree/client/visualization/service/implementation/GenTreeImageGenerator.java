package gentree.client.visualization.service.implementation;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Created by Martyna SZYMKOWIAK on 02/11/2017.
 */
public class GenTreeImageGenerator {

    public static final GenTreeImageGenerator INSTANCE = new GenTreeImageGenerator();

    private final SnapshotParameters snapshotParameters = new SnapshotParameters();

    /*
        PANES
     */
    private BorderPane imagePane = new BorderPane();
    private AnchorPane headerPane = new AnchorPane();
    private AnchorPane content = new AnchorPane();
    private Label title = new Label("Family Name");

    private GenTreeImageGenerator() {
        initHeader();
        initParameters();
    }

    private void generateImagePane() {
        imagePane = null;
        imagePane = new BorderPane();
        imagePane.setTop(headerPane);
        imagePane.setCenter(content);
        imagePane.autosize();
        imagePane.setStyle(
                "-fx-background-image: url(" +
                        "'layout/images/backgrounds/gentreebackground.jpg');" +
                        "-fx-background-size: cover;"
        );
    }

    private void initParameters() {
        snapshotParameters.setFill(Color.TRANSPARENT);
    }

    private void initHeader() {
        headerPane.getChildren().add(this.title);
        AnchorPane.setTopAnchor(this.title, 10.0);
        AnchorPane.setBottomAnchor(this.title, 10.0);
        AnchorPane.setLeftAnchor(this.title, 50.0);
        AnchorPane.setRightAnchor(this.title, 50.0);
        this.title.setFont(Font.font("Georgia", FontWeight.EXTRA_BOLD, 36));
        headerPane.setPrefHeight(70);

    }

    public WritableImage doScreen(Node nodeContent, String text) {
        this.title.setText(text);
        content.getChildren().clear();
        content.getChildren().add(new ImageView(nodeContent.snapshot(snapshotParameters, null)));
        generateImagePane();
        Scene scene = new Scene(imagePane);
        return scene.snapshot(null);
    }
}
