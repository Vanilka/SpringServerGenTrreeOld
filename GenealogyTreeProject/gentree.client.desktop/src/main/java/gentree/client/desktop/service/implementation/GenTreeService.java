package gentree.client.desktop.service.implementation;

import gentree.client.desktop.configuration.GenTreeProperties;
import gentree.client.desktop.domain.Family;
import gentree.client.desktop.domain.Member;
import gentree.client.desktop.domain.Relation;
import gentree.client.desktop.service.ScreenManager;
import gentree.common.configuration.enums.Gender;
import gentree.common.configuration.enums.RelationType;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.commons.configuration2.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Martyna SZYMKOWIAK on 22/10/2017.
 */
public abstract class GenTreeService {

    protected final Configuration config = GenTreeProperties.INSTANCE.getConfiguration();
    protected ObjectProperty<Family> currentFamily = new SimpleObjectProperty<>();
    protected ScreenManager sm = ScreenManager.INSTANCE;


    public List<Member> findAllRootMembers() {
        List<Member> rootList = new ArrayList<>();

        getCurrentFamily().getRelations().stream()
                .filter(r -> r.getLeft() == null)
                .filter(r -> r.getRight() == null)
                .filter(r -> !r.getChildren().isEmpty())
                .forEach(root -> rootList.addAll(root.getChildren()));
        return rootList;
    }

    public Relation findRelation(Member left, Member right) {
        List<Relation> list = getCurrentFamily().getRelations()
                .stream()
                .filter(r -> r.getLeft() != null || r.getRight() != null)
                .filter(r -> r.compareLeft(left) && r.compareRight(right))
                .collect(Collectors.toList());
        System.out.println("Found similar relations : " + list.size());

        return list.size() == 0 ? null : list.get(0);

    }


    protected Relation findRelation(Member left, Member right, Relation excluded) {
        List<Relation> list = getCurrentFamily().getRelations()
                .stream()
                .filter(r -> r != excluded)
                .filter(r -> r.getLeft() != null || r.getRight() != null)
                .filter(r -> r.compareLeft(left) && r.compareRight(right))
                .collect(Collectors.toList());
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


    /**
     * Function to create relation from composant.
     * The role of this function is decide which member between 2 provided will be Left or Right
     *
     * @param m1
     * @param m2
     * @param type
     * @param active
     * @return
     */
    protected Relation createRelationFrom(Member m1, Member m2, RelationType type, boolean active) {
        Member left = null;
        Member right = null;

        if (m1 == null || m2 == null) {
            if (m1 != null) {
                if (m1.getGender() == Gender.F) left = m1;
                if (m1.getGender() == Gender.M) right = m1;
            } else if (m2 != null) {
                if (m2.getGender() == Gender.F) left = m2;
                if (m2.getGender() == Gender.M) right = m2;
            }
            type = RelationType.NEUTRAL;
        } else {

        /*
            Left is an user with higher ID
         */
            if (m1.getGender() == m2.getGender()) {
                left = m1.getId() > m2.getId() ? m1 : m2;
                right = m1.getId() < m2.getId() ? m1 : m2;
            } else {
                left = m1.getGender() == Gender.F ? m1 : m2;
                right = m1.getGender() == Gender.M ? m1 : m2;

            }
        }
        return new Relation(left, right, type, active);
    }

    public Family getCurrentFamily() {
        return currentFamily.get();
    }


}
