package genealogytree.domain.wrappers;

import genealogytree.domain.dto.MemberDTO;
import genealogytree.domain.dto.RelationDTO;

/**
 * Created by vanilka on 04/01/2017.
 */
public class CreatedMemberWrapper {

    private MemberDTO member;
    private RelationDTO relation;

    public CreatedMemberWrapper(MemberDTO member, RelationDTO relation) {
        this.member = member;
        this.relation = relation;
    }

    /*
     *      GETTERS
     */

    public MemberDTO getMember() {
        return member;
    }

    public void setMember(MemberDTO member) {
        this.member = member;
    }

    /*
     *      SETTERS
     */

    public RelationDTO getRelation() {
        return relation;
    }

    public void setRelation(RelationDTO relation) {
        this.relation = relation;
    }

    @Override
    public String toString() {
        return "CreatedMemberWrapper{" +
                "member=" + member +
                ", relation=" + relation +
                '}';
    }
}
