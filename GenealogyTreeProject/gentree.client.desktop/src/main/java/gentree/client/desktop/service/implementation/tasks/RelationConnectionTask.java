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
        ServiceResponse serviceResponse = null;
        RelationDTO dto = cmd.convert(relation);
        dto.setFamily(cmd.convertLazy(service.getCurrentFamily()));
        log.info(LogMessages.MSG_POST_REQUEST, Entity.json(dto));

        Response response = cs.doPost(ServerPaths.RELATION.concat(ServerPaths.ADD), Entity.json(dto));

        if (response.getStatus() == 200) {
            try {
                List<RelationDTO> returnedDTO = response.readEntity(new GenericType<List<RelationDTO>>(){});
                System.out.println("Returned DTO is  :" + returnedDTO);
                List<Relation> relations = cdm.convertRelationList(returnedDTO, service.getCurrentFamily());
                serviceResponse = new RelationListResponse(relations);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return serviceResponse;
    }

}
