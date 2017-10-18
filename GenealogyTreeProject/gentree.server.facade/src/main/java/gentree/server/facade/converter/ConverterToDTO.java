package gentree.server.facade.converter;

import gentree.server.domain.entity.FamilyEntity;
import gentree.server.domain.entity.OwnerEntity;
import gentree.server.dto.FamilyDTO;
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
        return target;
    }

    public OwnerDTO convertWithPassword(OwnerEntity source) {
        if(source == null) return null;

        OwnerDTO target = convert(source);
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
        FamilyDTO target = new FamilyDTO();
        target.setVersion(source.getVersion());
        target.setId(source.getId());
        target.setName(source.getName());
        target.setOwner(convert(source.getOwner()));
        return target;
    }

    public List<FamilyDTO> convertFamilyList(List<FamilyEntity> sourceList) {
        List<FamilyDTO> targetList = new ArrayList<>();
        sourceList.forEach(entity -> targetList.add(convert(entity)));
        return targetList;
    }
    /* ************************************************************
        Member Conversion
    ************************************************************ */

    /* ************************************************************
        Relation Conversion
    ************************************************************ */
}