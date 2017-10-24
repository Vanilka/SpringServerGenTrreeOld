package gentree.server.service.Implementation;

import gentree.server.domain.entity.MemberEntity;
import gentree.server.repository.MemberRepository;
import gentree.server.service.MemberService;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by Martyna SZYMKOWIAK on 18/10/2017.
 */
@Service
@Transactional
public class MemberServiceImpl implements MemberService {

    @Autowired
    MemberRepository memberRepository;

    @Override
    public MemberEntity addNewMember(MemberEntity member) {
        MemberEntity m = memberRepository.saveAndFlush(member);
        Hibernate.initialize(member.getFamily());
        System.out.println(member);
        return m;
    }

    @Override
    public MemberEntity findMemberById(Long id) {
        return null;
    }
}
