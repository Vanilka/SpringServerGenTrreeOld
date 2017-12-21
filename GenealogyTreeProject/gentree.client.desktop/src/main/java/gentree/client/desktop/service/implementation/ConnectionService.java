package gentree.client.desktop.service.implementation;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import gentree.client.desktop.configuration.AlertsKeys;
import gentree.client.desktop.configuration.Realm;
import gentree.client.desktop.configuration.converters.ConverterDtoToModel;
import gentree.client.desktop.configuration.converters.ConverterModelToDto;
import gentree.client.desktop.configuration.enums.ServerPaths;
import gentree.client.desktop.configuration.messages.LogMessages;
import gentree.client.desktop.domain.Owner;
import gentree.client.desktop.service.GenTreeContext;
import gentree.client.desktop.service.ScreenManager;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.glassfish.jersey.client.ClientProperties;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by vanilka on 17/12/2017.
 */
@Log4j2
public class ConnectionService {

    public static final String SERVICE_NAME = "RestConnectionService";
    public static final ConnectionService INSTANCE = new ConnectionService();

    private static final String HEADER_AUTHORIZATION = "Authorization";
    private static final String AUTHORIZATION_METHOD = "Basic ";

    private static final GenTreeContext context = GenTreeContext.INSTANCE;
    private static final ScreenManager sm = ScreenManager.INSTANCE;
    private final ConverterModelToDto cmd = new ConverterModelToDto();
    private final ConverterDtoToModel cdm = new ConverterDtoToModel();
    private final Client client;
    private String TOKEN = "";
    private GenTreeOnlineService service;
    private ObjectProperty<Owner> owner = new SimpleObjectProperty<>();
    @Getter
    @Setter
    private WebTarget webTarget;


    private ConnectionService() {

        client = ClientBuilder.newClient().register(JacksonJsonProvider.class);
        client.register(log);
        client.property(
                ClientProperties.CONNECT_TIMEOUT,
                5000);
        client.property(
                ClientProperties.READ_TIMEOUT,
                5000);
        log.trace(LogMessages.MSG_SERVICE_INITIALIZATION, SERVICE_NAME);

    }

    public void registerService(GenTreeOnlineService onlineService) {
        this.service = service;

    }


    /**
     * If Login return True the Web target And Owner will be set.
     *
     * @param login
     * @param password
     * @param realm
     * @return
     */
    public boolean login(String login, String password, Realm realm) {
        boolean result = true;

        Response response = doPost(client.target(realm.getAddress()), ServerPaths.LOGIN, generateToken(login, password), null);

        if (response == null || response.getStatus() != 200) {
            sm.showError(context.getAlertValueFromKey(AlertsKeys.CONNECTION_ERROR_HEADER),
                    context.getAlertValueFromKey(AlertsKeys.CONNECTION_ERROR_HEADER),
                    context.getAlertValueFromKey(AlertsKeys.CONNECTION_ERROR_HEADER));
            result = false;
        } else {
            System.out.println("JEST OK");
            try {
                setOwner(response.readEntity(Owner.class));
                getOwner().setPassword(password);
                this.TOKEN = generateToken(owner.get().getLogin(), owner.get().getPassword());
                webTarget = client.target(realm.getAddress());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return result;
    }


    /**
     * Testing connection with a Realm
     *
     * @param URL
     * @return
     */
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

        return result;
    }



    /*
        GET AND POST ACTIONS
     */

    public Response doGet(String path) {
        return doGet(webTarget, path, TOKEN);
    }

    public Response doGet(WebTarget target, String path, String token) {
        Response response = null;
        printLogHeaderRestExchange();
        log.info(LogMessages.MSG_SERVER_ACCESS_PATH, target.getUri().resolve(path));
        log.info(LogMessages.DELIMITER_MIDDLE);
        try {
            response = target.path(path)
                    .request(MediaType.APPLICATION_JSON)
                    .header(HEADER_AUTHORIZATION, AUTHORIZATION_METHOD.concat(token))
                    .get();
            response.bufferEntity();
            log.info(LogMessages.MSG_SERVER_RETURNED_RESPONE,response.getStatus(), response.readEntity(String.class));
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }

        printLogFooterRestExchange();
        return response;
    }

    public Response doPost(String path, Entity entity) {

        return doPost(webTarget, path, TOKEN, entity);
    }

    public Response doPost(WebTarget target, String path, String token, Entity entity) {

        printLogHeaderRestExchange();
        log.info(LogMessages.MSG_POST_REQUEST);
        log.info(LogMessages.MSG_SERVER_ACCESS_PATH, target.getUri().resolve(path));
        log.info(LogMessages.DELIMITER_MIDDLE);
        log.info(LogMessages.MSG_POST_REQUEST_CONTENT);
        log.info(LogMessages.DELIMITER_MIDDLE);

        Response response = null;
        try {
            response = target.path(path)
                    .request(MediaType.APPLICATION_JSON)
                    .header(HEADER_AUTHORIZATION, AUTHORIZATION_METHOD.concat(token))
                    .post(entity);
            response.bufferEntity();
            log.info(LogMessages.MSG_SERVER_RETURNED_RESPONE,response.getStatus(), response.readEntity(String.class));
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }



        printLogFooterRestExchange();

        return response;
    }


    private String generateToken(String login, String password) {
        StringBuffer token = new StringBuffer();
        token.append(login);
        token.append(":");
        token.append(password);
        return java.util.Base64.getEncoder().encodeToString(token.toString().getBytes());
    }


    public Owner getOwner() {
        return owner.getValue();
    }

    public void setOwner(Owner owner) {
        this.owner.set(owner);
        this.TOKEN = generateToken(owner.getLogin(), owner.getPassword());
    }

    public ObjectProperty<Owner> ownerProperty() {
        return owner;
    }

    public void setWebTarget(Realm realm) {
        webTarget = client.target(realm.getAddress());
    }


    protected void printLogHeaderRestExchange() {

    }

    protected void printLogFooterRestExchange() {

    }

}
