package com.genealogytree.server.facade.converters;

import com.genealogytree.domain.dto.*;
import com.genealogytree.persist.entity.modules.administration.UserEntity;
import com.genealogytree.persist.entity.modules.tree.FamilyEntity;
import com.genealogytree.persist.entity.modules.tree.ImagesEntity;
import com.genealogytree.persist.entity.modules.tree.MemberEntity;
import com.genealogytree.persist.entity.modules.tree.RelationsEntity;

import java.util.List;

/**
 * Created by Martyna SZYMKOWIAK on 18/03/2017.
 */
public interface ConverterEntityToDto {

    UserDTO convert(UserEntity entity);

    FamilyDTO convert(FamilyEntity entity);

    MemberDTO convert(MemberEntity entity);

    RelationDTO convert(RelationsEntity entity);

    ImageDTO convert(ImagesEntity entity);


    /*
        MULTIPLE
     */

    List<UserDTO> convertUserList(List<UserEntity> list);

    List<MemberDTO> convertMemberList(List<MemberEntity> list);

    List<RelationDTO> convertRelationList(List<RelationsEntity> list);
}
