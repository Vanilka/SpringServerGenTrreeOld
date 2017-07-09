package gentree.client.desktop.service.responses;

import gentree.client.desktop.domain.Member;
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
        return "MemberResponse{" +
                "member=" + member +
                "} " + super.toString();
    }
}
