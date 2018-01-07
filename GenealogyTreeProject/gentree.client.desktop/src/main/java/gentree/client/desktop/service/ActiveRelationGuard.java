package gentree.client.desktop.service;

import gentree.client.desktop.configuration.messages.LogMessages;
import gentree.client.desktop.domain.Relation;
import gentree.common.configuration.enums.RelationType;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import lombok.extern.log4j.Log4j2;

import java.util.Objects;
import java.util.Observable;
import java.util.Observer;

/**
 * This is guard for relations
 * Created by Martyna SZYMKOWIAK on 14/05/2017.
 */
@Log4j2
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
            LogMessages.printHeader(log, LogMessages.MSG_LABEL_UPDATE_GUARD);
            log.info(LogMessages.MSG_RELATION_ADDED_CAUSE, relation);
            log.info(LogMessages.DELIMITER_MIDDLE);
            relations.stream()
                    .filter(r -> !Objects.equals(r, relation))
                    .filter(r -> r.getLeft() != null)
                    .filter(r -> r.getRight() != null)
                    .filter(r -> r.getType() != RelationType.NEUTRAL)
                    .filter(r -> (r.getRight().equals(relation.getRight()) || r.getLeft().equals(relation.getLeft())))
                    .filter(r -> !r.equals(relation))
                    .forEach(r ->  {
                        r.setActive(false);
                        log.info(LogMessages.MSG_RELATION_UPDATE, r.getId());
                    });
            LogMessages.printFooter(log, LogMessages.MSG_LABEL_FIN_UPDATE_GUARD);
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
        if(!relations.isEmpty()) {
            LogMessages.printHeader(log, LogMessages.MSG_LABEL_CLEAN_GUARD);
            relations.removeListener(relationListListener);

            relations.forEach(relation -> {
                removeObserverFrom(relation);
                log.info(LogMessages.MSF_RELATION_ID_CLEAN);
            });

            LogMessages.printFooter(log, LogMessages.MSG_LABEL_FIN_CLEAN_GUARD);
        }
    }
}

