package genealogytree.service.validators.implementation;

import genealogytree.domain.enums.RelationType;
import genealogytree.domain.enums.Sex;
import genealogytree.persist.entity.modules.tree.MemberEntity;
import genealogytree.persist.entity.modules.tree.RelationsEntity;
import genealogytree.service.MemberService;
import genealogytree.service.validators.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Martyna SZYMKOWIAK on 23/03/2017.
 */
@Service
public class ValidatorImpl implements Validator {

    @Autowired
    MemberService memberService;
    /*
       VALIDATION
    */

    @Override
    public boolean validStatus(RelationsEntity relationsEntity) {
        if ((relationsEntity.getSimLeft() == null || relationsEntity.getSimRight() == null)
                && !relationsEntity.getRelationType().equals(RelationType.NEUTRAL)
                && !relationsEntity.geActive()) {
            return false;
        }
        return true;
    }

    @Override
    public boolean validNull(RelationsEntity relationsEntity) {
        if ((relationsEntity.getSimLeft() == null || relationsEntity.getSimRight() == null)
                && (relationsEntity.getChildren() == null || relationsEntity.getChildren().isEmpty())) {
            return false;
        }
        return true;
    }

    @Override
    public boolean validMemberSex(RelationsEntity relation) {
        if (relation.getSimLeft() == null && relation.getSimRight() == null) {
            return true;
        }
        if (relation.getSimLeft() != null && relation.getSimRight() != null
                && relation.getSimLeft().getSex() != relation.getSimRight().getSex()
                && (relation.getSimRight().getSex() == Sex.FEMALE
                || relation.getSimLeft().getSex() == Sex.MALE)) {
            return false;
        } else if (relation.getSimLeft() == null && relation.getSimRight().getSex() == Sex.FEMALE) {
            return false;
        } else if (relation.getSimRight() == null && relation.getSimLeft().getSex() == Sex.MALE) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean isValidMember(MemberEntity member) {
        if (member == null) {
            return true;
        } else if (member.getId() == null) {
            return false;
        } else if (!memberService.exist(member)) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean validMembers(RelationsEntity relationsEntity) {

        if (!isValidMembers(relationsEntity.getSimLeft(), relationsEntity.getSimRight())) {
            return false;
        }
        for (MemberEntity member : relationsEntity.getChildren()) {
            if (!isValidMember(member)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean validDescendence(List<RelationsEntity> list, RelationsEntity bean, MemberEntity member) {

        for (MemberEntity m : bean.getChildren()) {
            if (m.equals(member)) {
                return false;
            }
        }

        for (MemberEntity child : bean.getChildren()) {
            List<RelationsEntity> filtered = list.stream().filter(o -> isLeftOrRight(o, child)).collect(Collectors.toList());

            for (RelationsEntity r : filtered) {
                if (!validDescendence(list, r, member)) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean validAscendance(List<RelationsEntity> list, MemberEntity bean, MemberEntity member) {

        if (bean == null || member == null) {
            return true;
        }

        List<RelationsEntity> filtered = list.stream().filter(o -> o.getChildren().contains(bean)).collect(Collectors.toList());
        for (RelationsEntity relation : filtered) {

            if (relation.getSimLeft() != null) {
                if (relation.getSimLeft().equals(member)) {
                    return false;
                } else {
                    if (!validAscendance(list, relation.getSimLeft(), member)) {
                        return false;
                    }
                }
            }

            if (relation.getSimRight() != null && relation.getSimRight().equals(member)) {
                if (relation.getSimRight().equals(member)) {
                    return false;
                } else {
                    if (!validAscendance(list, relation.getSimRight(), member)) {
                        return false;
                    }
                }

            }
        }
        return true;
    }


    private boolean isValidMembers(MemberEntity... member) {
        for (MemberEntity m : member) {
            if (!isValidMember(m)) {
                return false;
            }

        }
        return true;
    }

    private boolean isLeftOrRight(RelationsEntity relation, MemberEntity m) {
        return relation.getSimLeft() != null && relation.getSimLeft().equals(m) ||
                relation.getSimRight() != null && relation.getSimRight().equals(m);
    }
}
