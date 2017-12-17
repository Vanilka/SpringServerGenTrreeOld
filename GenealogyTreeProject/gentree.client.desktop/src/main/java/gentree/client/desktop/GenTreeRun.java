package gentree.client.desktop;

import gentree.client.desktop.configuration.GenTreeDefaultProperties;
import gentree.client.desktop.service.GenTreeContext;
import gentree.client.desktop.service.ScreenManager;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Created by Martyna SZYMKOWIAK on 01/07/2017.
 */

public class GenTreeRun extends Application {

    private static ScreenManager sc = ScreenManager.INSTANCE;
    private static GenTreeContext gtc = GenTreeContext.INSTANCE;
    private static GenTreeDefaultProperties def = GenTreeDefaultProperties.INSTANCE;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        sc.setStage(stage);
        sc.init();
    }

}

