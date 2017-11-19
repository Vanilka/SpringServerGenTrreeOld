package gentree.client.desktop.service;

import gentree.client.desktop.domain.Relation;
import gentree.common.configuration.enums.RelationType;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.util.Objects;
import java.util.Observable;
import java.util.Observer;

/**
 * This is guard t
 * Created by Martyna SZYMKOWIAK on 14/05/2017.
 */
public class ActiveRelationGuard implements Observer {

    private final ObservableList<Relation> relations;
    private final ListChangeListener<? super Relation> relationListListener = this::relationListChange;
    GenTreeContext context = GenTreeContext.INSTANCE;
    ScreenManager sm = ScreenManager.INSTANCE;


    public ActiveRelationGuard(ObservableList<Relation> relations) {
        this.relations = relations;
        relations.addListener(relationListListener);
        runCheckWithUpdate();
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof Relation) {
            Relation relation = (Relation) o;

            /*
                After change an relation type to Active,  auto-change others to inActive
             */
            runUpdate(relation);
            /*
                Redraw tree
             */
            sm.getGenTreeDrawingService().startDraw();
        }
    }


    private void runUpdate(Relation relation) {
        if (relation.getLeft() != null && relation.getRight() != null && !relation.getType().equals(RelationType.NEUTRAL) && relation.getActive()) {
            relations
                    .filtered(r -> !Objects.equals(r, relation))
                    .filtered(r -> r.getLeft() != null)
                    .filtered(r -> r.getRight() != null)
                    .filtered(r -> r.getType() != RelationType.NEUTRAL)
                    .filtered(r -> (r.getRight().equals(relation.getRight()) || r.getLeft().equals(relation.getLeft())))
                    .filtered(r -> !r.equals(relation))
                    .forEach(r -> r.setActive(false));
        }
    }

    private void runCheckWithUpdate() {
        relations.forEach(this::runUpdate);
    }

    public void addObserverTo(Observable observable) {
        observable.addObserver(this);
    }

    public void removeObserverFrom(Observable observable) {
        observable.deleteObserver(this);
    }

    private void relationListChange(ListChangeListener.Change<? extends Relation> c) {
        while (c.next()) {
            if (c.wasAdded()) {
                c.getAddedSubList().forEach(relation -> {
                    this.addObserverTo(relation);
                    this.runUpdate(relation);
                });
            }
        }
    }

    public void clean() {
        relations.removeListener(relationListListener);
        relations.forEach(this::removeObserverFrom);
    }
}

