package mobile.client.gentree.gentreemobile.rest.responses;

import gentree.server.dto.FamilyDTO;
import lombok.Getter;

import java.util.List;

/**
 * Created by vanilka on 21/12/2017.
 */
@Getter
public class FamilyResponse extends ServerResponse {

    private FamilyDTO family;

    public FamilyResponse(FamilyDTO family) {
        super(ServerResponse.ResponseStatus.OK);
        this.family = family;
    }
}
