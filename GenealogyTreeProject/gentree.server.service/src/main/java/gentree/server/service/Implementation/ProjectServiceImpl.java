package gentree.server.service.Implementation;

import gentree.server.domain.entity.FamilyEntity;
import gentree.server.domain.entity.MemberEntity;
import gentree.server.domain.entity.OwnerEntity;
import gentree.server.domain.entity.RelationEntity;
import gentree.server.service.FamilyService;
import gentree.server.service.MemberService;
import gentree.server.service.ProjectService;
import gentree.server.service.RelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
    public List<FamilyEntity> findAllFamiliesByOwner(OwnerEntity owner) {
        return familyService.findAllByOwner(owner);
    }



    /* ************************************************************
        Member Management
    ************************************************************ */

    @Override
    public MemberEntity addMember(MemberEntity memberEntity) {
        MemberEntity newMember = memberService.addNewMember(memberEntity);
        RelationEntity bornRelation = relationService.addNewBornRelation(memberEntity);

        return null;
    }



    /* ************************************************************
        Relation Management
    ************************************************************ */


    @Override
    public RelationEntity addRelation(RelationEntity relationEntity) {
        return null;
    }
}
