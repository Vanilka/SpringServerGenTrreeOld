package com.genealogytree.application;

import javafx.stage.Stage;

/**
 * Created by vanilka on 19/11/2016.
 */
public interface FXMLDialogController {
    public void setManager(ScreenManager manager);

    public void setContext(GenealogyTreeContext context);

    public void setStage(Stage stage);
}
