package com.genealogytree.client.desktop.configuration.helper;

import com.genealogytree.client.desktop.configuration.ContextGT;
import com.genealogytree.client.desktop.configuration.ScreenManager;
import com.genealogytree.client.desktop.domain.GTX_Relation;
import com.genealogytree.domain.enums.RelationType;
import javafx.collections.ObservableList;

import java.util.Observable;
import java.util.Observer;

/**
 * This is guard t
 * Created by Martyna SZYMKOWIAK on 14/05/2017.
 */
public class ActiveRelationGuard implements Observer {

    private final ObservableList<GTX_Relation> relations;
    ContextGT context = ContextGT.getInstance();
    ScreenManager sc = ScreenManager.getInstance();


    public ActiveRelationGuard(ObservableList<GTX_Relation> relations) {
        this.relations = relations;
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("UPDATE DETECTED");
        if (o instanceof GTX_Relation) {
            GTX_Relation relation = (GTX_Relation) o;

            /*
                After change an relation type to Active,  auto-change others to inActive
             */
            if (relation.getSimLeft() != null && relation.getSimRight() != null && relation.isActive()) {
                relations
                        .filtered(r -> r.getSimLeft() != null)
                        .filtered(r -> r.getSimRight() != null)
                        .filtered(r -> r.getType() != RelationType.NEUTRAL)
                        .filtered(r -> (r.getSimRight().equals(relation.getSimRight()) || r.getSimLeft().equals(relation.getSimLeft())))
                        .filtered(r -> !r.equals(relation))
                        .forEach(r -> r.setActive(false));
            }

            /*
                Redraw tree
             */
            sc.getPaneGenealogyTreeDrawController().redrawTree();
        }
    }

    public void addObserverTo(Observable observable) {
        observable.addObserver(this);
    }


}

