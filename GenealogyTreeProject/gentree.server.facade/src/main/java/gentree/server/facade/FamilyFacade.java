package gentree.server.facade;

import gentree.exception.*;
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

    MemberDTO updateMember(MemberDTO member);

    FamilyDTO deleteMember(MemberDTO m);


    /* ************************************************************
        Relation Gestion
    ************************************************************ */
    List<RelationDTO> addRelation(RelationDTO relation) throws TooManyNullFieldsException, AscendanceViolationException, IncorrectStatusException, NotExistingMemberException, NotExistingRelationException;

    List<RelationDTO> updateRelation(RelationDTO relation) throws NotExistingRelationException;

    List<RelationDTO> deleteRelation(RelationDTO relationDTO);

    MemberDTO getMemberById(Long id);
}

