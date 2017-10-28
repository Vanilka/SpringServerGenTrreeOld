package gentree.server.service.Implementation;

import gentree.server.domain.entity.MemberEntity;
import gentree.server.domain.entity.RelationEntity;
import gentree.server.repository.MemberRepository;
import gentree.server.service.MemberService;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Martyna SZYMKOWIAK on 18/10/2017.
 */
@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    MemberRepository memberRepository;

    @Override
    public MemberEntity addNewMember(MemberEntity member) {
 /*       if (member.getBornRelation() == null) {
            member.setBornRelation(new RelationEntity(null, null, member, member.getFamily()));
        }*/
        MemberEntity m = memberRepository.saveAndFlush(member);
        Hibernate.initialize(member.getFamily());
        return m;
    }

    @Override
    public MemberEntity deleteMember(MemberEntity member) {
        return null;
    }

    @Override
    public MemberEntity findMemberById(Long id) {
        return null;
    }

    @Override
    public List<MemberEntity> findMembersByFamilyId(Long id) {
        return null;
    }
}
