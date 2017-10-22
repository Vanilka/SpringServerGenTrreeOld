package gentree.server.facade.implementation;

import gentree.server.domain.entity.FamilyEntity;
import gentree.server.domain.entity.OwnerEntity;
import gentree.server.dto.FamilyDTO;
import gentree.server.dto.MemberDTO;
import gentree.server.dto.OwnerDTO;
import gentree.server.facade.FamilyFacade;
import gentree.server.facade.converter.ConverterToEntity;
import gentree.server.facade.converter.ConverterToDTO;
import gentree.server.service.*;
import lombok.Setter;
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
    OwnerService ownerService;

    /* ************************************************************
        Family Gestion
    ************************************************************ */

    @Override
    public FamilyDTO addNewFamily(FamilyDTO familyDTO, OwnerDTO ownerDTO) {

        OwnerEntity ownerEntity = ownerService.findOperatorByLogin(ownerDTO.getLogin());

        FamilyEntity newFamily = ConverterToEntity.INSTANCE.convert(familyDTO);
        newFamily.setOwner(ownerEntity);

        newFamily = projectService.addFamily(newFamily);

        return ConverterToDTO.INSTANCE.convert(newFamily);
    }

    @Override
    public FamilyDTO findFamilyById(Long id) {

        FamilyEntity result = projectService.findFamilyById(id);

        return ConverterToDTO.INSTANCE.convert(result);
    }


    @Override
    public List<FamilyDTO> findAllFamiliesByOwner(OwnerDTO ownerDTO) {

        OwnerEntity owner = ConverterToEntity.INSTANCE.convert(ownerDTO);

        List<FamilyEntity> list = projectService.findAllFamiliesByOwner(owner);

        return ConverterToDTO.INSTANCE.convertFamilyList(list);
    }

    /* ************************************************************
        Member Gestion
    ************************************************************ */

    @Override
    public FamilyDTO addNewMember(MemberDTO member) {
        return null;
    }

    /* ************************************************************
        Relation Gestion
    ************************************************************ */
}
