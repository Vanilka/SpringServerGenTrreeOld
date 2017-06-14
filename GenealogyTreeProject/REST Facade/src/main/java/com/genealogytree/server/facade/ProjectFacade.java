package genealogytree.server.facade;


import genealogytree.ExceptionManager.exception.*;
import genealogytree.domain.dto.FamilyDTO;
import genealogytree.domain.dto.MemberDTO;
import genealogytree.domain.dto.RelationDTO;
import genealogytree.domain.dto.UserDTO;
import genealogytree.domain.wrappers.CreatedMemberWrapper;

import java.util.List;

/**
 * Created by Martyna SZYMKOWIAK on 18/03/2017.
 */
public interface ProjectFacade {


    /*
        GET
     */
    List<FamilyDTO> getFamillies(UserDTO user);

    List<MemberDTO> getMembers(FamilyDTO owner) throws NotFoundFamilyException;

    List<RelationDTO> getRelations(Long id) throws NotFoundFamilyException;

    FamilyDTO getFamily(Long id) throws NotFoundFamilyException;

    /*
        PERSIST
     */
    FamilyDTO addFamily(FamilyDTO family);

    CreatedMemberWrapper addMember(MemberDTO member) throws IncorrectSex, IntegrationViolation, TooManyNullFields, IncorrectStatus, NoValidMembers;

    List<RelationDTO> addRelation(RelationDTO relation) throws IncorrectSex, NoValidMembers, IntegrationViolation, IncorrectStatus, TooManyNullFields;

    /*
        UPDATE
     */
    FamilyDTO updateFamily(FamilyDTO family);


}
