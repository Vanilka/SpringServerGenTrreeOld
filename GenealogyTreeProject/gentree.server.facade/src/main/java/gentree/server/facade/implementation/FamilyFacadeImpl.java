package gentree.server.facade.implementation;

import gentree.server.domain.entity.FamilyEntity;
import gentree.server.domain.entity.OwnerEntity;
import gentree.server.dto.FamilyDTO;
import gentree.server.dto.OwnerDTO;
import gentree.server.facade.FamilyFacade;
import gentree.server.facade.converter.ConverterToDAO;
import gentree.server.facade.converter.ConverterToDTO;
import gentree.server.service.FamilyService;
import gentree.server.service.MemberService;
import gentree.server.service.RelationService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by Martyna SZYMKOWIAK on 18/10/2017.
 */
@Setter
public class FamilyFacadeImpl implements FamilyFacade {

    @Autowired
    FamilyService familyService;

    @Autowired
    MemberService memberService;

    @Autowired
    RelationService relationService;

    /* ************************************************************
        Family Gestion
    ************************************************************ */

    @Override
    public FamilyDTO addNewFamily(FamilyDTO familyDTO) {
        if (familyDTO.getOwner() == null) return null;

        FamilyEntity newFamily = familyService.addNewFamily(ConverterToDAO.INSTANCE.convert(familyDTO));

        return ConverterToDTO.INSTANCE.convert(newFamily);
    }

    @Override
    public FamilyDTO findFamilyById(Long id) {

        FamilyEntity result = familyService.findFamilyById(id);

        return ConverterToDTO.INSTANCE.convert(result);
    }


    @Override
    public List<FamilyDTO> findAllFamiliesByOwner(OwnerDTO ownerDTO) {

        OwnerEntity owner = ConverterToDAO.INSTANCE.convert(ownerDTO);

        List<FamilyEntity> list = familyService.findAllByOwner(owner);

        return ConverterToDTO.INSTANCE.convertFamilyList(list);
    }

    /* ************************************************************
        Member Gestion
    ************************************************************ */

    /* ************************************************************
        Relation Gestion
    ************************************************************ */
}
