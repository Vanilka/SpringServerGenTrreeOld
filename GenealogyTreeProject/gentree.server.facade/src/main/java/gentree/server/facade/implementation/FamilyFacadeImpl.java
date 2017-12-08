package gentree.server.facade.implementation;

import gentree.exception.AscendanceViolationException;
import gentree.exception.IncorrectStatusException;
import gentree.exception.NotExistingMemberException;
import gentree.exception.TooManyNullFieldsException;
import gentree.server.domain.entity.*;
import gentree.server.dto.*;
import gentree.server.facade.FamilyFacade;
import gentree.server.facade.converter.ConverterToDTO;
import gentree.server.facade.converter.ConverterToEntity;
import gentree.server.service.OwnerService;
import gentree.server.service.PhotoService;
import gentree.server.service.ProjectService;
import gentree.server.service.validator.RelationValidator;
import gentree.server.service.wrappers.NewMemberWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Martyna SZYMKOWIAK on 18/10/2017.
 */
@Service
public class FamilyFacadeImpl implements FamilyFacade {


    @Autowired
    ProjectService projectService;

    @Autowired
    PhotoService photoService;

    @Autowired
    OwnerService ownerService;

    @Autowired
    ConverterToDTO converterToDTO;

    @Autowired
    ConverterToEntity converterToEntity;

    @Autowired
    RelationValidator relationValidator;

    /* ************************************************************
        Family Gestion
    ************************************************************ */

    @Override
    public FamilyDTO addNewFamily(FamilyDTO familyDTO, OwnerDTO ownerDTO) {
        OwnerEntity ownerEntity = ownerService.findOperatorByLogin(ownerDTO.getLogin());
        FamilyEntity newFamily = converterToEntity.convert(familyDTO);
        newFamily.setOwner(ownerEntity);
        newFamily = projectService.addFamily(newFamily);
        return converterToDTO.convertPoor(newFamily);
    }

    @Override
    public FamilyDTO findFamilyById(Long id) {
        FamilyEntity result = projectService.findFamilyById(id);
        return converterToDTO.convert(result);
    }

    @Override
    public FamilyDTO findExtendedFamilyById(Long id) {
        FamilyEntity result = projectService.findFullFamilyById(id);
        return converterToDTO.convertFullFamily(result);
    }

    @Override
    public List<FamilyDTO> findAllFamiliesByOwner(OwnerDTO ownerDTO) {
        OwnerEntity owner = converterToEntity.convert(ownerDTO);
        List<FamilyEntity> list = projectService.findAllFamiliesByOwner(owner);
        return converterToDTO.convertFamilyList(list);
    }

    /* ************************************************************
        Member Gestion
    ************************************************************ */

    @Override
    public NewMemberDTO addNewMember(MemberDTO member) {
        member.setId(null);
        member.setVersion(null);
        NewMemberWrapper wrapper = projectService.addMember(converterToEntity.convert(member));
        NewMemberDTO dto = new NewMemberDTO();
        dto.setMemberDTO(converterToDTO.convert(wrapper.getMember()));
        dto.setRelationDTO(converterToDTO.convert(wrapper.getBornRelation()));
        return dto;
    }

    @Override
    public MemberWithPhotoDTO updateMember(MemberWithPhotoDTO member) {
        MemberEntity memberEntity = converterToEntity.convert(member);

        if (member.getPhotoDTO() != null) {
            PhotoEntity photoEntity = new PhotoEntity();
            photoEntity.setOwner(converterToEntity.convertLazy(member));
            photoEntity.setEncodedStringPhoto(member.getPhotoDTO().getEncodedPicture());
            memberEntity.setPhoto(photoEntity);
        }
        memberEntity = projectService.updateMember(memberEntity);
        return converterToDTO.convertWithPhoto(memberEntity);
    }

    @Override
    public FamilyDTO deleteMember(MemberDTO m) {
        FamilyEntity entity = projectService.deleteMember(converterToEntity.convert(m));
        return converterToDTO.convertFullFamily(entity);
    }

    /* ************************************************************
        Relation Gestion
    ************************************************************ */

    @Override
    public List<RelationDTO> addRelation(RelationDTO relation) throws TooManyNullFieldsException, AscendanceViolationException, IncorrectStatusException, NotExistingMemberException {
        RelationEntity relationEntity = converterToEntity.convert(relation);
        List<RelationEntity> list = projectService.addRelation(relationEntity);
        List<RelationDTO> target = converterToDTO.convertFullRelationList(list);
        return target;
    }

    @Override
    public List<RelationDTO> updateRelation(RelationDTO relation) {
        RelationEntity relationEntity = converterToEntity.convert(relation);
        List<RelationEntity> list = projectService.updateRelation(relationEntity);
        return converterToDTO.convertFullRelationList(list);
    }

    @Override
    public List<RelationDTO> deleteRelation(RelationDTO relation) {
        RelationEntity relationEntity = converterToEntity.convert(relation);
        projectService.deleteRelation(relationEntity);
        return converterToDTO.convertFullRelationList(projectService.findFullFamilyById(relation.getFamily().getId()).getRelations());
    }

}
