package com.genealogytree.client.desktop;

import com.genealogytree.client.desktop.configuration.ContextGT;
import com.genealogytree.client.desktop.configuration.ScreenManager;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Created by Martyna SZYMKOWIAK on 19/03/2017.
 */
public class GenealogyTree extends Application {

    private static ScreenManager sc = ScreenManager.getInstance();
    private static ContextGT context = ContextGT.getInstance();
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        sc.setStage(stage);
        sc.init();
    }
}
