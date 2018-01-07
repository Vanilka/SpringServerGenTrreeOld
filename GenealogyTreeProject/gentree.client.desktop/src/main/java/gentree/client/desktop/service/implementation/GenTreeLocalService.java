package gentree.client.desktop.service.implementation;


import gentree.client.desktop.configuration.ErrorMessages;
import gentree.client.desktop.configuration.enums.PropertiesKeys;
import gentree.client.desktop.configuration.messages.LogMessages;
import gentree.client.desktop.domain.Family;
import gentree.client.desktop.domain.Member;
import gentree.client.desktop.domain.Relation;
import gentree.client.desktop.responses.ServiceResponse;
import gentree.client.desktop.service.ActiveRelationGuard;
import gentree.client.desktop.service.FamilyService;
import gentree.client.desktop.service.responses.FamilyResponse;
import gentree.client.desktop.service.responses.MemberResponse;
import gentree.client.desktop.service.responses.RelationResponse;
import gentree.common.configuration.enums.RelationType;
import gentree.exception.NotUniqueBornRelationException;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import lombok.extern.log4j.Log4j2;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Martyna SZYMKOWIAK on 01/07/2017.
 */
@Log4j2
public class GenTreeLocalService extends GenTreeService implements FamilyService {


    private ActiveRelationGuard guard;
    private ProjectsLocalFilesService ps = new ProjectsLocalFilesService();

    // private String projectFilename;

    private Long idMember;
    private Long idRelation;
    private ChangeListener<? super Family> familyListener = this::familyChanged;
    private ListChangeListener<? super Member> membersListListener = this::memberListChange;
    private ListChangeListener<? super Relation> relationListListener = this::relationListChange;

    /*
        Functions
     */

    {
        currentFamily = new SimpleObjectProperty<>();
        idMember = 0L;
        idRelation = 0L;
    }


    public GenTreeLocalService() {
        setCurrentFamilyListener();
    }


    /**
     * Function to adding Member to Family
     *
     * @param member
     * @return
     */
    @Override
    public ServiceResponse addMember(Member member) {
        log.trace(LogMessages.MSG_PARAMETER_PASSED_TO_FUNCTION, Thread.currentThread().getStackTrace()[1].getMethodName(), member);
        if (!ps.isGenericPhoto(member.getPhoto())) {
            String newPhotoPath = ps.copyPhoto(member.getPhoto());
            member.setPhoto(newPhotoPath == null ? null : ps.generateNewPathForImage(newPhotoPath));
        }
        getCurrentFamily().addMember(member);
        return new MemberResponse(member);
    }

    @Override
    public ServiceResponse updateMember(Member m) {
        // Nothing to do in Local service
        log.trace(LogMessages.MSG_PARAMETER_PASSED_TO_FUNCTION, Thread.currentThread().getStackTrace()[1].getMethodName(), m);
        if (ps.needCopy(m.getPhoto())) {
            String coppied = ps.copyPhoto(m.getPhoto());
            m.setPhoto(coppied == null ? null : ps.generateNewPathForImage(coppied));
        }
        return new MemberResponse(m);
    }

    @Override
    public ServiceResponse deleteMember(Member m) {
        log.trace(LogMessages.MSG_PARAMETER_PASSED_TO_FUNCTION, Thread.currentThread().getStackTrace()[1].getMethodName(), m);
        this.getCurrentFamily().getMembers().remove(m);
        return new MemberResponse(m);
    }

    /**
     * Function to adding Relation beetween Members to Family
     *
     * @param relation
     * @return
     */
    @Override
    public ServiceResponse addRelation(Relation relation) {
        log.trace(LogMessages.MSG_PARAMETER_PASSED_TO_FUNCTION, Thread.currentThread().getStackTrace()[1].getMethodName(), relation);
        if (relation.getLeft() == null && relation.getRight() == null) {
            this.getCurrentFamily().addRelation(relation);
        } else {
            Relation exist = findRelation(relation.getLeft(), relation.getRight());
            if (exist == null) {
                getCurrentFamily().addRelation(relation);
            } else {
                relation = mergeRelations(exist, relation);
            }
        }
        return new RelationResponse(relation);
    }

    /**
     * Function Merging two Relation.
     *
     * @param root
     * @param merged
     * @return
     */
    private Relation mergeRelations(Relation root, Relation merged) {
        log.trace(LogMessages.MSG_MERGING_RELATIONS, merged, root);
        root.setType(merged.getType());
        root.setActive(merged.getActive());
        merged.getChildren().forEach(root::addChildren);
        log.trace(LogMessages.MSG_AFTER_MERGE, root);
        return root;
    }

