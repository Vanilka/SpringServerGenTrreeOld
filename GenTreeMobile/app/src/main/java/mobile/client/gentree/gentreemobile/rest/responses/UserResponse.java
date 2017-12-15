package mobile.client.gentree.gentreemobile.rest.responses;

import android.os.Parcel;
import android.os.Parcelable;
import gentree.server.dto.OwnerDTO;
import gentree.server.dto.OwnerExtendedDTO;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by vanilka on 14/12/2017.
 */
@Getter
@Setter
public class UserResponse extends ServerResponse{

    private OwnerExtendedDTO owner;

    public UserResponse(OwnerExtendedDTO owner) {
        super(ResponseStatus.OK);
        this.owner = owner;
    }


}
