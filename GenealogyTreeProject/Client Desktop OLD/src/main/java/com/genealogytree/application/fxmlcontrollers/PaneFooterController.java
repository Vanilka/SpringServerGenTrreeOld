/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genealogytree.application.fxmlcontrollers;

import genealogytree.application.FXMLPaneController;
import genealogytree.application.GenealogyTreeContext;
import genealogytree.application.ScreenManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author vanilka
 */
public class PaneFooterController implements Initializable, FXMLPaneController {

    private static final Logger LOG = LogManager.getLogger(PaneFooterController.class);
    private ScreenManager manager;
    private GenealogyTreeContext context;

    @FXML
    private Label footerCopyright;

    @FXML
    private ResourceBundle languageBundle;

	/*
     * Initializes the controller class.
	 */

    private void setCopyrightText(String s) {
        this.footerCopyright.setText(s);
    }

    @Override
    public void setManager(ScreenManager manager) {
        this.manager = manager;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        LOG.info("Initialisation :  " + this.toString());
        this.languageBundle = rb;
        setCopyrightText("Martyna SZYMKOWIAK 2016");
    }

    @Override
    public void setContext(GenealogyTreeContext context) {
        this.context = context;
    }

    private void setInfoLog(String msg) {
        msg = this.getClass().getSimpleName() + ": " + msg;
        LOG.info(msg);
        System.out.println("INFO:  " + msg);
    }

    private void setErrorLog(String msg) {
        msg = this.getClass().getSimpleName() + ": " + msg;
        LOG.error(msg);
        System.out.println("ERROR:  " + msg);
    }
}
