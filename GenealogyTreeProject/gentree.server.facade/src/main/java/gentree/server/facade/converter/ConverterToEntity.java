package gentree.server.facade.converter;

import gentree.server.domain.entity.FamilyEntity;
import gentree.server.domain.entity.MemberEntity;
import gentree.server.domain.entity.OwnerEntity;
import gentree.server.domain.entity.RelationEntity;
import gentree.server.dto.FamilyDTO;
import gentree.server.dto.MemberDTO;
import gentree.server.dto.OwnerDTO;
import gentree.server.dto.RelationDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martyna SZYMKOWIAK on 16/10/2017.
 * <p>
 * Class to Covert DTO objects to Entity.
 * Validation of null is not prevue at this level. It is realise by ConverterValidator Bean
 */
@Component
public class ConverterToEntity {

    /* ************************************************************
        Family Conversion
    ************************************************************ */

    /**
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

    public MemberEntity convertLazy(MemberDTO source) {
        MemberEntity target = new MemberEntity();
        target.setVersion(source.getVersion());
        target.setId(source.getId());
        return target;
    }

    public MemberEntity convert(MemberDTO source) {
        MemberEntity target = convertLazy(source);
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

    public List<MemberEntity> convertLazyMemberList(List<MemberDTO>  sourceList) {
        List<MemberEntity> targetList = new ArrayList<>();
        sourceList.forEach(child -> targetList.add(convertLazy(child)));
        return targetList;
    }


    /* ************************************************************
        Relation Conversion
    ************************************************************ */

    public RelationEntity convertLazy(RelationDTO source) {
        RelationEntity target = new RelationEntity();
        target.setVersion(source.getVersion());
        target.setId(source.getId());
        return target;
    }

    public RelationEntity convert(RelationDTO source) {
        RelationEntity target = convertLazy(source);
        target.setType(source.getType());
        target.setActive(source.isActive());
        target.setFamily(convert(source.getFamily()));
        if (source.getLeft() != null) target.setLeft(convertLazy(source.getLeft()));
        if (source.getRight() != null) target.setLeft(convertLazy(source.getRight()));
        if (source.getChildren() != null && !source.getChildren().isEmpty()) target.getChildren().addAll(convertLazyMemberList(source.getChildren()));
        return target;
    }

}
