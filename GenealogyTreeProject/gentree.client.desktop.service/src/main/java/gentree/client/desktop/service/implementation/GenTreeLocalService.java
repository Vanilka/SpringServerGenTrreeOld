package gentree.client.desktop.service.implementation;


import gentree.client.desktop.configuration.common.GenTreeProperties;
import gentree.client.desktop.configuration.common.enums.PropertiesKeys;
import gentree.client.desktop.configuration.common.messages.LogMessages;
import gentree.client.desktop.domain.Family;
import gentree.client.desktop.domain.Member;
import gentree.client.desktop.domain.Relation;
import gentree.client.desktop.domain.enums.Gender;
import gentree.client.desktop.domain.enums.RelationType;
import gentree.client.desktop.service.ActiveRelationGuard;
import gentree.client.desktop.service.FamilyService;
import gentree.client.desktop.service.responses.MemberResponse;
import gentree.client.desktop.service.responses.RelationResponse;
import gentree.client.desktop.service.responses.ServiceResponse;
import gentree.exception.NotUniqueBornRelationException;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.configuration2.Configuration;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by Martyna SZYMKOWIAK on 01/07/2017.
 */
@Log4j2
public class GenTreeLocalService implements FamilyService {

    private final static String PROJECT_FILE_EXTENSION = ".xml";
    private static final String PREFIX_FILE_ABSOLUTE = "file://";
    private static final String PREFIX_FILE_RELATIVE = "file:";
    private final Configuration config = GenTreeProperties.INSTANCE.getConfiguration();
    private ObjectProperty<Family> currentFamily;
    private ActiveRelationGuard guard;

    private String projectFilename;

