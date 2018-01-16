package gentree.server.service;

import gentree.exception.*;
import gentree.server.domain.entity.FamilyEntity;
import gentree.server.domain.entity.MemberEntity;
import gentree.server.domain.entity.OwnerEntity;
import gentree.server.domain.entity.RelationEntity;
import gentree.server.service.wrappers.NewMemberWrapper;

import java.util.List;

/**
 * Created by Martyna SZYMKOWIAK on 20/10/2017.
 */
public interface ProjectService {

    FamilyEntity addFamily(FamilyEntity familyEntity);

    NewMemberWrapper addMember(MemberEntity memberEntity);

    MemberEntity updateMember(MemberEntity memberEntity);

    FamilyEntity deleteMember(MemberEntity memberEntity);

    MemberEntity getMemberById(Long id);

    List<RelationEntity> addRelation(RelationEntity relationEntity) throws TooManyNullFieldsException, AscendanceViolationException, IncorrectStatusException, NotExistingMemberException, NotExistingRelationException;

    FamilyEntity findFamilyById(Long id);

    FamilyEntity findFullFamilyById(Long id);

    List<FamilyEntity> findAllFamiliesByOwner(OwnerEntity owner);

    List<RelationEntity> updateRelation(RelationEntity relationEntity) throws NotExistingRelationException;

    RelationEntity deleteRelation(RelationEntity relationEntity);

}