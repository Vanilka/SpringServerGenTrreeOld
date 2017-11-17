package gentree.server.service.Implementation;

import gentree.server.domain.entity.MemberEntity;
import gentree.server.domain.entity.RelationEntity;
import gentree.server.repository.MemberRepository;
import gentree.server.service.MemberService;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by Martyna SZYMKOWIAK on 18/10/2017.
 */
@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    MemberRepository memberRepository;

    @Override
    public MemberEntity addNewMember(MemberEntity member) {
/*        if (member.getBornRelation() == null) {
            member.setBornRelation(new RelationEntity(null, null, member, member.getFamily()));
        }*/
        try {
            member = memberRepository.saveAndFlush(member);
            System.out.println("Member added" +member);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return member;
    }

    @Override
    public MemberEntity deleteMember(MemberEntity member) {

        Optional<MemberEntity>  optional = memberRepository.findById(member.getId());

        System.out.println("Is present ?? " +optional.isPresent());

        optional.ifPresent(memberRepository::delete);

        memberRepository.flush();
        return member;
    }

    @Override
    public MemberEntity findMemberById(Long id) {

        return memberRepository.findById(id).orElse(null);
    }

    @Override
    public List<MemberEntity> findMembersByFamilyId(Long id) {
        return null;
    }
}
