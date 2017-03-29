package com.genealogytree.service.validators;

import com.genealogytree.persist.entity.modules.tree.MemberEntity;
import com.genealogytree.persist.entity.modules.tree.RelationsEntity;

import java.util.List;

/**
 * Created by Martyna SZYMKOWIAK on 23/03/2017.
 */
public interface Validator {

    boolean isValidMember(MemberEntity member);

    boolean validMemberSex(RelationsEntity relationsEntity);

    boolean validMembers(RelationsEntity relationsEntity);

    boolean validNull(RelationsEntity relationsEntity);

    boolean validStatus(RelationsEntity relationsEntity);

    boolean validDescendence(List<RelationsEntity> list, RelationsEntity bean, MemberEntity member);

    boolean validAscendance(List<RelationsEntity> list, MemberEntity bean, MemberEntity member);

}
