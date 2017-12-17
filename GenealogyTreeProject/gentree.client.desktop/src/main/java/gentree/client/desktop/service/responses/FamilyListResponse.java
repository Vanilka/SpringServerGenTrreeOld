package gentree.client.desktop.service.responses;

import gentree.client.desktop.domain.Family;
import gentree.client.desktop.responses.ServiceResponse;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martyna SZYMKOWIAK on 23/10/2017.
 */
@Getter
public class FamilyListResponse extends ServiceResponse {

    List<Family> list = new ArrayList<>();


    public FamilyListResponse(List<Family> familyList) {
        super(ServiceResponse.ResponseStatus.OK);
        this.list = familyList;
    }
}
