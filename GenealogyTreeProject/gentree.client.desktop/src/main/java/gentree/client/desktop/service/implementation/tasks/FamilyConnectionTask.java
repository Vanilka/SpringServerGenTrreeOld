package gentree.client.desktop.service.implementation.tasks;

import gentree.client.desktop.configuration.enums.ServerPaths;
import gentree.client.desktop.domain.Family;
import gentree.client.desktop.responses.ServiceResponse;
import gentree.client.desktop.service.responses.ExceptionResponse;
import gentree.client.desktop.service.responses.FamilyListResponse;
import gentree.client.desktop.service.responses.FamilyResponse;
import gentree.server.dto.FamilyDTO;
import lombok.extern.log4j.Log4j2;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vanilka on 17/12/2017.
 */
@Log4j2
public class FamilyConnectionTask extends ConnectionTask {

    public FamilyConnectionTask() {
        super();
    }

    public ServiceResponse retrieveFamilies() {

        ServiceResponse serviceResponse = null;

        Response response = cs.doGet(ServerPaths.FAMILY);

        if (response.getStatus() == 200) {
            List<FamilyDTO> list = response.readEntity(new GenericType<List<FamilyDTO>>() {
            });

            System.out.println("LIST COUNT" + list.size());

            List<Family> resultList = new ArrayList<>();
            list.forEach(dto -> resultList.add(cdm.convertLazy(dto)));
            serviceResponse = new FamilyListResponse(resultList);
        } else {
            serviceResponse = new ExceptionResponse();
        }

        return serviceResponse;
    }


    public ServiceResponse addFamily(Family f) {
        ServiceResponse serviceResponse = null;
        Response response = cs.doPost(ServerPaths.FAMILY.concat(ServerPaths.ADD), Entity.json(cmd.convertLazy(f)));

        if (response.getStatus() == 200) {
            //Actualy do nothing. Familly List will be retrieved later
        }

        return serviceResponse;
    }


    public ServiceResponse retrieveFullFamily(Family f) throws Exception {
        ServiceResponse serviceResponse = null;

        Response response = cs.doGet(ServerPaths.FAMILY.concat("/").concat(String.valueOf(f.getId())));
        if (response.getStatus() == 200) {
            f = cdm.convertFull(response.readEntity(FamilyDTO.class));
            serviceResponse = new FamilyResponse(f);
        }

        return serviceResponse;
    }
}