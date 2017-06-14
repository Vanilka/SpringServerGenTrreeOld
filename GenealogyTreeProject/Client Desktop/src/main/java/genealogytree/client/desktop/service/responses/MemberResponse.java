package genealogytree.client.desktop.service.responses;

import genealogytree.client.desktop.domain.GTX_Member;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by vanilka on 26/12/2016.
 */
@Setter
@Getter
public class MemberResponse extends ServiceResponse {

    GTX_Member member;


    public MemberResponse(GTX_Member member) {
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
