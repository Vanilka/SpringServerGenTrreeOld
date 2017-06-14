/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.genealogytree;

import com.genealogytree.application.GenealogyTreeContext;
import com.genealogytree.application.ScreenManager;
import javafx.application.Application;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author vanilka
 */
public class GenealogyTree extends Application {

    private static final Logger LOG = LogManager.getLogger(GenealogyTree.class);

    private final ScreenManager screenManager;
    private final GenealogyTreeContext context;

    {
        LOG.info("Start Application: " + this.toString());
        this.screenManager = new ScreenManager();
        this.context = new GenealogyTreeContext();
        screenManager.setContext(this.context);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        screenManager.setStage(stage);
        screenManager.startApplication();
    }

}