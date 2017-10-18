package gentree.server.facade.converter;

import gentree.server.domain.entity.FamilyEntity;
import gentree.server.domain.entity.OwnerEntity;
import gentree.server.dto.FamilyDTO;
import gentree.server.dto.OwnerDTO;

/**
 * Created by Martyna SZYMKOWIAK on 16/10/2017.
 */
public enum ConverterToDAO {
    INSTANCE;

    /* ************************************************************
        Family Conversion
    ************************************************************ */

    public OwnerEntity convert(OwnerDTO source) {
        if(source == null) return null;
        OwnerEntity target = new OwnerEntity();
        target.setVersion(source.getVersion());
        target.setId(source.getId());
        target.setLogin(source.getLogin());
        target.setRole(source.getRole());
        return target;
    }

    public OwnerEntity convertWithPass(OwnerDTO source) {
        if(source == null) return null;
        OwnerEntity target = this.convert(source);
        target.setPassword(source.getPassword());
        return target;
    }

    /* ************************************************************
        Family Conversion
    ************************************************************ */

    public FamilyEntity convert(FamilyDTO source) {
        if(source == null) return null;
        FamilyEntity target = new FamilyEntity();
        target.setVersion(source.getVersion());
        target.setId(source.getId());
        target.setName(source.getName());
        target.setOwner(convert(source.getOwner()));
        return target;
    }

    /* ************************************************************
        Member Conversion
    ************************************************************ */

    /* ************************************************************
        Relation Conversion
    ************************************************************ */

}
