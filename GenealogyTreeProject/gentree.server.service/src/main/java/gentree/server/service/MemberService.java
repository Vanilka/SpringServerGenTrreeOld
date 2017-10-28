package gentree.server.service;

import gentree.server.domain.entity.MemberEntity;

import java.util.List;

/**
 * Created by Martyna SZYMKOWIAK on 18/10/2017.
 */
public interface MemberService {

    MemberEntity addNewMember(MemberEntity member);
    MemberEntity deleteMember(MemberEntity member);

    MemberEntity findMemberById(Long id);
    List<MemberEntity> findMembersByFamilyId(Long id);

}