    private Relation moveChildrenFromTo(Relation from, Relation to) {
        to.getChildren().addAll(from.getChildren());
        from.getChildren().clear();
        return to;
    }

    @Override
    public ServiceResponse addRelation(Member m1, Member m2, RelationType type, boolean active) {
        return addRelation(createRelationFrom(m1, m2, type, active));
    }

    @Override
    public ServiceResponse updateRelation(Relation relation) {
        // Nothing to perform if Local Service
        invalidate();
        return new RelationResponse(relation);
    }

    @Override
    public ServiceResponse moveChildFromRelation(Member m, Relation oldRelation, Relation newRelation) {

        if (!getCurrentFamily().getRelations().contains(newRelation)) {
            addRelation(newRelation);
        }

        oldRelation.getChildren().remove(m);
        newRelation.getChildren().add(m);


        if ((oldRelation.getLeft() == null || oldRelation.getRight() == null) && oldRelation.getChildren().isEmpty()) {
            getCurrentFamily().getRelations().remove(oldRelation);
        }

        return new RelationResponse(newRelation);
    }

    private void moveChildToNewRelation(Relation target, Member m) {
        try {
            Relation born = getCurrentFamily().findBornRelation(m);
            moveChildFromRelation(m, born, target);

        } catch (NotUniqueBornRelationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Move all children from List to Target relation
     *
     * @param target
     * @param children
     * @return
     */
    @Override
    public ServiceResponse moveChildrenToNewRelation(Relation target, List<Member> children) {

        children.forEach(c -> {
            if (!target.getChildren().contains(c)) {
                moveChildToNewRelation(target, c);
            }
        });

        return new RelationResponse(target);
    }


    /**
     * Totaly remove relation from Family
     *
     * @param r
     * @return
     */
    @Override
    public ServiceResponse removeRelation(Relation r) {

        if (r.getChildren().size() > 0) {
            r.setLeft(null);
            r.setRight(null);
            r.setType(null);
        } else {
            getCurrentFamily().getRelations().remove(r);
        }

        return new RelationResponse(r);
    }


    /*
        LISTENERS
     */

    private void setCurrentFamilyListener() {
        this.currentFamily.addListener(familyListener);
    }

    private void setMemberListener() {
        this.getCurrentFamily().getMembers().addListener(membersListListener);
    }

    private void setRelationListener(Family family) {
        family.getRelations().addListener(relationListListener);
    }

    private void cleanFamilyListener() {
        cleanMemberAndRelationListeners(getCurrentFamily());
        currentFamily.removeListener(familyListener);
    }

    private void cleanMemberAndRelationListeners(Family family) {
        family.getRelations().removeListener(relationListListener);
        family.getMembers().removeListener(membersListListener);
    }

    private void familyChanged(ObservableValue<? extends Family> observable, Family oldValue, Family newValue) {
        ActiveRelationGuard oldGuard = guard;

        if (oldGuard != null) {
            oldGuard.clean();
        }
        if (newValue != null) {
            guard = new ActiveRelationGuard(newValue.getRelations());
        }
    }

    private void memberListChange(ListChangeListener.Change<? extends Member> c) {
        while (c.next()) {
            if (c.wasAdded()) {
                c.getAddedSubList().forEach(member -> {
                    incrementMemberId(member);
                    log.info(LogMessages.MSG_MEMBER_ADDED, member);
                    addRelation(new Relation(member));
                });
            } else if (c.wasRemoved()) {
                c.getRemoved().forEach(element -> {
                    clearRelationFromDeletedMember(element);
                    log.info(LogMessages.MSG_MEMBER_REMOVED, element);
                });

            }
        }
    }

    private void relationListChange(ListChangeListener.Change<? extends Relation> c) {
        while (c.next()) {
            if (c.wasAdded()) {
                c.getAddedSubList().forEach(element -> {
                    guard.addObserverTo(element);
                    incrementRelationId(element);
                    log.info(LogMessages.MSG_RELATION_ADDED, element);
                });
            }
            if (c.wasRemoved()) {
                c.getRemoved().forEach(element -> {
                    guard.removeObserverFrom(element);
                    log.info(LogMessages.MSG_RELATION_REMOVED, element);
                });
            }
        }
        sm.getGenTreeDrawingService().startDraw();
    }


    private void incrementMemberId(Member member) {
        if (member.getId() <= 0) {
            member.setId(incrementMember());
        } else {
            idMember = idMember < member.getId() ? member.getId() : idMember;
        }
    }

    private Long incrementMember() {
        return ++idMember;
    }


    private void incrementRelationId(Relation relation) {
        if (relation.getId() <= 0) {
            relation.setId(incrementRelation());
        } else {
            idRelation = idRelation < relation.getId() ? relation.getId() : idRelation;
        }
    }

    private Long incrementRelation() {
        return ++idRelation;
    }



    /*
        GETTERS
     */


    @Override
    public ServiceResponse setCurrentFamily(Family currentFamily) {
        log.trace(LogMessages.MSG_FAMILY_SERVICE_CURRENT_FAMILY, currentFamily);
        this.currentFamily.set(currentFamily);

        if (currentFamily.getMembers().size() > 0) {
            idMember = currentFamily.getMembers().stream().max(Comparator.comparingLong(Member::getId)).get().getId();
        }
        if (currentFamily.getRelations().size() > 0) {
            idRelation = currentFamily.getRelations().stream().max(Comparator.comparingLong(Relation::getId)).get().getId();
        }

        setMemberListener();
        setRelationListener(currentFamily);

        return new FamilyResponse(currentFamily);
    }


    private void clearRelationFromDeletedMember(Member m) {
        List<Relation> list = getCurrentFamily().getRelations()
                .stream()
                .filter(r -> (r.compareLeft(m)) || r.compareRight(m) || r.getChildren().contains(m))
                .collect(Collectors.toList());

        for (Relation r : list) {
            if (r.compareLeft(m)) r.setLeft(null);
            if (r.compareRight(m)) r.setRight(null);
            r.getChildren().remove(m);

            /*
                Merging relations
             */
            if (r.getLeft() != null || r.getRight() != null) {
                Relation toMerge = findRelation(r.getLeft(), r.getRight(), r);
                if (toMerge != null) moveChildrenFromTo(r, toMerge);
            }
        }
        /*
            Orphan delete
         */
        List<Relation> toDelete = getCurrentFamily().getRelations()
                .stream()
                .filter(r -> (r.getLeft() == null || r.getRight() == null) && r.getChildren().isEmpty())
                .collect(Collectors.toList());
        getCurrentFamily().getRelations().removeAll(toDelete);
        invalidate();
    }

    @Override
    public ReadOnlyObjectProperty<Family> currentFamilyPropertyI() {
        return currentFamily;
    }
    /*
        SETTERS
     */

    @Override
    public ReadOnlyObjectProperty<Family> familyProperty() {
        return currentFamily;
    }

    public ObjectProperty<Family> currentFamilyProperty() {
        return currentFamily;
    }

    /**
     * Create project xml file and directory
     *
     * @param currentFamily
     */
    public ServiceResponse createFamily(Family currentFamily) {
        log.trace(LogMessages.MSG_PARAMETER_PASSED_TO_FUNCTION, Thread.currentThread().getStackTrace()[1].getMethodName(), currentFamily);
        setCurrentFamily(currentFamily);
        try {
            ps.generateLocalProjectFiles(currentFamily);
            saveProject();
        } catch (IOException e) {
            log.error(LogMessages.MSG_ERROR_CREATE_FAMILY, e);
            sm.showError(ErrorMessages.TITLE_ERROR_CREATE_FAMILY, String.format(ErrorMessages.HEADER_ERROR_CREATE_FAMILY, currentFamily.getName()), e.getMessage());

        }
        return new FamilyResponse(currentFamily);
    }


    /**
     * Open existing family
     *
     * @param currentFamily
     * @param filename
     */
    public void openProject(Family currentFamily, String filename) {
        setCurrentFamily(currentFamily);
        ps.setProjectFilename(filename);
    }


    /**
     * Marchalling project to XML file
     */
    public void saveProject() {
        ps.copyImagesToTargetProject(getCurrentFamily());

        Path p = Paths.get(config.getString(PropertiesKeys.PARAM_DIR_PROJECT_NAME)).resolve(ps.getProjectFilename());
        log.trace(LogMessages.MSG_SAVE_PROJECT_LOCAL, p);
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Family.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            // output pretty printed
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(getCurrentFamily(), new File(p.toUri()));
            log.trace(LogMessages.MSG_CONFIRM_SAVE_PROJECT_LOCAL, p);
        } catch (Exception e) {
            log.error(LogMessages.MSG_ERROR_SAVE_FAMILY, p,  e.getMessage());
            sm.showError(ErrorMessages.TITLE_ERROR_SAVE, ErrorMessages.HEADER_ERROR_SAVE, e.getMessage());
            e.printStackTrace();
        }
    }


    @Override
    public void clean() {

    }

    private void invalidate() {
        sm.getGenTreeDrawingService().startDraw();
    }
}

