package gentree.client.desktop.service.responses;

import gentree.client.desktop.domain.Relation;
import gentree.client.desktop.responses.ServiceResponse;

/**
 * Created by Martyna SZYMKOWIAK on 07/07/2017.
 */
public class RelationResponse extends ServiceResponse {

    Relation relation;

    public RelationResponse(Relation relation) {
        super(ServiceResponse.ResponseStatus.OK);
        this.relation = relation;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("RelationResponse{");
        sb.append("relation=").append(relation);
        sb.append('}');
        return sb.toString();
    }
}
