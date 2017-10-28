package gentree.server.facade;

import gentree.server.dto.*;

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

    FamilyDTO findExtendedFamilyById(Long id);

    List<FamilyDTO> findAllFamiliesByOwner(OwnerDTO ownerDTO);

    /* ************************************************************
        Member Gestion
    ************************************************************ */
    NewMemberDTO addNewMember(MemberDTO member);

    FamilyDTO deleteMember(MemberDTO m);


    /* ************************************************************
        Relation Gestion
    ************************************************************ */
    List<RelationDTO> addRelation(RelationDTO relation);

    List<RelationDTO> updateRelation(RelationDTO relation);

    List<RelationDTO> deleteRelation(RelationDTO relationDTO);

}
