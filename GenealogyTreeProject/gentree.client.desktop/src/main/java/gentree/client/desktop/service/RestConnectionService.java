package gentree.client.desktop.service;

import gentree.client.desktop.configuration.Realm;
import gentree.client.desktop.domain.Family;
import gentree.client.desktop.domain.Member;
import gentree.client.desktop.domain.Relation;
import gentree.client.desktop.responses.ServiceResponse;
import gentree.client.desktop.service.implementation.ConnectionService;
import gentree.client.desktop.service.implementation.GenTreeOnlineService;
import gentree.client.desktop.service.implementation.tasks.FamilyConnectionTask;
import gentree.client.desktop.service.implementation.tasks.MemberConnectionTask;
import gentree.client.desktop.service.implementation.tasks.RelationConnectionTask;
import lombok.extern.log4j.Log4j2;

/**
 * Created by Martyna SZYMKOWIAK on 22/10/2017.
 */
@Log4j2
public class RestConnectionService {

    public static final RestConnectionService INSTANCE = new RestConnectionService();

    private ConnectionService connectionService = ConnectionService.INSTANCE;

    private FamilyConnectionTask fct = new FamilyConnectionTask();
    private MemberConnectionTask mct = new MemberConnectionTask();
    private RelationConnectionTask rct = new RelationConnectionTask();

    private GenTreeOnlineService service;

    private RestConnectionService() {
    }


    public void registerService(GenTreeOnlineService onlineService) {
        this.service = onlineService;
        connectionService.registerService(onlineService);
        mct.registerService(onlineService);
        fct.registerService(onlineService);
        rct.registerService(onlineService);

    }

    /**
     * Testing connection with a Realm
     *
     * @param URL
     * @return
     */
    public boolean testConnection(String URL) {
        return connectionService.testConnection(URL);
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
        return connectionService.login(login, password, realm);
    }

    /**
     * Retireve Families
     *
     * @return
     */
    public ServiceResponse retrieveFamilies() {
        return fct.retrieveFamilies();
    }

    public ServiceResponse addFamily(Family f) {
        return fct.addFamily(f);
    }

    public ServiceResponse retrieveFullFamily(Family f) throws Exception {

        return fct.retrieveFullFamily(f);
    }


    /* *************************
        MEMBER MANAGEMENT
    *************************** */

    public ServiceResponse addNewMember(Member member) {
        return mct.addNewMember(member);
    }

    public ServiceResponse updateMember(Member member) {
        return mct.updateMember(member);
    }

    public ServiceResponse deleteMember(Member m) {
        return mct.deleteMember(m);
    }

    /* *************************
        RELATION MANAGEMENT
    *************************** */

    public ServiceResponse addRelation(Relation relation) {
        return rct.addRelation(relation);
    }

    public ServiceResponse removeRelation(Relation r) {
        return rct.removeRelation(r);
    }

    public void setWebTarget(Realm realm) {
        connectionService.setWebTarget(realm);
    }


}
