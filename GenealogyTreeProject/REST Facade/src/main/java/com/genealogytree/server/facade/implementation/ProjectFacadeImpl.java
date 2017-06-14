package genealogytree.server.facade.implementation;

import genealogytree.ExceptionManager.exception.*;
import genealogytree.domain.dto.FamilyDTO;
import genealogytree.domain.dto.MemberDTO;
import genealogytree.domain.dto.RelationDTO;
import genealogytree.domain.dto.UserDTO;
import genealogytree.domain.wrappers.CreatedMemberWrapper;
import genealogytree.persist.entity.modules.administration.UserEntity;
import genealogytree.persist.entity.modules.tree.FamilyEntity;
import genealogytree.persist.entity.modules.tree.MemberEntity;
import genealogytree.persist.entity.modules.tree.RelationsEntity;
import genealogytree.server.facade.ProjectFacade;
import genealogytree.server.facade.converters.ConverterDtoToEntity;
import genealogytree.server.facade.converters.ConverterEntityToDto;
import genealogytree.service.validators.Validator;
import genealogytree.service.FamilyService;
import genealogytree.service.MemberService;
import genealogytree.service.RelationService;
import genealogytree.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martyna SZYMKOWIAK on 18/03/2017.
 */
@Service
public class ProjectFacadeImpl implements ProjectFacade {


    @Autowired
    UserService userService;

    @Autowired
    FamilyService familyService;

    @Autowired
    RelationService relationService;

    @Autowired
    MemberService memberService;

    @Autowired
    ConverterDtoToEntity converterDtoToEntity;

    @Autowired
    ConverterEntityToDto converterEntityToDto;



    /*
        SELECT METHODS
     */
    @Override
    public List<FamilyDTO> getFamillies(UserDTO user) {
        UserEntity owner = user.getLogin() == null ?
                (user.getId() == null ? null : userService.findUser(user.getId())) : userService.findUser(user.getLogin());
        return null;
    }

    @Override
    public List<MemberDTO> getMembers(FamilyDTO owner) throws NotFoundFamilyException {

        FamilyEntity family = converterDtoToEntity.convert(owner);
        if (family == null) {
            throw new NotFoundFamilyException();
        }

        return converterEntityToDto.convertMemberList(memberService.getMembers(family));
    }

    @Override
    public List<RelationDTO> getRelations(Long ownerId) throws NotFoundFamilyException {
        FamilyEntity family = familyService.getFamily(ownerId);
        if (family == null) {
            throw new NotFoundFamilyException();
        }

        return converterEntityToDto.convertRelationList(relationService.getRelations(family));
    }

    @Override
    public FamilyDTO getFamily(Long id) throws NotFoundFamilyException {
        return converterEntityToDto.convert(familyService.getFamily(id));
    }

    @Override
    public FamilyDTO addFamily(FamilyDTO family) {
        FamilyEntity familyEntity = familyService.addFamily(converterDtoToEntity.convert(family));
        return converterEntityToDto.convert(familyEntity);
    }

    // INJECT METHODS


    @Override
    public CreatedMemberWrapper addMember(MemberDTO member) throws IncorrectSex, IntegrationViolation, TooManyNullFields, IncorrectStatus, NoValidMembers {

        FamilyEntity owner = familyService.getFamily(member.getOwnerF().getId());
        MemberEntity memberEntity = converterDtoToEntity.convert(member);
        memberEntity.setOwnerF(owner);

        memberEntity = memberService.addMember(memberEntity);
        RelationsEntity relation = relationService.addRelation(new RelationsEntity(owner, null, null, memberEntity));

        return new CreatedMemberWrapper(converterEntityToDto.convert(memberEntity), converterEntityToDto.convert(relation));
    }

    @Override
    public List<RelationDTO> addRelation(RelationDTO relation) throws IncorrectSex, NoValidMembers, IntegrationViolation, IncorrectStatus, TooManyNullFields {
        List<RelationDTO> result = new ArrayList<>();

        FamilyEntity owner = familyService.getFamily(relation.getOwnerF().getId());
        RelationsEntity toAdd = converterDtoToEntity.convert(relation);

        RelationsEntity relationsEntity = relationService.addRelation(toAdd);

        result = converterEntityToDto.convertRelationList(relationService.getRelations(owner));
        return result;
    }


    @Override
    public FamilyDTO updateFamily(FamilyDTO family) {
        //TODO
        return null;
    }


}
