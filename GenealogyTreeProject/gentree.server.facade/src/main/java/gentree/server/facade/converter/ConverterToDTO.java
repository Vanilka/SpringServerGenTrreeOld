package gentree.server.facade.converter;

import gentree.server.domain.entity.*;
import gentree.server.dto.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martyna SZYMKOWIAK on 16/10/2017.
 */
@Component
public class ConverterToDTO {


    /* ************************************************************
        Owner Conversion
    ************************************************************ */


    public OwnerDTO convert(OwnerEntity source, boolean extendedConvert) {
        if (source == null) return null;

        OwnerDTO target = extendedConvert ? new OwnerExtendedDTO() : new OwnerDTO();
        target.setVersion(source.getVersion());
        target.setId(source.getId());
        target.setLogin(source.getLogin());
        target.setRole(source.getRole());

        if (extendedConvert && source.getFamilyList().size() > 0) {
            source.getFamilyList().forEach(family -> ((OwnerExtendedDTO) target).getFamilyList().add(convertPoor(family)));
        }

        return target;
    }


    /**
     * Poor conversion of OwnerEntity convert only fields but not collections
     *
     * @param source
     * @return
     */
    public OwnerDTO convertPoor(OwnerEntity source) {
        OwnerDTO target = convertLazy(source);
        target.setLogin(source.getLogin());
        target.setRole(source.getRole());
        return target;
    }


    /**
     * Lazy conversion of OwnerEntity convert only Verion and Id
     *
     * @param source
     * @return
     */
    public OwnerDTO convertLazy(OwnerEntity source) {
        OwnerDTO target = new OwnerDTO();
        target.setVersion(source.getVersion());
        target.setId(source.getId());
        return target;
    }

    /**
     * Conversion with password to authentication process.
     * That is not recommended to use it with others contexts
     *
     * @param source
     * @return
     */
    public OwnerDTO convertWithPassword(OwnerEntity source) {
        if (source == null) return null;
        OwnerDTO target = convertPoor(source);
        target.setPassword(source.getPassword());
        return target;
    }


    /**
     * Convert owners List
     *
     * @param sourceList
     * @return
     */
    public List<OwnerDTO> convertOwnerList(List<OwnerEntity> sourceList) {
        List<OwnerDTO> targetList = new ArrayList<>();
        sourceList.forEach(entity -> targetList.add(convert(entity, false)));
        return targetList;
    }

    /* ************************************************************
        Family Conversion
    ************************************************************ */

    public FamilyDTO convertFullFamily(FamilyEntity source) {
        FamilyDTO target = convert(source);
        target.setMembers(convertMemberWithPhotoList(source.getMembers()));
        target.setRelations(convertFullRelationList(source.getRelations()));
        return target;
    }

    public FamilyDTO convert(FamilyEntity source) {
        FamilyDTO target = convertPoor(source);
        target.setOwner(convertLazy(source.getOwner()));
        return target;
    }


    public FamilyDTO convertPoor(FamilyEntity source) {
        if (source == null) return null;
        FamilyDTO target = new FamilyDTO();
        target.setVersion(source.getVersion());
        target.setId(source.getId());
        target.setName(source.getName());
        return target;
    }

    public FamilyDTO convertLazy(FamilyEntity source) {
        FamilyDTO target = new FamilyDTO();
        // target.setVersion(source.getVersion());
        target.setId(source.getId());
        return target;
    }

    public List<FamilyDTO> convertFamilyList(List<FamilyEntity> sourceList) {
        List<FamilyDTO> targetList = new ArrayList<>();
        sourceList.forEach(entity -> targetList.add(convertPoor(entity)));
        return targetList;
    }
    /* ************************************************************
        Member Conversion
    ************************************************************ */

    public MemberDTO convert(MemberEntity source) {
        MemberDTO target = convertPoor(source);
        target.setFamily(convertLazy(source.getFamily()));
        return target;
    }

    public MemberDTO convertWithPhoto(MemberEntity source) {
        MemberDTO target = convertPoor(source);
        if (source.getPhoto() != null) {
            target.setPhotoDTO(convert(source.getPhoto()));
        }
        return target;
    }

    public MemberDTO convertLazy(MemberEntity source) {
        MemberDTO target = new MemberDTO();
        target.setVersion(source.getVersion());
        target.setId(source.getId());
        return target;
    }

    public MemberDTO convertPoor(MemberEntity source) {
        MemberDTO target = convertLazy(source);
        target.setName(source.getName());
        target.setSurname(source.getSurname());
        target.setBornname(source.getBornname());
        target.setAlive(source.isAlive());
        target.setAge(source.getAge());
        target.setRace(source.getRace());
        target.setDeathCauses(source.getDeathCauses());
        target.setGender(source.getGender());
        return target;
    }


    public List<MemberDTO> convertMemberList(List<MemberEntity> sourceList) {
        List<MemberDTO> list = new ArrayList<>();
        sourceList.forEach(entity -> list.add(convertPoor(entity)));
        return list;
    }

    public List<MemberDTO> convertMemberWithPhotoList(List<MemberEntity> sourceList) {
        List<MemberDTO> list = new ArrayList<>();
        sourceList.forEach(entity -> list.add(convertWithPhoto(entity)));
        return list;
    }

    /* ************************************************************
        Relation Conversion
    ************************************************************ */
    public RelationDTO convertLazy(RelationEntity source) {
        RelationDTO target = new RelationDTO();
        target.setVersion(source.getVersion());
        target.setId(source.getId());
        return target;
    }

    public RelationDTO convertPoor(RelationEntity source) {
        RelationDTO target = convertLazy(source);
        target.setActive(source.isActive());
        target.setType(source.getType());
        return target;
    }

    public RelationDTO convert(RelationEntity source) {
        RelationDTO target = convertPoor(source);
        target.setFamily(convertLazy(source.getFamily()));
        if (source.getLeft() != null) target.setLeft(convertLazy(source.getLeft()));
        if (source.getRight() != null) target.setRight(convertLazy(source.getRight()));
        if (!source.getChildren().isEmpty()) target.setChildren(convertMemberList(source.getChildren()));
        return target;
    }

    public List<RelationDTO> convertFullRelationList(List<RelationEntity> sourceList) {
        List<RelationDTO> list = new ArrayList<>();
        sourceList.forEach(entity -> list.add(convert(entity)));
        return list;
    }


    public PhotoDTO convert(PhotoEntity source) {
        PhotoDTO target = new PhotoDTO();
        target.setId(source.getId());
        target.setVersion(source.getVersion());
        target.setName(source.getPhoto());
        target.setEncodedPicture(source.getEncodedStringPhoto());
        return target;
    }

}
