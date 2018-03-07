package gentree.server.facade.implementation;

import gentree.exception.*;
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
    public FamilyDTO updateFamily(FamilyDTO temp, OwnerDTO ownerDTO) {
        OwnerEntity ownerEntity = ownerService.findOperatorByLogin(ownerDTO.getLogin());
        FamilyEntity family = converterToEntity.convert(temp);
        family.setOwner(ownerEntity);
        family = projectService.updateFamily(family);
        return converterToDTO.convertPoor(family);
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

        if (member.getPhotoDTO() != null) {
            PhotoEntity photoEntity = new PhotoEntity();
            photoEntity.setOwner(wrapper.getMember());
            photoEntity.setEncodedStringPhoto(member.getPhotoDTO().getEncodedPicture());
            photoEntity = photoService.persistPhoto(photoEntity);
            wrapper.getMember().setPhoto(photoEntity);
        }

        NewMemberDTO dto = new NewMemberDTO();
        dto.setMemberDTO(converterToDTO.convertWithPhoto(wrapper.getMember()));
        dto.setRelationDTO(converterToDTO.convert(wrapper.getBornRelation()));
        return dto;
    }

    @Override
    public MemberDTO updateMember(MemberDTO member) {
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

    @Override
    public MemberDTO getMemberById(Long id) {

        return converterToDTO.convertWithPhoto(projectService.getMemberById(id));
    }


    /* ************************************************************
        Relation Gestion
    ************************************************************ */

    @Override
    public List<RelationDTO> addRelation(RelationDTO relation) throws TooManyNullFieldsException, AscendanceViolationException, IncorrectStatusException, NotExistingMemberException, NotExistingRelationException {
        RelationEntity relationEntity = converterToEntity.convert(relation);
        List<RelationEntity> list = projectService.addRelation(relationEntity);
        List<RelationDTO> target = converterToDTO.convertFullRelationList(list);
        return target;
    }

    @Override
    public List<RelationDTO> updateRelation(RelationDTO relation) throws NotExistingRelationException {
        RelationEntity relationEntity = converterToEntity.convert(relation);
        List<RelationEntity> list = projectService.updateRelation(relationEntity);
        List<RelationDTO> target = converterToDTO.convertFullRelationList(list);
        return target;
    }

    @Override
    public List<RelationDTO> deleteRelation(RelationDTO relation) {
        RelationEntity relationEntity = converterToEntity.convert(relation);
        projectService.deleteRelation(relationEntity);
        return converterToDTO.convertFullRelationList(projectService.findFullFamilyById(relation.getFamily().getId()).getRelations());
    }

}
