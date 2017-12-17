package gentree.client.desktop.service;

import javafx.scene.image.WritableImage;

/**
 * Created by Martyna SZYMKOWIAK on 05/07/2017.
 */
public interface GenTreeDrawingService {

    void startDraw();

    WritableImage takeScreenshot();
}
