package gentree.client.desktop.service.implementation;

import gentree.client.desktop.configuration.GenTreeProperties;
import gentree.client.desktop.domain.Family;
import gentree.client.desktop.domain.Member;
import gentree.client.desktop.domain.Relation;
import gentree.client.desktop.service.ScreenManager;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.commons.configuration2.Configuration;

import java.util.List;

/**
 * Created by Martyna SZYMKOWIAK on 22/10/2017.
 */
public abstract class GenTreeService {

    protected final Configuration config = GenTreeProperties.INSTANCE.getConfiguration();
    protected ObjectProperty<Family> currentFamily = new SimpleObjectProperty<>();
    protected ScreenManager sm = ScreenManager.INSTANCE;



    public ObservableList<Member> findAllRootMembers() {
        ObservableList<Member> rootList = FXCollections.observableArrayList();

        getCurrentFamily().getRelations()
                .filtered(r -> r.getLeft() == null)
                .filtered(r -> r.getRight() == null)
                .filtered(r -> !r.getChildren().isEmpty())
                .forEach(root -> rootList.addAll(root.getChildren()));
        return rootList;
    }

    public Relation findRelation(Member left, Member right) {
        List<Relation> list = getCurrentFamily().getRelations()
                .filtered(r -> r.getLeft() != null || r.getRight() != null)
                .filtered(r -> r.compareLeft(left) && r.compareRight(right));
        return list.size() == 0 ? null : list.get(0);

    }

    /**
     * Verify if parameter sim is Ascendant of parameter grain
     *
     * @param grain
     * @param sim
     * @return
     */
    public boolean isAscOf(Member grain, Member sim) {
        return grain != null && sim != null && getCurrentFamily().isAscOf(grain, sim);
    }


    /**
     * Verify if parameter sim is Descendant of parameter grain
     *
     * @param grain
     * @param sim
     * @return
     */
    public boolean isDescOf(Member grain, Member sim) {
        return grain != null && sim != null && getCurrentFamily().isDescOf(grain, sim);
    }


    public Family getCurrentFamily() {
        return currentFamily.get();
    }

}
