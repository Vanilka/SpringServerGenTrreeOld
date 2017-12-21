package gentree.client.desktop.service.implementation.tasks;

import gentree.client.desktop.configuration.enums.ServerPaths;
import gentree.client.desktop.configuration.messages.LogMessages;
import gentree.client.desktop.domain.Relation;
import gentree.client.desktop.responses.ServiceResponse;
import gentree.client.desktop.service.responses.MemberResponse;
import gentree.client.desktop.service.responses.RelationListResponse;
import gentree.server.dto.MemberDTO;
import gentree.server.dto.RelationDTO;
import lombok.extern.log4j.Log4j2;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by vanilka on 17/12/2017.
 */
@Log4j2
public class RelationConnectionTask extends ConnectionTask {

    public RelationConnectionTask() {
        super();
    }

    public ServiceResponse addRelation(Relation relation) {
        RelationDTO dto = cmd.convert(relation);
        dto.setFamily(cmd.convertLazy(service.getCurrentFamily()));

        Response response = cs.doPost(ServerPaths.RELATION.concat(ServerPaths.ADD), Entity.json(dto));

       return attendRelationListResponse(response);

    }

    public  ServiceResponse removeRelation(Relation r) {
        ServiceResponse serviceResponse = null;

        RelationDTO dto = cmd.convertLazy(r);
        dto.setFamily(cmd.convertLazy(service.getCurrentFamily()));
        Response response = cs.doPost(ServerPaths.RELATION.concat(ServerPaths.DELETE), Entity.json(dto));
        serviceResponse = attendRelationListResponse(response);
        return serviceResponse;
    }

    private ServiceResponse attendRelationListResponse(Response response) {
        ServiceResponse serviceResponse = null;
        if (response.getStatus() == 200) {
            try {
                List<RelationDTO> returnedDTO = response.readEntity(new GenericType<List<RelationDTO>>(){});
                List<Relation> relations = cdm.convertRelationList(returnedDTO, service.getCurrentFamily());
                serviceResponse = new RelationListResponse(relations);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return serviceResponse;
    }


}
