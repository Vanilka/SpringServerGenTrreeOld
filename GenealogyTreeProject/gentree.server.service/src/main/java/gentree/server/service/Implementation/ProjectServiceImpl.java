package gentree.server.service.Implementation;

import gentree.server.domain.entity.FamilyEntity;
import gentree.server.domain.entity.MemberEntity;
import gentree.server.domain.entity.OwnerEntity;
import gentree.server.domain.entity.RelationEntity;
import gentree.server.service.FamilyService;
import gentree.server.service.MemberService;
import gentree.server.service.ProjectService;
import gentree.server.service.RelationService;
import gentree.server.service.validator.RelationValidator;
import gentree.server.service.wrappers.NewMemberWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Wrapper;
import java.util.List;

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

        return familyService.findFullFamilyById(id);
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
        NewMemberWrapper  wrapper = new NewMemberWrapper();
        try {
            wrapper.setMember(memberService.addNewMember(memberEntity));
            wrapper.setBornRelation(relationService.addNewBornRelation(wrapper.getMember()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wrapper;
    }

    @Override
    public FamilyEntity deleteMember(MemberEntity memberEntity) {
        memberService.deleteMember(memberEntity);
        relationService.removeOrphans(memberEntity.getFamily().getId());
        return findFullFamilyById(memberEntity.getFamily().getId());
    }

    /* ************************************************************
        Relation Management
    ************************************************************ */


    @Override
    public List<RelationEntity> addRelation(RelationEntity relationEntity) {
        RelationEntity added = relationService.addNewRelation(relationEntity);
        return relationService.findAllRelationsByFamilyId(added.getFamily().getId()) ;
    }

    @Override
    public List<RelationEntity> updateRelation(RelationEntity relationEntity) {
        return null;
    }

    @Override
    public RelationEntity deleteRelation(RelationEntity relationEntity) {

        return relationService.deleteRelation(relationEntity);
    }
}
