package gentree.client.desktop.service;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import gentree.client.desktop.configuration.Realm;
import gentree.client.desktop.configuration.enums.ServerPaths;
import gentree.client.desktop.domain.Owner;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import lombok.Getter;
import lombok.Setter;
import org.glassfish.jersey.client.ClientProperties;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Martyna SZYMKOWIAK on 22/10/2017.
 */

public class RestConnectionService {

    public static final RestConnectionService INSTANCE = new RestConnectionService();

    private static final String HEADER_AUTHORIZATION = "Authorization";
    private static final String AUTHORIZATION_METHOD = "Basic ";

    private ObjectProperty<Owner> owner = new SimpleObjectProperty<>();

    private  WebTarget webTarget;
    private final Client client;
   // private final List<Object> providers;

    private RestConnectionService() {
        client = ClientBuilder.newClient().register(JacksonJsonProvider.class);
        client.property(
                ClientProperties.CONNECT_TIMEOUT,
                5000);
        client.property(
                ClientProperties.READ_TIMEOUT,
                5000);
    }

    public boolean testConnection(String URL) {
        boolean result = true;
        WebTarget testWebtarget = client.target(URL);
        try {
            Response response = testWebtarget.request(MediaType.APPLICATION_JSON).get();
            if (response.getStatus() != 200) {
                result = false;
            }
        } catch (Exception e) {
            result = false;
        }

        return  result;
    }


    public boolean login(String login, String password, Realm realm) {
        boolean result = true;
        WebTarget canditateWebTarget = client.target(realm.getAddress());

        try {
            Response response = canditateWebTarget
                    .path(ServerPaths.LOGIN)
                    .request(MediaType.APPLICATION_JSON)
                    .header(HEADER_AUTHORIZATION, AUTHORIZATION_METHOD + generateToken(login, password))
                    .post(null);
            if (response.getStatus() != 200) {
                System.out.println("NOT OK");

                result = false;
            } else  {
                System.out.println("JEST OK");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = false;
        }

        return  result;

    }

    public void SetWebTarget(Realm realm) {
        webTarget = client.target(realm.getAddress());
    }

    private String generateToken() {
        return generateToken(getOwner().getLogin(), getOwner().getPassword());
    }

    private String generateToken(String login, String password) {
        StringBuffer token = new StringBuffer();
        token.append(login);
        token.append(":");
        token.append(password);
        return java.util.Base64.getEncoder().encodeToString(token.toString().getBytes());
    }


    /*
            GETTERS SETTERS
     */

    public Owner getOwner() {
        return owner.get();
    }

    public ObjectProperty<Owner> ownerProperty() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner.set(owner);
    }
}
