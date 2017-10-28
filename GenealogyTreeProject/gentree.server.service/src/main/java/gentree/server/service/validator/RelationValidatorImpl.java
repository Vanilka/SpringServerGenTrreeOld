package gentree.server.service.validator;

import gentree.common.configuration.enums.RelationType;
import gentree.exception.AscendanceViolationException;
import gentree.server.domain.entity.FamilyEntity;
import gentree.server.domain.entity.MemberEntity;
import gentree.server.domain.entity.RelationEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Martyna SZYMKOWIAK on 26/10/2017.
 */
@Component
public class RelationValidatorImpl implements RelationValidator {

    @Override
    public boolean validate(RelationEntity relation, FamilyEntity family) throws AscendanceViolationException {
        if (relation == null) return false;
        if (!checkNulls(relation)) return false;
        if (!checkStatus(relation)) return false;

        checkAsc(relation, family);

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


    private void checkAsc(RelationEntity relation, FamilyEntity family) throws AscendanceViolationException {
        if (isAscOf(relation.getLeft(), relation.getRight(), family.getRelations()))
            throw new AscendanceViolationException();
        if (isAscOf(relation.getRight(), relation.getLeft(), family.getRelations()))
            throw new AscendanceViolationException();

        if (!relation.getChildren().isEmpty()) {
            for (MemberEntity child : relation.getChildren()) {
                if (isAscOf(child, relation.getLeft(), family.getRelations()))
                    throw new AscendanceViolationException();
                if (isAscOf(child, relation.getRight(), family.getRelations()))
                    throw new AscendanceViolationException();
            }
        }

    }


    /**
     * Function checking if SIM is Ascendance (Parent) of GRAIN
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
     * Check if MemberEntity SIM is descendant of GRAIN
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

}
