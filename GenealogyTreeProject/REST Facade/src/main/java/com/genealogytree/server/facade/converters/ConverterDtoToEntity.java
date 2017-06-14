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
public interface ConverterDtoToEntity {

    UserEntity convert(UserDTO dto);

    FamilyEntity convert(FamilyDTO dto);

    MemberEntity convert(MemberDTO dto);

    RelationsEntity convert(RelationDTO dto);

    ImagesEntity convert(ImageDTO dto);

    /*
        MULTIPLE
     */

    List<UserEntity> convertUserList(List<UserDTO> list);

    List<MemberEntity> convertMemberList(List<MemberDTO> list);

    List<RelationsEntity> convertRelationList(List<RelationDTO> list);
}
