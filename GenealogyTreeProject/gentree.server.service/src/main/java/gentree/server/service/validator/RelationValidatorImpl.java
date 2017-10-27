package gentree.server.service.validator;

import gentree.common.configuration.enums.RelationType;
import gentree.exception.AscendanceViolationException;
import gentree.exception.DescendanceViolationException;
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
        isAscOf(relation.getLeft(), relation.getRight(), family.getRelations());
        isAscOf(relation.getRight(), relation.getLeft(), family.getRelations());

        if(! relation.getChildren().isEmpty()) {
            for(MemberEntity child : relation.getChildren()) {
               // checkAsc(relation.getLeft(), );
            }
        }

    }

    private boolean isAscOf(MemberEntity grain, MemberEntity sim, List<RelationEntity> list)
            throws AscendanceViolationException {
        if (grain == null || sim == null) return false;

        RelationEntity r = findBornRelation(grain, list);
        if (r.compareLeft(sim) || isAscOf(r.getLeft(), sim, list)) throw new AscendanceViolationException();
        if (r.compareRight(sim) || isAscOf(r.getRight(), sim, list)) throw new AscendanceViolationException();

        return false;
    }


    private boolean isDescOf(MemberEntity grain, MemberEntity sim, List<RelationEntity> relations)
            throws DescendanceViolationException {
        if (grain == null || sim == null) return false;

        List<RelationEntity> list = relations.stream()
                .filter(r -> r.compareLeft(grain) || r.compareRight(grain))
                .collect(Collectors.toList());


        for (RelationEntity r : list) {
            if (r.getChildren().contains(sim)) throw new DescendanceViolationException();

            for (MemberEntity m : r.getChildren()) {
                if (isDescOf(m, sim, relations)) throw new DescendanceViolationException();
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
