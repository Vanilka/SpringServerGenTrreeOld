package genealogytree.services.implementation;

import genealogytree.application.GenealogyTreeContext;
import genealogytree.domain.beans.UserBean;
import genealogytree.exception.ExceptionBean;
import genealogytree.services.GTUserService;
import genealogytree.services.responses.ExceptionResponse;
import genealogytree.services.responses.ServerResponse;
import genealogytree.services.responses.UserResponse;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class GTUserServiceOnline implements GTUserService {

    private GenealogyTreeContext context;

    public GTUserServiceOnline() {
        this(null);
    }

    public GTUserServiceOnline(GenealogyTreeContext context) {
        this.context = context;
    }

    public ServerResponse registerUser(String email, String login, String password) {
        ServerResponse result = null;
        try {
            UserBean registerUser = new UserBean(email, login, password);
            Response registerResponse = this.context.getMainTarget().path("user").path("add")
                    .request(MediaType.APPLICATION_JSON).post(Entity.json(registerUser));

            if (registerResponse.getStatus() != 200) {
                result = new ExceptionResponse((registerResponse.readEntity(ExceptionBean.class)));
            } else {
                UserBean user = registerResponse.readEntity(UserBean.class);
                result = new UserResponse(user);
            }
        } catch (Exception e) {
            result = new ExceptionResponse(new ExceptionBean());
        }
        return result;
    }

    public ServerResponse connect(String login, String password) {
        ServerResponse result = null;
        try {
            UserBean loginuser = new UserBean(null, login, password);
            Response response = this.context.getMainTarget().path("user").path("login")
                    .request(MediaType.APPLICATION_JSON).post(Entity.json(loginuser));
            if (response.getStatus() != 200) {
                System.out.println("Nie Udalo SIE !");
                System.out.println(response.readEntity(String.class));
                result = new ExceptionResponse((response.readEntity(ExceptionBean.class)));

            } else {
                UserBean LoggedUser = response.readEntity(UserBean.class);
                this.context.setConnectedUser(LoggedUser);

                result = new UserResponse(LoggedUser);
            }

        } catch (Exception e) {
            result = new ExceptionResponse(new ExceptionBean());
        }

        return result;
    }

    public void setContext(GenealogyTreeContext context) {
        this.context = context;
    }

}
