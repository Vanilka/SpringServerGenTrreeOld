package genealogytree.client.desktop.service.responses;

import genealogytree.client.desktop.domain.GTX_Member;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by vanilka on 26/12/2016.
 */
@Setter
@Getter
public class ListMemberResponse extends ServiceResponse {


    List<GTX_Member> listMember;

    public ListMemberResponse() {
        this(null);
    }

    public ListMemberResponse(List<GTX_Member> list) {
        super(ResponseStatus.OK);
        this.listMember = list;
    }


    @Override
    public String toString() {
        return "ListMemberResponse{" +
                "listMember=" + listMember +
                "} " + super.toString();
    }
}
