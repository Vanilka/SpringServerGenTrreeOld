package gentree.server.facade;

import gentree.server.dto.FamilyDTO;
import gentree.server.dto.OwnerDTO;

import java.util.List;

/**
 * Created by Martyna SZYMKOWIAK on 18/10/2017.
 */
public interface FamilyFacade {

    /* ************************************************************
        Family Gestion
    ************************************************************ */
    FamilyDTO addNewFamily(FamilyDTO familyDTO);

    FamilyDTO findFamilyById(Long id);

    List<FamilyDTO> findAllFamiliesByOwner(OwnerDTO ownerDTO);

    /* ************************************************************
        Member Gestion
    ************************************************************ */

    /* ************************************************************
        Relation Gestion
    ************************************************************ */
}
