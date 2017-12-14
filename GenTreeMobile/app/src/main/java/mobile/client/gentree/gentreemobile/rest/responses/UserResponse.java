package mobile.client.gentree.gentreemobile.rest.responses;

import gentree.server.dto.OwnerDTO;

/**
 * Created by vanilka on 14/12/2017.
 */
public class UserResponse extends ServerResponse {

    private OwnerDTO owner;

    public UserResponse(OwnerDTO owner) {
        super(ResponseStatus.OK);
        this.owner = owner;
    }


    public OwnerDTO getOwner() {
        return owner;
    }

    public void setOwner(OwnerDTO owner) {
        this.owner = owner;
    }
}
