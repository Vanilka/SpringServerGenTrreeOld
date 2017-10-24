package gentree.server.facade.converter;

import gentree.server.domain.entity.FamilyEntity;
import gentree.server.domain.entity.MemberEntity;
import gentree.server.domain.entity.OwnerEntity;
import gentree.server.dto.FamilyDTO;
import gentree.server.dto.MemberDTO;
import gentree.server.dto.OwnerDTO;
import org.springframework.stereotype.Component;

/**
 * Created by Martyna SZYMKOWIAK on 16/10/2017.
 *
 * Class to Covert DTO objects to Entity.
 * Validation of null is not prevue at this level. It is realise by ConverterValidator Bean
 *
 */
@Component
public class ConverterToEntity {

    /* ************************************************************
        Family Conversion
    ************************************************************ */

    /**
     *
     * @param source
     * @return
     */
    public OwnerEntity convert(OwnerDTO source) {
        OwnerEntity target = new OwnerEntity();
        target.setVersion(source.getVersion());
        target.setId(source.getId());
        target.setLogin(source.getLogin());
        target.setRole(source.getRole());
        return target;
    }

    public OwnerEntity convertWithPass(OwnerDTO source) {
        OwnerEntity target = this.convert(source);
        target.setPassword(source.getPassword());
        return target;
    }

    /* ************************************************************
        Family Conversion
    ************************************************************ */

    public FamilyEntity convert(FamilyDTO source) {
        FamilyEntity target = new FamilyEntity();
        target.setVersion(source.getVersion());
        target.setId(source.getId());
        target.setName(source.getName());
        return target;
    }

    /* ************************************************************
        Member Conversion
    ************************************************************ */

    public MemberEntity convert(MemberDTO source) {
        MemberEntity target = new MemberEntity();
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
        target.setFamily(convert(source.getFamily()));
        return target;
    }


    /* ************************************************************
        Relation Conversion
    ************************************************************ */

}
