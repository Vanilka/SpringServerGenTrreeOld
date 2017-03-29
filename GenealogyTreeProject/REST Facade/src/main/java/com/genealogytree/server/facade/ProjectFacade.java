package com.genealogytree.server.facade;


import com.genealogytree.ExceptionManager.exception.*;
import com.genealogytree.domain.dto.FamilyDTO;
import com.genealogytree.domain.dto.MemberDTO;
import com.genealogytree.domain.dto.RelationDTO;
import com.genealogytree.domain.dto.UserDTO;
import com.genealogytree.domain.wrappers.CreatedMemberWrapper;

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
