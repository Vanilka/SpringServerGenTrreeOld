package gentree.client.desktop.service.responses;

import gentree.client.desktop.domain.Relation;
import gentree.client.desktop.responses.ServiceResponse;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martyna SZYMKOWIAK on 29/10/2017.
 */
@Getter
public class RelationListResponse extends ServiceResponse {

    List<Relation> list = new ArrayList<>();


    public RelationListResponse(List<Relation> familyList) {
        super(ServiceResponse.ResponseStatus.OK);
        this.list = familyList;
    }
}
