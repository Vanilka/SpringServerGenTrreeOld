package gentree.server.service;

import gentree.server.domain.entity.MemberEntity;

/**
 * Created by Martyna SZYMKOWIAK on 18/10/2017.
 */
public interface MemberService {

    MemberEntity addNewMember(MemberEntity member);
    MemberEntity findMemberById(Long id);

}
