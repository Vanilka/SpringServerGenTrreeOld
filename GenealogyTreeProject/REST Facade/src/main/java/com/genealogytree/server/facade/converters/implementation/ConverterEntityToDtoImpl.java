package genealogytree.server.facade.converters.implementation;

import genealogytree.domain.dto.*;
import genealogytree.persist.entity.modules.administration.UserEntity;
import genealogytree.persist.entity.modules.tree.FamilyEntity;
import genealogytree.persist.entity.modules.tree.ImagesEntity;
import genealogytree.persist.entity.modules.tree.MemberEntity;
import genealogytree.persist.entity.modules.tree.RelationsEntity;
import genealogytree.server.facade.converters.ConverterEntityToDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martyna SZYMKOWIAK on 18/03/2017.
 */
@Component
public class ConverterEntityToDtoImpl implements ConverterEntityToDto {

    @Override
    public UserDTO convert(UserEntity entity) {
        UserDTO dto = new UserDTO();
        if(entity.getVersion() != null) {
            dto.setVersion(entity.getVersion());
        }
        dto.setId(entity.getId());
        dto.setEmail(entity.getEmail());
        dto.setLogin(entity.getLogin());
        return dto;
    }

    @Override
    public FamilyDTO convert(FamilyEntity entity) {
        FamilyDTO dto = new FamilyDTO();
        dto.setVersion(entity.getVersion());
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setOwner(entity.getOwner() == null ? null : convert(entity.getOwner()));
        return dto;
    }

    @Override
    public MemberDTO convert(MemberEntity entity) {
        MemberDTO dto = new MemberDTO();
        dto.setVersion(entity.getVersion());
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setAge(entity.getAge());
        dto.setSex(entity.getSex());
        dto.setImage(entity.getImage() == null ? null : convert(entity.getImage()));
        dto.setOwnerF(entity.getOwnerF() == null ? null : convert(entity.getOwnerF()));
        return dto;
    }

    @Override
    public ImageDTO convert(ImagesEntity entity) {
        ImageDTO dto = new ImageDTO();
        dto.setVersion(entity.getVersion());
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setContent(entity.getContent());
        return dto;
    }

    @Override
    public RelationDTO convert(RelationsEntity entity) {
        RelationDTO dto = new RelationDTO();
        dto.setVersion(entity.getVersion());
        dto.setId(entity.getId());
        dto.setOwnerF(convert(entity.getOwnerF()));
        dto.setRelationType(entity.getRelationType());
        dto.setActive(entity.geActive());
        dto.setSimLeft(entity.getSimLeft() == null ? null : convert(entity.getSimLeft()));
        dto.setSimRight(entity.getSimRight() == null ? null : convert(entity.getSimRight()));
        dto.setChildren(convertMemberList(entity.getChildren()));
        return dto;
    }

    /*
        MULTIPLE
     */

    @Override
    public List<UserDTO> convertUserList(List<UserEntity> list) {
        List<UserDTO> result = new ArrayList<>();
        list.forEach(user -> result.add(convert(user)));
        return result;
    }

    @Override
    public List<MemberDTO> convertMemberList(List<MemberEntity> list) {
        List<MemberDTO> result = new ArrayList<>();
        list.forEach(member -> result.add(convert(member)));
        return result;
    }

    @Override
    public List<RelationDTO> convertRelationList(List<RelationsEntity> list) {
        List<RelationDTO> result = new ArrayList<>();
        list.forEach(relation -> result.add(convert(relation)));
        return result;
    }
}
