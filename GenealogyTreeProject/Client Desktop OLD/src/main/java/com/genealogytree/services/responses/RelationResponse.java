package genealogytree.services.responses;

import genealogytree.domain.GTX_Relation;

/**
 * Created by vanilka on 31/12/2016.
 */
public class RelationResponse extends ServerResponse {

    GTX_Relation relation;

    public RelationResponse(GTX_Relation relation) {
        super(ResponseStatus.OK);
    }

    public GTX_Relation getRelation() {
        return relation;
    }

    public void setRelation(GTX_Relation relation) {
        this.relation = relation;
    }

    @Override
    public String toString() {
        return "RelationResponse{" +
                "relation=" + relation +
                "} " + super.toString();
    }
}
