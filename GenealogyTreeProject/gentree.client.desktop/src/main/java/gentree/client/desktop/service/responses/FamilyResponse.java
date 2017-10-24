package gentree.client.desktop.service.responses;

import gentree.client.desktop.domain.Family;
import gentree.client.desktop.responses.ServiceResponse;
import lombok.Getter;

/**
 * Created by Martyna SZYMKOWIAK on 24/10/2017.
 */
@Getter
public class FamilyResponse extends ServiceResponse {

    private final Family family;


    public FamilyResponse(Family family) {
        super(ServiceResponse.ResponseStatus.OK);
        this.family = family;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FamilyResponse{");
        sb.append("family=").append(family);
        sb.append('}');
        return sb.toString();
    }
}
