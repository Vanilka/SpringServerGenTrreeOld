package gentree.server.facade.converter;

import gentree.server.domain.entity.FamilyEntity;
import gentree.server.domain.entity.MemberEntity;
import gentree.server.domain.entity.OwnerEntity;
import gentree.server.dto.FamilyDTO;
import gentree.server.dto.MemberDTO;
import gentree.server.dto.OwnerDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martyna SZYMKOWIAK on 16/10/2017.
 */
public enum ConverterToDTO {
    INSTANCE;


    /* ************************************************************
        Owner Conversion
    ************************************************************ */

    public OwnerDTO convert(OwnerEntity source) {
        if(source == null) return null;

        OwnerDTO target = new OwnerDTO();
        target.setVersion(source.getVersion());
        target.setId(source.getId());
        target.setLogin(source.getLogin());
        target.setRole(source.getRole());

        if(source.getFamilyList().size() > 0) {
            source.getFamilyList().forEach(family -> target.getFamilyList().add(lazyConvert(family)));
        }

        return target;
    }

    public OwnerDTO convertWithPassword(OwnerEntity source) {
        if(source == null) return null;
        OwnerDTO target = new OwnerDTO();
        target.setVersion(source.getVersion());
        target.setId(source.getId());
        target.setLogin(source.getLogin());
        target.setRole(source.getRole());
        target.setPassword(source.getPassword());
        return target;
    }

    public List<OwnerDTO> convertOwnerList(List<OwnerEntity> sourceList) {
        List<OwnerDTO> targetList = new ArrayList<>();
        sourceList.forEach(entity -> targetList.add(convert(entity)));
        return targetList;
    }

    /* ************************************************************
        Family Conversion
    ************************************************************ */
    public FamilyDTO convert(FamilyEntity source) {
        if(source == null) return null;
        FamilyDTO target = lazyConvert(source);
        return target;
    }

    public FamilyDTO lazyConvert(FamilyEntity source) {
        if(source == null) return null;
        FamilyDTO target = new FamilyDTO();
        target.setVersion(source.getVersion());
        target.setId(source.getId());
        target.setName(source.getName());
        return target;
    }

    public List<FamilyDTO> convertFamilyList(List<FamilyEntity> sourceList) {
        List<FamilyDTO> targetList = new ArrayList<>();
        sourceList.forEach(entity -> targetList.add(lazyConvert(entity)));
        return targetList;
    }
    /* ************************************************************
        Member Conversion
    ************************************************************ */

    public MemberDTO convert(MemberEntity source) {
        if (source == null) return null;
        MemberDTO target = new MemberDTO();

        target.setVersion(source.getVersion());
        target.setId(source.getId());
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



    /* ************************************************************
        Relation Conversion
    ************************************************************ */
}