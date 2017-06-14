package genealogytree.client.desktop.service.responses;


import genealogytree.client.desktop.domain.GTX_Family;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by vanilka on 19/11/2016.
 */
@Setter
@Getter
public class FamilyResponse extends ServiceResponse {

    GTX_Family family;

    public FamilyResponse(GTX_Family bean) {
        super(ServiceResponse.ResponseStatus.OK);
        this.family = bean;
    }

    @Override
    public String toString() {
        return "FamilyResponse{" +
                "family=" + family +
                "} " + super.toString();
    }
}
