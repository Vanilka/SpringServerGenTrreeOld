package genealogytree.client.desktop.service.responses;


import genealogytree.client.desktop.domain.GTX_User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse extends ServiceResponse {

    GTX_User user;

    public UserResponse() {
        this(null);
    }

    public UserResponse(GTX_User u) {
        super(ResponseStatus.OK);
        this.user = u;
    }


    @Override
    public String toString() {
        return "UserResponse{" +
                "user=" + user +
                "} " + super.toString();
    }
}
