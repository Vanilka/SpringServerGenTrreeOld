package gentree.server.service.validator;

import gentree.common.configuration.enums.RelationType;
import gentree.exception.AscendanceViolationException;
import gentree.exception.IncorrectStatusException;
import gentree.exception.NotExistingMemberException;
import gentree.exception.TooManyNullFieldsException;
import gentree.server.domain.entity.FamilyEntity;
import gentree.server.domain.entity.MemberEntity;
import gentree.server.domain.entity.RelationEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by Martyna SZYMKOWIAK on 26/10/2017.
 */
@Component
public class RelationValidatorImpl implements RelationValidator {


    /**
     * Will return true, if relation will pass all check OK. </br>
     * In case of error will generate exception.  </br>
     * If you attanding FALSE please refer to <b>validateSimple </b> method
     *
     * @param relation
     * @param family
     * @return true / exception
     * @throws AscendanceViolationException
     * @throws TooManyNullFieldsException
     * @throws IncorrectStatusException
     */
    @Override
    public boolean validate(RelationEntity relation, FamilyEntity family)
            throws AscendanceViolationException, TooManyNullFieldsException, IncorrectStatusException, NotExistingMemberException {
        if (relation == null) throw new NullPointerException();
        if (!checkNulls(relation)) throw new TooManyNullFieldsException();
        if (!checkExistingSims(relation, family)) throw new NotExistingMemberException();
        if (!checkStatus(relation)) throw new IncorrectStatusException();
        if (!checkAsc(relation, family)) throw new AscendanceViolationException();

        return true;
    }


    /**
     * Will return true, if relation will pass all check OK. </br>
     *
     * @param relation
     * @param family
     * @return true/false
     */
    @Override
    public boolean validateSimple(RelationEntity relation, FamilyEntity family) {
        if (relation == null) return false;
        if (!checkNulls(relation)) return false;
        if (!checkStatus(relation)) return false;

        if (!checkAsc(relation, family)) return false;

        return true;
    }

    private boolean checkNulls(RelationEntity relation) {
        if (relation == null) return false;
        if ((relation.getLeft() == null || relation.getRight() == null) && relation.getChildren().isEmpty())
            return false;
        return true;
    }


    private boolean checkStatus(RelationEntity relation) {
        if ((relation.getLeft() == null || relation.getRight() == null)
                && !relation.getType().equals(RelationType.NEUTRAL)
                && !relation.isActive()) return false;
        return true;
    }


    /**
     * Check Ascendance in relation.  </br>
     * <p>
     * False -  relation contains inappropriate connections beetween members </br>
     * True - relation is OK </br>
     *
     * @param relation
     * @param family
     * @return true/false
     */
    private boolean checkAsc(RelationEntity relation, FamilyEntity family) {
        if (isAscOf(relation.getLeft(), relation.getRight(), family.getRelations())) return false;
        if (isAscOf(relation.getRight(), relation.getLeft(), family.getRelations())) return false;

        if (!relation.getChildren().isEmpty()) {
            for (MemberEntity child : relation.getChildren()) {
                if (isAscOf(child, relation.getLeft(), family.getRelations())) return false;
                if (isAscOf(child, relation.getRight(), family.getRelations())) return false;
            }
        }

        return true;

    }


    /**
     * Function checking if SIM is Ascendance (Parent) of GRAIN
     * FALSE - Sim is NOT ascendant of GRAIN
     * TRUE - Sim is ascendant of GRAIN
     *
     * @param grain
     * @param sim
     * @param list
     * @return true/false
     */
    private boolean isAscOf(MemberEntity grain, MemberEntity sim, List<RelationEntity> list) {
        if (grain == null || sim == null) return false;

        RelationEntity r = findBornRelation(grain, list);
        if (r == null) return false;   // Maybe should return exception
        if (r.compareLeft(sim) || isAscOf(r.getLeft(), sim, list)) return true;
        if (r.compareRight(sim) || isAscOf(r.getRight(), sim, list)) return true;

        return false;
    }

    /**
     * Check if MemberEntity SIM is descendant of GRAIN </br>
     * FALSE - sim is NOT descendant of GRAIN </br>
     * TRUE - SIM is descendant of GRAIN </br>
     *
     * @param grain
     * @param sim
     * @param relations
     * @return true/false
     */
    private boolean isDescOf(MemberEntity grain, MemberEntity sim, List<RelationEntity> relations) {
        if (grain == null || sim == null) return false;

        List<RelationEntity> list = relations.stream()
                .filter(r -> r.compareLeft(grain) || r.compareRight(grain))
                .collect(Collectors.toList());


        for (RelationEntity r : list) {
            if (r.getChildren().contains(sim)) return true;

            for (MemberEntity m : r.getChildren()) {
                if (isDescOf(m, sim, relations)) return true;
            }
        }
        return false;
    }

    private RelationEntity findBornRelation(MemberEntity m, List<RelationEntity> list) {
        RelationEntity entity = list.stream().filter(relation -> relation.getChildren().contains(m)).findFirst().orElse(null);

        if (entity == null) {
            return null;
        }

        return entity;
    }


    private boolean checkExistingSims(RelationEntity relation, FamilyEntity family) {
        if (relation.getLeft() != null && !simExist(relation.getLeft(), family)) return false;
        if (relation.getRight() != null && !simExist(relation.getRight(), family)) return false;

        if (relation.getChildren() != null && !relation.getChildren().isEmpty()) {
            for (MemberEntity child : relation.getChildren()) {
                if (!simExist(child, family)) return false;
            }
        }

        return true;
    }

    private boolean simExist(MemberEntity member, FamilyEntity familyEntity) {
        return familyEntity.getMembers().stream().filter(element -> Objects.equals(element.getId(), member.getId())).count() > 0;
    }

}
