package mobile.client.gentree.gentreemobile.rest.responses;

import gentree.server.dto.FamilyDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by vanilka on 15/12/2017.
 */
@Getter
@Setter
public class FamilyListResponse extends ServerResponse {

    private List<FamilyDTO> familyList;

    public FamilyListResponse(List<FamilyDTO> familyList) {
        super(ServerResponse.ResponseStatus.OK);
        this.familyList = familyList;
    }
}
