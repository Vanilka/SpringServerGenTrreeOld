package gentree.client.desktop.service;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import gentree.client.desktop.configuration.Realm;
import gentree.client.desktop.configuration.converters.ConverterDtoToModel;
import gentree.client.desktop.configuration.converters.ConverterModelToDto;
import gentree.client.desktop.configuration.enums.ServerPaths;
import gentree.client.desktop.configuration.messages.LogMessages;
import gentree.client.desktop.domain.Family;
import gentree.client.desktop.domain.Member;
import gentree.client.desktop.domain.Owner;
import gentree.client.desktop.domain.Relation;
import gentree.client.desktop.responses.ServiceResponse;
import gentree.client.desktop.service.implementation.GenTreeOnlineService;
import gentree.client.desktop.service.responses.*;
import gentree.exception.ExceptionBean;
import gentree.server.dto.FamilyDTO;
import gentree.server.dto.MemberDTO;
import gentree.server.dto.NewMemberDTO;
import gentree.server.dto.RelationDTO;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import lombok.extern.log4j.Log4j2;
import org.glassfish.jersey.client.ClientProperties;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martyna SZYMKOWIAK on 22/10/2017.
 */
@Log4j2
public class RestConnectionService {

    public static final String SERVICE_NAME = "RestConnectionService";
    public static final RestConnectionService INSTANCE = new RestConnectionService();

    private static final String HEADER_AUTHORIZATION = "Authorization";
    private static final String AUTHORIZATION_METHOD = "Basic ";

    private final ConverterModelToDto cmd = ConverterModelToDto.INSTANCE;
    private final ConverterDtoToModel cdm = new ConverterDtoToModel();
    private final Client client;
    private GenTreeOnlineService service;
    private ObjectProperty<Owner> owner = new SimpleObjectProperty<>();
    private WebTarget webTarget;

    private RestConnectionService() {
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
        this.service = onlineService;
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
            System.out.println("NOT OK");
            result = false;
        } else {
            System.out.println("JEST OK");
            try {
                owner.setValue(response.readEntity(Owner.class));
                getOwner().setPassword(password);
                webTarget = client.target(realm.getAddress());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return result;

    }

    /**
     * Retireve Families
     *
     * @return
     */
    public ServiceResponse retrieveFamilies() {

        ServiceResponse serviceResponse = null;

        Response response = doGet(ServerPaths.FAMILY);

        if (response.getStatus() == 200) {
            List<FamilyDTO> list = response.readEntity(new GenericType<List<FamilyDTO>>() {
            });

            System.out.println("LIST COUNT" + list.size());

            List<Family> resultList = new ArrayList<>();
            list.forEach(dto -> resultList.add(cdm.convertLazy(dto)));
            serviceResponse = new FamilyListResponse(resultList);
        } else {
            serviceResponse = new ExceptionResponse(response.readEntity(ExceptionBean.class));
        }

        return serviceResponse;
    }


    public ServiceResponse addFamily(Family f) {
        ServiceResponse serviceResponse = null;
        Response response = doPost(ServerPaths.FAMILY.concat(ServerPaths.ADD), Entity.json(cmd.convertLazy(f)));

        if (response.getStatus() == 200) {
            //Actualy do nothing. Familly List will be retrieved later
        }

        return serviceResponse;
    }

    public ServiceResponse retrieveFullFamily(Family f) throws Exception {
        ServiceResponse serviceResponse = null;

        Response response = doGet(ServerPaths.FAMILY.concat("/").concat(String.valueOf(f.getId())));
        if (response.getStatus() == 200) {
            f = cdm.convertFull(response.readEntity(FamilyDTO.class));
            serviceResponse = new FamilyResponse(f);
        }

        return serviceResponse;
    }


    /* *************************
        MEMBER MANAGEMENT
    *************************** */

    public ServiceResponse addNewMember(Member member) {
        ServiceResponse serviceResponse = null;
        MemberDTO dto = cmd.convert(member);
        dto.setFamily(cmd.convertLazy(service.getCurrentFamily()));

        log.info(LogMessages.MSG_POST_REQUEST, Entity.json(dto));

        Response response = doPost(ServerPaths.MEMBER.concat(ServerPaths.ADD), Entity.json(dto));

        if (response.getStatus() == 200) {
            try {
                NewMemberDTO returnedDTO = response.readEntity(NewMemberDTO.class);
                Member addedMember = cdm.convert(returnedDTO.getMemberDTO());
                Relation bornRelation = cdm.convertPoor(returnedDTO.getRelationDTO());
                bornRelation.addChildren(addedMember);
                serviceResponse = new MemberWithBornRelationResponse(addedMember, bornRelation);
            } catch (Exception e ) {
                e.printStackTrace();
            }

        }
        return serviceResponse;
    }


    public ServiceResponse deleteMember(Member m) {
        ServiceResponse serviceResponse = null;
        MemberDTO dto = cmd.convert(m);
        dto.setFamily(cmd.convertLazy(service.getCurrentFamily()));
        Response response = doPost(ServerPaths.MEMBER.concat(ServerPaths.DELETE), Entity.json(dto));
        if (response.getStatus() == 200) {
            try {
                if (response.getStatus() == 200) {
                    Family f = cdm.convertFull(response.readEntity(FamilyDTO.class));
                    serviceResponse = new FamilyResponse(f);
                }
            } catch (Exception e ) {
                e.printStackTrace();
            }
        }
        return serviceResponse;
    }

    /*
        GET AND POST ACTIONS
     */

    private Response doGet(String path) {
        return doGet(webTarget, path, generateToken());
    }

    private Response doGet(WebTarget target, String path, String token) {
        Response response = null;
        System.out.println(token);
        try {
            response = target.path(path)
                    .request(MediaType.APPLICATION_JSON)
                    .header(HEADER_AUTHORIZATION, AUTHORIZATION_METHOD.concat(token))
                    .get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    private Response doPost(String path, Entity entity) {
        return doPost(webTarget, path, generateToken(), entity);
    }

    private Response doPost(WebTarget target, String path, String token, Entity entity) {
        Response response = null;
        try {
            response = target.path(path)
                    .request(MediaType.APPLICATION_JSON)
                    .header(HEADER_AUTHORIZATION, AUTHORIZATION_METHOD.concat(token))
                    .post(entity);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
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
        return owner.getValue();
    }

    public void setOwner(Owner owner) {
        this.owner.set(owner);
    }

    public ObjectProperty<Owner> ownerProperty() {
        return owner;
    }

}
