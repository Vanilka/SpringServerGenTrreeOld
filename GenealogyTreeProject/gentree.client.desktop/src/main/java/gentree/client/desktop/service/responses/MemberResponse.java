package gentree.client.desktop.service.responses;

import gentree.client.desktop.domain.Member;
import gentree.client.desktop.responses.ServiceResponse;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by vanilka on 26/12/2016.
 */
@Setter
@Getter
public class MemberResponse extends ServiceResponse {

    Member member;


    public MemberResponse(Member member) {
        super(ServiceResponse.ResponseStatus.OK);
        this.member = member;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MemberResponse{");
        sb.append("mem=").append(member);
        sb.append('}');
        return sb.toString();
    }
}
