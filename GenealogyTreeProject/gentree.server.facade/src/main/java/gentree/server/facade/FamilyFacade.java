package gentree.server.facade;

import gentree.server.dto.FamilyDTO;
import gentree.server.dto.MemberDTO;
import gentree.server.dto.OwnerDTO;
import gentree.server.dto.RelationDTO;

import java.util.List;

/**
 * Created by Martyna SZYMKOWIAK on 18/10/2017.
 */
public interface FamilyFacade {

    /* ************************************************************
        Family Gestion
    ************************************************************ */
    FamilyDTO addNewFamily(FamilyDTO familyDTO, OwnerDTO ownerDTO);

    FamilyDTO findFamilyById(Long id);

    List<FamilyDTO> findAllFamiliesByOwner(OwnerDTO ownerDTO);

    /* ************************************************************
        Member Gestion
    ************************************************************ */
    FamilyDTO addNewMember(MemberDTO member);

    /* ************************************************************
        Relation Gestion
    ************************************************************ */
}
