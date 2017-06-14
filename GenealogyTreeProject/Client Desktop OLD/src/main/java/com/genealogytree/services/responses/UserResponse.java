package genealogytree.services.responses;

import genealogytree.domain.beans.UserBean;

public class UserResponse extends ServerResponse {

    UserBean user;

    public UserResponse() {
        this(null);
    }

    public UserResponse(UserBean u) {
        super(ResponseStatus.OK);
        this.user = u;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "UserResponse{" +
                "user=" + user +
                "} " + super.toString();
    }
}
