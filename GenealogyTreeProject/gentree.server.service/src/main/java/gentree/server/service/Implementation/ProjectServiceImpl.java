package gentree.server.service.Implementation;

import gentree.common.configuration.enums.Gender;
import gentree.common.configuration.enums.RelationType;
import gentree.exception.*;
import gentree.server.domain.entity.*;
import gentree.server.service.*;
import gentree.server.service.validator.RelationValidator;
import gentree.server.service.wrappers.NewMemberWrapper;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

/**
 * Created by Martyna SZYMKOWIAK on 20/10/2017.
 */
@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    FamilyService familyService;

    @Autowired
    MemberService memberService;

    @Autowired
    PhotoService photoService;

    @Autowired
    RelationService relationService;

    @Autowired
    RelationValidator relationValidator;


    /* ************************************************************
         Family Management
    ************************************************************ */

    @Override
    public FamilyEntity addFamily(FamilyEntity familyEntity) {
        return familyService.addNewFamily(familyEntity);
    }

    @Override
    public FamilyEntity findFamilyById(Long id) {
        return familyService.findFamilyById(id);
    }

    @Override
    public FamilyEntity findFullFamilyById(Long id) {
        FamilyEntity family = familyService.findFullFamilyById(id);
        for (MemberEntity m : family.getMembers()) {
            photoService.populateEncodedPhoto(m);
        }
        return family;
    }

    @Override
    public FamilyEntity updateFamily(FamilyEntity family) {
        return familyService.updateFamily(family);

    }

    @Override
    public List<FamilyEntity> findAllFamiliesByOwner(OwnerEntity owner) {
        return familyService.findAllByOwner(owner);
    }



    /* ************************************************************
        Member Management
    ************************************************************ */

    @Override
    public NewMemberWrapper addMember(MemberEntity memberEntity) {
        NewMemberWrapper wrapper = new NewMemberWrapper();
        try {
            wrapper.setMember(memberService.addNewMember(memberEntity));
            wrapper.setBornRelation(relationService.addNewBornRelation(wrapper.getMember()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wrapper;
    }


    @Override
    public MemberEntity updateMember(MemberEntity memberEntity) {
        if (memberEntity.getPhoto() != null) {
            PhotoEntity ph = photoService.persistPhoto(memberEntity.getPhoto());
            memberEntity.setPhoto(ph);
        }
        return memberService.updateMember(memberEntity);
    }

    @Override
    public FamilyEntity deleteMember(MemberEntity memberEntity) {
        memberService.deleteMember(memberEntity);
        relationService.removeOrphans(memberEntity.getFamily().getId());
        return findFullFamilyById(memberEntity.getFamily().getId());
    }

    @Override
    public MemberEntity getMemberById(Long id) {
        MemberEntity memberEntity = memberService.findMemberById(id);
        memberEntity.setPhoto(photoService.retrievePhoto(memberEntity));
        return memberEntity;
    }

    /* ************************************************************
        Relation Management
    ************************************************************ */


    @Override
    public List<RelationEntity> addRelation(RelationEntity relationEntity)
            throws TooManyNullFieldsException, AscendanceViolationException, IncorrectStatusException, NotExistingMemberException, NotExistingRelationException {
        FamilyEntity familyEntity = familyService.findFamilyById(relationEntity.getFamily().getId());

        /*
           Validate relation
         */
        relationValidator.validate(relationEntity, familyEntity);

        relationEntity = setMembersToCorrectPlace(relationEntity);


        /*
            Foreach children
         */
        // relationService


        /*
         * Verify Existing
         *
         */

        RelationEntity existing = relationService.findRelationBySimLeftAndSimRight
                (relationEntity.getLeft(),
                        relationEntity.getRight());
        // System.out.println("Existing is : " +existing);



        /*
            Prepare target
         */
        RelationEntity target = existing == null ?
                relationService.addNewRelation(relationEntity) : mergeChildrenAndStatus(existing, relationEntity);

        relationService.saveandflush();
        return relationService.findAllRelationsByFamilyId(target.getFamily().getId());
    }

    @Override
    public List<RelationEntity> updateRelation(RelationEntity relationEntity) throws NotExistingRelationException {
        RelationEntity target = relationService.findRelationById(relationEntity.getId());
        target.setActive(relationEntity.isActive());
        target.setType(relationEntity.getType());

        target = relationService.updateRelation(target);
        List<RelationEntity> targetList = relationService.findAllRelationsByFamilyId(target.getFamily().getId());


        Hibernate.initialize(targetList);
        targetList.forEach(Hibernate::initialize);
        return targetList;
    }

    @Override
    public RelationEntity deleteRelation(RelationEntity relationEntity) {

        return relationService.deleteRelation(relationEntity);
    }


    /**
     * For two relation with same  Member Left and Member Right merge Children
     *
     * @param existing
     * @param candidate
     * @return relation
     */
    private RelationEntity mergeChildrenAndStatus(RelationEntity existing, RelationEntity candidate) throws NotExistingRelationException {
        existing.setType(candidate.getType());
        existing.setActive(candidate.isActive());

        if (candidate.getChildren() != null && !candidate.getChildren().isEmpty()) {
            for (MemberEntity child : candidate.getChildren()) {
                if ((existing.getChildren().stream().noneMatch(c -> Objects.equals(child.getId(), c.getId())))) {
                    removeChildFromCurrentBornRelationAndRemoveIt(child);
                    existing.getChildren().add(child);
                    relationService.saveandflush();
                }
            }
        } else {
        }

        return relationService.updateRelation(existing);

    }

    private void removeChildFromCurrentBornRelationAndRemoveIt(MemberEntity child) throws NotExistingRelationException {
        List<RelationEntity> list = relationService.findBornRelations(child);
        for (RelationEntity relation : list) {
            if ((relation.getLeft() != null && relation.getRight() != null) || relation.getChildren().size() > 1) {
                relation.getChildren().remove(child);
                relationService.updateRelation(relation);
            } else {
                relationService.forcedeleteRelation(relation);
            }
        }
        relationService.saveandflush();
    }


    private MemberEntity findInList(List<MemberEntity> list, MemberEntity entity) {
        return list.stream().filter(member -> Objects.equals(member.getId(), entity.getId())).findAny().orElse(null);
    }


    protected RelationEntity setMembersToCorrectPlace(RelationEntity relationEntity) {

        MemberEntity candidateLeft = relationEntity.getLeft() == null ? null : memberService.findMemberById(relationEntity.getLeft().getId());
        MemberEntity candidateRight = relationEntity.getRight() == null ? null : memberService.findMemberById(relationEntity.getRight().getId());


        if (candidateLeft == null || candidateRight == null) {
            if (candidateLeft != null) {
                if (candidateLeft.getGender() == Gender.F) relationEntity.setLeft(candidateLeft);
                if (candidateLeft.getGender() == Gender.M) relationEntity.setRight(candidateLeft);
            } else if (candidateRight != null) {
                if (candidateRight.getGender() == Gender.F) relationEntity.setLeft(candidateRight);
                if (candidateRight.getGender() == Gender.M) relationEntity.setRight(candidateRight);
            }
            relationEntity.setType(RelationType.NEUTRAL);
        } else {

        /*
            Left is an user with higher ID
         */
            if (candidateLeft.getGender() == candidateRight.getGender()) {
                relationEntity.setLeft(candidateLeft.getId() > candidateRight.getId() ? candidateLeft : candidateRight);
                relationEntity.setRight(candidateLeft.getId() < candidateRight.getId() ? candidateLeft : candidateRight);
            } else {
                relationEntity.setLeft(candidateLeft.getGender() == Gender.F ? candidateLeft : candidateRight);
                relationEntity.setRight(candidateLeft.getGender() == Gender.M ? candidateLeft : candidateRight);
            }
        }
        return relationEntity;
    }


}