    private Long idMember;
    private Long idRelation;

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
        log.info(LogMessages.MSG_MEMBER_ADD_NEW, member);
        if (!isGenericPhoto(member.getPhoto())) {
            String newPhotoPath = copyPhoto(config.getString(PropertiesKeys.PARAM_DIR_IMAGE_NAME), member.getPhoto());
            member.setPhoto(newPhotoPath == null ? null : PREFIX_FILE_RELATIVE + newPhotoPath);
        }
        getCurrentFamily().addMember(member);
        return new MemberResponse(member);
    }

    /**
     * Function to adding Relation beetween Members to Family
     *
     * @param relation
     * @return
     */
    @Override
    public ServiceResponse addRelation(Relation relation) {
        if (relation.getLeft() == null && relation.getRight() == null) {
            getCurrentFamily().addRelation(relation);
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
        root.setType(merged.getType());
        root.setActive(merged.getActive());
        merged.getChildren().forEach(root::addChildren);


        return root;
    }


    @Override
    public ServiceResponse addRelation(Member m1, Member m2, RelationType type, boolean active) {
        Member left;
        Member right;
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
        return addRelation(new Relation(left, right, type, active));
    }

    @Override
    public ServiceResponse updateRelation(Relation relation) {
        // Nothing to perform if Local Service
        return new RelationResponse(relation);
    }

    @Override
    public ServiceResponse moveChildFromRelation(Member m, Relation oldRelation, Relation newRelation) {
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

    @Override
    public Relation findRelation(Member left, Member right) {
        List<Relation> list = getCurrentFamily().getRelations()
                .filtered(r -> r.getLeft() != null || r.getRight() != null)
                .filtered(r -> r.compareLeft(left) && r.compareRight(right));
        return list.size() == 0 ? null : list.get(0);

    }

    @Override
    public ObservableList<Member> findAllRootMembers() {
        ObservableList<Member> rootList = FXCollections.observableArrayList();

        getCurrentFamily().getRelations()
                .filtered(r -> r.getLeft() == null)
                .filtered(r -> r.getRight() == null)
                .filtered(r -> !r.getChildren().isEmpty())
                .forEach(root -> rootList.addAll(root.getChildren()));


        return rootList;
    }

    /**
     * Verify if parameter sim is Ascendant of parameter grain
     * @param grain
     * @param sim
     * @return
     */
    @Override
    public boolean isAscOf(Member grain, Member sim) {
        return grain != null && sim != null && getCurrentFamily().isAscOf(grain, sim);
    }


    /**
     * Verify if parameter sim is Descendant of parameter grain
     * @param grain
     * @param sim
     * @return
     */
    @Override
    public boolean isDescOf(Member grain, Member sim) {
        return grain != null && sim != null && getCurrentFamily().isDescOf(grain, sim);
    }


    /*
        LISTENERS
     */


    private void setCurrentFamilyListener() {
        this.currentFamily.addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                guard = new ActiveRelationGuard(newValue.getRelations());
                newValue.getRelations().forEach(element -> guard.addObserverTo(element));
            }
        });
    }


    private void setMemberListener() {
        getCurrentFamily().getMembers().addListener((ListChangeListener<Member>) c -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    c.getAddedSubList().forEach(member -> {
                        log.info(LogMessages.MSG_MEMBER_ADD_NEW, member);
                        incrementMemberId(member);
                        addRelation(new Relation(member));
                    });
                } else if (c.wasPermutated()) {
                    for (int i = c.getFrom(); i < c.getTo(); ++i) {
                        //permutate
                        System.out.println("permutated");
                    }
                } else if (c.wasUpdated()) {
                    //update item
                    System.out.println("UpdateItem Member");
                } else if (c.wasRemoved()) {
                    System.out.println("Removed " + c.getRemoved().toArray().toString());

                } else {
                }
            }
        });
    }

    private void setRelationListener() {
        getCurrentFamily().getRelations().addListener((ListChangeListener<Relation>) c -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    c.getAddedSubList().forEach(relation -> {
                        guard.addObserverTo(relation);
                        incrementRelationId(relation);
                        log.info(LogMessages.MSG_RELATION_ADD_NEW, relation);
                    });
                } else if (c.wasPermutated()) {
                    for (int i = c.getFrom(); i < c.getTo(); ++i) {
                        //permutate
                        System.out.println(" Relation permutated");
                    }
                } else if (c.wasUpdated()) {
                    //update item
                    System.out.println(" Relation UpdateItem");
                } else if (c.wasRemoved()) {
                    System.out.println("Relation removed" + c.getRemoved().toString());
                } else {
                }
            }
        });
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



    /*
        GETTERS
     */

    private Long incrementRelation() {
        return ++idRelation;
    }

    @Override
    public Family getCurrentFamily() {
        return currentFamily.get();
    }


    @Override
    public void setCurrentFamily(Family currentFamily) {
        log.trace(LogMessages.MSG_FAMILY_SERVICE_CURRENT_FAMILY, currentFamily);
        this.currentFamily.set(currentFamily);

        if (currentFamily.getMembers().size() > 0) {
            idMember = currentFamily.getMembers().stream().max(Comparator.comparingLong(Member::getId)).get().getId();
        }
        if (currentFamily.getRelations().size() > 0) {
            idRelation = currentFamily.getRelations().stream().max(Comparator.comparingLong(Relation::getId)).get().getId();
        }

        setMemberListener();
        setRelationListener();
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
    public void createProject(Family currentFamily) {
        setCurrentFamily(currentFamily);

        String filename = generateProjectName(currentFamily.getName());
        String baseDir = config.getString(PropertiesKeys.PARAM_DIR_PROJECT_NAME);
        try {
            Files.createDirectory(Paths.get(baseDir, filename.replace(PROJECT_FILE_EXTENSION, "")));
            Files.createFile(Paths.get(baseDir, filename));
            projectFilename = filename;

            saveProject();


        } catch (IOException e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Open existing family
     *
     * @param currentFamily
     * @param filename
     */
    public void openProject(Family currentFamily, String filename) {
        setCurrentFamily(currentFamily);
        this.projectFilename = filename;

    }

    /**
     * Generete projectname for new Family.
     * If name already exist, increment number.
     *
     * @param name
     * @return
     */
    private String generateProjectName(String name) {
        name = name.replaceAll("[^a-zA-Z0-9]+", "");

        String baseDir = config.getString(PropertiesKeys.PARAM_DIR_PROJECT_NAME);
        String filename = name + PROJECT_FILE_EXTENSION;

        if (Files.exists(Paths.get(baseDir, filename))) {
            String template = "%s%d.xml";
            int i = 0;
            do {
                i++;
                filename = String.format(template, name, i);
                ;
            } while (Files.exists(Paths.get(baseDir, String.format(template, name, i))));
        }

        return filename;
    }



    /**
     * Marchalling project to XML file
     */
    public void saveProject() {

        copyImagesToTargetProject(getCurrentFamily());
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Family.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            // output pretty printed
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(getCurrentFamily(), new File(
                    config.getString(PropertiesKeys.PARAM_DIR_PROJECT_NAME), projectFilename));
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Copy Member photo to parentPane folder
     *
     * @param parent
     * @param path
     * @return
     */
    private String copyPhoto(String parent, String path) {
        path = path.replace(PREFIX_FILE_ABSOLUTE, "").replace(PREFIX_FILE_RELATIVE, "");
        try {
            Path result = Files.copy(Paths.get(path), Paths.get(parent, Long.toString((new Date()).getTime())));
            File file = new File(result.toString());
            path = Paths.get(file.getParent(), file.getName()).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return path;
    }

    /**
     * Move photo to parentPane folder
     *
     * @param parent
     * @param path
     * @return
     */
    private String movePhoto(String parent, String path) {
        path = path.replace(PREFIX_FILE_ABSOLUTE, "").replace(PREFIX_FILE_RELATIVE, "");
        try {
            Path source = Paths.get(path);
            Path target = Paths.get(parent, source.getFileName().toString());
            Path result = Files.move(source, target);
            File file = new File(result.toString());
            path = Paths.get(file.getParent(), file.getName()).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return path;
    }

    /**
     * Verify is photo generic
     *
     * @param s
     * @return
     */
    private boolean isGenericPhoto(String s) {
        return (s.equals(ImageFiles.GENERIC_FEMALE.toString()) || s.equals(ImageFiles.GENERIC_MALE.toString()));
    }

    /**
     * Verifiy necessity of copy photo
     *
     * @param s
     * @return
     */

    private boolean needCopy(String s) {
        boolean result = !isGenericPhoto(s)
                && (s.contains(PREFIX_FILE_RELATIVE)
                && !s.contains(config.getString(PropertiesKeys.PARAM_DIR_PROJECT_NAME))
                && !s.contains(projectFilename.replace(PROJECT_FILE_EXTENSION, "")))
                || s.contains(PREFIX_FILE_ABSOLUTE);
        return result;
    }


    /**
     * While save file, copy / move photos to target project folder
     *
     * @param family
     */
    private void copyImagesToTargetProject(Family family) {
        family.getMembers()
                .filtered(m -> needCopy(m.getPhoto()))
                .forEach(member -> {
                    String newPath = movePhoto(Paths.get(
                            config.getString(PropertiesKeys.PARAM_DIR_PROJECT_NAME),
                            projectFilename.replace(PROJECT_FILE_EXTENSION, "")).toString(),
                            member.getPhoto());
                    member.setPhoto(newPath == null ? null : PREFIX_FILE_RELATIVE + newPath);

                });
    }
}

