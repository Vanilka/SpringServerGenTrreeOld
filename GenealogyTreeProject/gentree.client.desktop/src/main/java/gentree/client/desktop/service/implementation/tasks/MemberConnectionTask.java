package gentree.client.desktop.service.implementation.tasks;

import gentree.client.desktop.configuration.enums.ServerPaths;
import gentree.client.desktop.configuration.messages.LogMessages;
import gentree.client.desktop.domain.Family;
import gentree.client.desktop.domain.Member;
import gentree.client.desktop.domain.Relation;
import gentree.client.desktop.responses.ServiceResponse;
import gentree.client.desktop.service.responses.FamilyResponse;
import gentree.client.desktop.service.responses.MemberResponse;
import gentree.client.desktop.service.responses.MemberWithBornRelationResponse;
import gentree.server.dto.FamilyDTO;
import gentree.server.dto.MemberDTO;
import gentree.server.dto.NewMemberDTO;
import lombok.extern.log4j.Log4j2;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

/**
 * Created by vanilka on 17/12/2017.
 */
@Log4j2
public class MemberConnectionTask extends ConnectionTask {

    public MemberConnectionTask() {
        super();
    }

    public ServiceResponse addNewMember(Member member) {
        ServiceResponse serviceResponse = null;
        MemberDTO dto = cmd.convert(member);
        dto.setFamily(cmd.convertLazy(service.getCurrentFamily()));

        Response response = cs.doPost(ServerPaths.MEMBER.concat(ServerPaths.ADD), Entity.json(dto));

        if (response.getStatus() == 200) {
            try {
                NewMemberDTO returnedDTO = response.readEntity(NewMemberDTO.class);
                Member addedMember = cdm.convert(returnedDTO.getMemberDTO());
                Relation bornRelation = cdm.convertPoor(returnedDTO.getRelationDTO());
                bornRelation.addChildren(addedMember);
                serviceResponse = new MemberWithBornRelationResponse(addedMember, bornRelation);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return serviceResponse;
    }

    public ServiceResponse updateMember(Member member) {
        ServiceResponse serviceResponse = null;
        MemberDTO dto = cmd.convert(member);
        dto.setFamily(cmd.convertLazy(service.getCurrentFamily()));
        Response response = cs.doPost(ServerPaths.MEMBER.concat(ServerPaths.UPDATE), Entity.json(dto));
        response.bufferEntity();

        log.info(LogMessages.MSG_SERVER_RETURNED_RESPONE, response.getStatus(), response.getEntity());

        if (response.getStatus() == 200) {
            try {
                MemberDTO returnedDTO = response.readEntity(MemberDTO.class);
                cdm.convertTo(member, returnedDTO);
                serviceResponse = new MemberResponse(member);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return serviceResponse;
    }


    public ServiceResponse deleteMember(Member m) {
        ServiceResponse serviceResponse = null;
        MemberDTO dto = cmd.convert(m);
        dto.setFamily(cmd.convertLazy(service.getCurrentFamily()));
        Response response = cs.doPost(ServerPaths.MEMBER.concat(ServerPaths.DELETE), Entity.json(dto));
        if (response.getStatus() == 200) {
            try {
                if (response.getStatus() == 200) {
                    Family f = cdm.convertFull(response.readEntity(FamilyDTO.class));
                    serviceResponse = new FamilyResponse(f);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return serviceResponse;
    }


    public ServiceResponse addRelation(Relation relation) {
        return null;
    }

}
