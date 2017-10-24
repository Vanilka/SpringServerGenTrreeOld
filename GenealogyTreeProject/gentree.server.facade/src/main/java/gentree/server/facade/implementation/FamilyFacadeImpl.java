package gentree.server.facade.implementation;

import gentree.server.domain.entity.FamilyEntity;
import gentree.server.domain.entity.OwnerEntity;
import gentree.server.dto.FamilyDTO;
import gentree.server.dto.MemberDTO;
import gentree.server.dto.NewMemberDTO;
import gentree.server.dto.OwnerDTO;
import gentree.server.facade.FamilyFacade;
import gentree.server.facade.converter.ConverterToEntity;
import gentree.server.facade.converter.ConverterToDTO;
import gentree.server.service.*;
import gentree.server.service.wrappers.NewMemberWrapper;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by Martyna SZYMKOWIAK on 18/10/2017.
 */
@Service
public class FamilyFacadeImpl implements FamilyFacade {


    @Autowired
    ProjectService projectService;

    @Autowired
    OwnerService ownerService;

    @Autowired
    ConverterToDTO converterToDTO;

    @Autowired
    ConverterToEntity converterToEntity;

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
        NewMemberWrapper wrapper = projectService.addMember(converterToEntity.convert(member));

        NewMemberDTO dto = new NewMemberDTO();
        dto.setMemberDTO(converterToDTO.convert(wrapper.getMember()));
        dto.setRelationDTO(converterToDTO.convert(wrapper.getBornRelation()));

        return dto;
    }

    /* ************************************************************
        Relation Gestion
    ************************************************************ */
}
