package gentree.client.visualization.controls.skin;

import gentree.client.visualization.controls.HeaderPane;
import gentree.client.visualization.elements.configuration.AutoCleanable;
import javafx.beans.InvalidationListener;
import javafx.scene.control.Label;
import javafx.scene.control.SkinBase;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import lombok.Getter;

/**
 * Created by vanilka on 08/10/2017.
 */
public class HeaderPaneSkin extends SkinBase<HeaderPane> implements AutoCleanable {

    private final Color default_color = Color.web("#0188AE");

    @Getter
    private final AnchorPane backgroundPane;
    private final Label title;
    private boolean invalidate = false;
    private final InvalidationListener invalidListener = observable -> invalidate = true;

    {
        backgroundPane = new AnchorPane();
        title = new Label();
    }

    public HeaderPaneSkin(HeaderPane control) {
        super(control);
        initialize();

        getSkinnable().prefWidthProperty().bind(backgroundPane.widthProperty());
        getSkinnable().prefHeightProperty().bind(backgroundPane.heightProperty());

        getSkinnable().widthProperty().addListener(invalidListener);
        getSkinnable().heightProperty().addListener(invalidListener);

        AnchorPane.setRightAnchor(title, 20.0);
        AnchorPane.setLeftAnchor(title, 20.0);

    }

    private void initialize() {
        getChildren().add(backgroundPane);
        backgroundPane.getChildren().addAll(title);
        title.textProperty().bind(getSkinnable().titleProperty());
    }

    private void initDefaultSkin() {

    }

    protected void initBorder(Color color, Pane node) {
        node.setBorder(new Border
                (new BorderStroke(color,
                        BorderStrokeStyle.SOLID,
                        CornerRadii.EMPTY,
                        BorderWidths.DEFAULT)));
    }

    @Override
    public void clean() {
        getSkinnable().widthProperty().removeListener(invalidListener);
        getSkinnable().heightProperty().removeListener(invalidListener);

        getSkinnable().prefWidthProperty().unbind();
        getSkinnable().prefHeightProperty().unbind();
    }
}
