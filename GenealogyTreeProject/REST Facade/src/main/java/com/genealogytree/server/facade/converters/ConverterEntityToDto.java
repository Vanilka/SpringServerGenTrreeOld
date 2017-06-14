package genealogytree.server.facade.converters;

import genealogytree.domain.dto.*;
import genealogytree.persist.entity.modules.administration.UserEntity;
import genealogytree.persist.entity.modules.tree.FamilyEntity;
import genealogytree.persist.entity.modules.tree.ImagesEntity;
import genealogytree.persist.entity.modules.tree.MemberEntity;
import genealogytree.persist.entity.modules.tree.RelationsEntity;

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
