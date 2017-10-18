package gentree.client.desktop.service;

import gentree.client.desktop.domain.Relation;
import gentree.client.desktop.domain.enums.RelationType;
import javafx.collections.ObservableList;

import java.util.Observable;
import java.util.Observer;

/**
 * This is guard t
 * Created by Martyna SZYMKOWIAK on 14/05/2017.
 */
public class ActiveRelationGuard implements Observer {

    private final ObservableList<Relation> relations;
    GenTreeContext context = GenTreeContext.INSTANCE;
    ScreenManager sm = ScreenManager.INSTANCE;


    public ActiveRelationGuard(ObservableList<Relation> relations) {
        this.relations = relations;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof Relation) {
            Relation relation = (Relation) o;

            /*
                After change an relation type to Active,  auto-change others to inActive
             */
            if (relation.getLeft() != null && relation.getRight() != null && relation.getActive()) {
                relations
                        .filtered(r -> r.getLeft() != null)
                        .filtered(r -> r.getRight() != null)
                        .filtered(r -> r.getType() != RelationType.NEUTRAL)
                        .filtered(r -> (r.getRight().equals(relation.getRight()) || r.getLeft().equals(relation.getLeft())))
                        .filtered(r -> !r.equals(relation))
                        .forEach(r -> r.setActive(false));
            }

            /*
                Redraw tree
             */
            System.out.println(sm.getGenTreeDrawingService() + " From guardian");
            sm.getGenTreeDrawingService().startDraw();
        }
    }

    public void addObserverTo(Observable observable) {
        observable.addObserver(this);
    }


}

