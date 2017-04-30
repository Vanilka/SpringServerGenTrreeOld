package com.genealogytree.client.desktop.service.implementation;

import com.genealogytree.client.desktop.configuration.ContextGT;
import com.genealogytree.client.desktop.controllers.implementation.custom.*;
import com.genealogytree.client.desktop.domain.GTX_Member;
import com.genealogytree.client.desktop.domain.GTX_Relation;
import com.genealogytree.client.desktop.service.GenTreeDrawingService;
import com.genealogytree.domain.enums.Sex;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

/**
 * Created by Martyna SZYMKOWIAK on 31/03/2017.
 */
public class GenTreeDrawingServiceImpl implements GenTreeDrawingService {

    ContextGT context = ContextGT.getInstance();

    public GenTreeDrawingServiceImpl() {

    }

    @Override
    public void startDraw(GTNode node) {


        for (GTX_Member m : node.getRacine().getChildren()) {
            GTPanelChild panel = new GTPanelChild(new GTLeaf(m));
            populatePanel(panel);
            node.getContentHbox().getChildren().add(panel);
        }
    }

    public void populatePanel(GTPanelChild panel) {

        //TODO exception if leaf is null
        // Find relations where Sim is Mother or Father
        System.out.println(panel.getLeaf().getMember());
        ObservableList<GTX_Relation> relationList = context.getService().getCurrentFamily().getRelationsList()
                .filtered(r -> ((r.getSimLeft() != null && r.getSimLeft().equals(panel.getLeaf().getMember()))
                        || (r.getSimRight() != null && r.getSimRight().equals(panel.getLeaf().getMember()))));
        //Transform List of GTX_Relation to GTPanelSim
        ObservableList<GTPanelSim> panelSimObservableList = FXCollections.observableArrayList();

        for (GTX_Relation r : relationList) {
            GTPanelSim simPanel = toPanelSim(r, panel.getLeaf().getMember());
            if (isStrong(r, panel.getLeaf().getMember())) {
                for (GTX_Member child : r.getChildren()) {
                    GTPanelChild childPanel = new GTPanelChild(new GTLeaf(child));
                    populatePanel(childPanel);
                    simPanel.addPanelChild(childPanel);
                }
            }
            panelSimObservableList.add(simPanel);
        }
        panel.getPanels().addAll(panelSimObservableList);

    }


    private GTPanelSim toPanelSim(GTX_Relation relation, GTX_Member member) {
        GTPanelSim simPanel = new GTPanelSim();

        GTX_Member simLeft = relation.getSimLeft();
        GTX_Member simRight = relation.getSimRight();

        if (simLeft != null && simLeft.equals(member)) {
            setLeafAndSpouse(simPanel, simLeft, simRight);
        } else {
            setLeafAndSpouse(simPanel, simRight, simLeft);
        }

        simPanel.getRelationType().setType(relation.getType());
        return simPanel;
    }

    private void setLeafAndSpouse(GTPanelSim panel, GTX_Member leaf, GTX_Member spouse) {
        panel.setLeaf(new GTLeaf(leaf));
        if (spouse != null) {
            panel.setSpouse(new GTLeaf(spouse));
        }
    }



    public void test(GTX_Relation racine, AnchorPane AnchorB) {
        for (GTX_Member m : racine.getChildren()) {
            GTLeaf leaf1 = new GTLeaf(racine.getSimLeft());

            AnchorPane ap = new AnchorPane();
            ap.getChildren().add(leaf1);
            ap.setBorder(new Border(new BorderStroke(Color.BLACK,
                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            leaf1.setLayoutX(50);
            leaf1.setLayoutY(50);

            GTLeaf leaf2 = new GTLeaf(racine.getSimRight());
            leaf2.setLayoutX(300);
            leaf2.setLayoutY(50);

            AnchorPane ap2 = new AnchorPane();
            ap2.getChildren().add(leaf2);
            ap2.setLayoutX(400);
            ap2.setBorder(new Border(new BorderStroke(Color.BLACK,
                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            GTConnectorSpouse connectorSpouse = new GTConnectorSpouse(leaf1, leaf2);
            GTConnectorChildren connectorChildren = new GTConnectorChildren(leaf1, leaf2);
            AnchorB.getChildren().addAll(ap, ap2, connectorSpouse, connectorChildren);

        }
    }

    private void bpTestInit(BorderPane pane) {
        pane.resize(300, 300);
        pane.setBorder(new Border(new BorderStroke(Color.RED,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
    }

    private boolean isStrong(GTX_Relation r, GTX_Member bean)  {

        if (!((r.getSimLeft() != null && r.getSimLeft().equals(bean))
                || r.getSimRight() != null && r.getSimRight().equals(bean))) {

            return false;
        } else {
            if (bean.getSex().equals(Sex.FEMALE)) {
                if (r.getSimRight() != null && r.getSimRight().getSex().equals(Sex.MALE) && r.isActive()) {
                    return false;
                }
            }
        }
        return true;
    }
}
