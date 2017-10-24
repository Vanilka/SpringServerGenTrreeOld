package gentree.client.desktop.service.implementation;

import gentree.client.desktop.domain.Family;
import gentree.client.desktop.domain.Member;
import gentree.client.desktop.domain.Relation;
import gentree.client.desktop.responses.ServiceResponse;
import gentree.client.desktop.service.FamilyService;
import gentree.client.desktop.service.RestConnectionService;
import gentree.client.desktop.service.responses.FamilyResponse;
import gentree.client.desktop.service.responses.MemberWithBornRelationResponse;
import gentree.common.configuration.enums.RelationType;
import javafx.beans.property.ReadOnlyObjectProperty;

import java.util.List;

/**
 * Created by Martyna SZYMKOWIAK on 22/10/2017.
 */
public class GenTreeOnlineService extends GenTreeService implements FamilyService {

    private final RestConnectionService rcs = RestConnectionService.INSTANCE;

   public GenTreeOnlineService() {
        rcs.registerService(this);
    }

    @Override
    public Family getCurrentFamily() {
        return currentFamily.get();
    }


    @Override
    public ServiceResponse setCurrentFamily(Family family) {
        ServiceResponse response = null;

        try {
            response = rcs.retrieveFullFamily(family);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (response instanceof FamilyResponse) {
            family = ((FamilyResponse) response).getFamily();
            currentFamily.setValue(family);
        }
        return response;
    }

    @Override
    public ServiceResponse createFamily(Family f) {
        return rcs.addFamily(f);
    }

    @Override
    public ReadOnlyObjectProperty<Family> familyProperty() {
        return currentFamily;
    }


    @Override
    public ServiceResponse addMember(Member member) {

       ServiceResponse response = rcs.addNewMember(member);

       if(response instanceof MemberWithBornRelationResponse) {
           getCurrentFamily().getMembers().add(((MemberWithBornRelationResponse) response ).getMember());
       }

        return response;
    }

    @Override
    public ServiceResponse updateMember(Member m) {
        return null;
    }

    @Override
    public ServiceResponse addRelation(Relation relation) {
        return null;
    }

    @Override
    public ServiceResponse addRelation(Member left, Member right, RelationType type, boolean active) {
        return null;
    }

    @Override
    public ServiceResponse moveChildrenToNewRelation(Relation to, List<Member> children) {
        return null;
    }

    @Override
    public ServiceResponse updateRelation(Relation relation) {
        return null;
    }

    @Override
    public ServiceResponse moveChildFromRelation(Member m, Relation oldRelation, Relation newRelation) {
        return null;
    }

    @Override
    public ServiceResponse removeRelation(Relation r) {
        return null;
    }


}
