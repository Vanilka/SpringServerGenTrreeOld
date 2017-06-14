package genealogytree.server.facade.converters.implementation;

import genealogytree.domain.dto.*;
import genealogytree.persist.entity.modules.administration.UserEntity;
import genealogytree.persist.entity.modules.tree.FamilyEntity;
import genealogytree.persist.entity.modules.tree.ImagesEntity;
import genealogytree.persist.entity.modules.tree.MemberEntity;
import genealogytree.persist.entity.modules.tree.RelationsEntity;
import genealogytree.server.facade.converters.ConverterDtoToEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martyna SZYMKOWIAK on 18/03/2017.
 */
@Component
public class ConverterDtoToEntityImpl implements ConverterDtoToEntity {

    @Override
    public UserEntity convert(UserDTO dto) {
        UserEntity entity = new UserEntity();
        if(dto.getVersion() != null) {
            entity.setVersion(dto.getVersion());
        }
        entity.setId(dto.getId());
        entity.setEmail(dto.getEmail());
        entity.setLogin(dto.getLogin());
        entity.setPassword(dto.getPassword());
        return entity;
    }

    @Override
    public FamilyEntity convert(FamilyDTO dto) {
        FamilyEntity entity = new FamilyEntity();
        entity.setVersion(dto.getVersion());
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setOwner(dto.getOwner() == null ? null : convert(dto.getOwner()));
        return entity;
    }

    @Override
    public MemberEntity convert(MemberDTO dto) {
        MemberEntity entity = new MemberEntity();
        entity.setVersion(dto.getVersion());
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setAge(dto.getAge());
        entity.setSex(dto.getSex());
        entity.setOwnerF(dto.getOwnerF() == null ? null : convert(dto.getOwnerF()));
        entity.setImage(dto.getImage() == null ? null : convert(dto.getImage()));
        return entity;
    }


    @Override
    public RelationsEntity convert(RelationDTO dto) {
        RelationsEntity entity = new RelationsEntity();
        entity.setVersion(dto.getVersion());
        entity.setId(dto.getId());
        entity.setOwnerF(convert(dto.getOwnerF()));
        entity.setActive(dto.isActive());
        entity.setRelationType(dto.getRelationType());
        entity.setSimLeft(dto.getSimLeft() == null ? null : convert(dto.getSimLeft()));
        entity.setSimRight(dto.getSimRight() == null ? null : convert(dto.getSimRight()));
        entity.setChildren(convertMemberList(dto.getChildren()));
        return entity;
    }

    @Override
    public ImagesEntity convert(ImageDTO dto) {
        ImagesEntity entity = new ImagesEntity();
        entity.setVersion(dto.getVersion());
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setContent(dto.getContent());
        return entity;
    }

    /*
        MULTIPLE
     */

    @Override
    public List<UserEntity> convertUserList(List<UserDTO> list) {
        List<UserEntity> result = new ArrayList<>();
        list.forEach(user -> result.add(convert(user)));
        return result;
    }

    @Override
    public List<MemberEntity> convertMemberList(List<MemberDTO> list) {
        List<MemberEntity> result = new ArrayList<>();
        list.forEach(member -> result.add(convert(member)));
        return result;
    }

    @Override
    public List<RelationsEntity> convertRelationList(List<RelationDTO> list) {
        List<RelationsEntity> result = new ArrayList<>();
        list.forEach(relation ->  result.add(convert(relation)));
        return result;
    }
}
