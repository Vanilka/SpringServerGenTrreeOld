package gentree.client.desktop.service.implementation;

import gentree.client.desktop.domain.Family;
import gentree.client.desktop.domain.Member;
import gentree.client.desktop.domain.Relation;
import gentree.client.desktop.responses.ServiceResponse;
import gentree.client.desktop.service.FamilyService;
import gentree.client.desktop.service.RestConnectionService;
import gentree.client.desktop.service.responses.FamilyResponse;
import gentree.client.desktop.service.responses.MemberResponse;
import gentree.client.desktop.service.responses.MemberWithBornRelationResponse;
import gentree.client.desktop.service.responses.RelationListResponse;
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
        if (response instanceof MemberWithBornRelationResponse) {
            getCurrentFamily().getMembers().add(((MemberWithBornRelationResponse) response).getMember());
            getCurrentFamily().getRelations().add(((MemberWithBornRelationResponse) response).getRelation());
            sm.getGenTreeDrawingService().startDraw();
        }

        return response;
    }

    @Override
    public ServiceResponse deleteMember(Member m) {
        ServiceResponse response = rcs.deleteMember(m);
        if (response instanceof FamilyResponse) {
            setCurrentFamily(((FamilyResponse) response).getFamily());
            invalidate();
        }
        return response;
    }

    @Override
    public ServiceResponse updateMember(Member m) {
        ServiceResponse response = rcs.updateMember(m);
        if (response instanceof MemberResponse) {

        }
        return response;
    }

    @Override
    public ServiceResponse addRelation(Relation relation) {
        ServiceResponse response = rcs.addRelation(relation);
        if(response instanceof RelationListResponse) {
            getCurrentFamily().getRelations().clear();
            getCurrentFamily().getRelations().addAll(((RelationListResponse) response).getList());
            invalidate();
        }
        return null;
    }

    @Override
    public ServiceResponse addRelation(Member left, Member right, RelationType type, boolean active) {
        return addRelation(createRelationFrom(left, right, type, active));
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
        ServiceResponse response = rcs.removeRelation(r);
        return null;
    }

    @Override
    public ReadOnlyObjectProperty<Family> currentFamilyPropertyI() {
        return currentFamily;
    }

    private void invalidate() {
        sm.getGenTreeDrawingService().startDraw();
    }

    @Override
    public void clean() {

    }
}
