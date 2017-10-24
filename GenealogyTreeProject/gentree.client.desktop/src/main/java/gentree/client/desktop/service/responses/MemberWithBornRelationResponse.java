package gentree.client.desktop.service.responses;

import gentree.client.desktop.domain.Member;
import gentree.client.desktop.domain.Relation;
import gentree.client.desktop.responses.ServiceResponse;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Martyna SZYMKOWIAK on 24/10/2017.
 */
@Getter
@Setter
public class MemberWithBornRelationResponse extends ServiceResponse {

    Member member;
    Relation relation;

    public MemberWithBornRelationResponse(Member member, Relation relation) {
        super(ServiceResponse.ResponseStatus.OK);
        this.member = member;
        this.relation = relation;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MemberWithBornRelationResponse{");
        sb.append("member=").append(member);
        sb.append(", relation=").append(relation);
        sb.append('}');
        return sb.toString();
    }
}
