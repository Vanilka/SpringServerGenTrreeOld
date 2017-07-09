package gentree.client.desktop.service;

import gentree.client.desktop.domain.Family;
import gentree.client.desktop.domain.Member;
import gentree.client.desktop.service.responses.ServiceResponse;

/**
 * Created by Martyna SZYMKOWIAK on 01/07/2017.
 */
public interface FamilyService  {

    Family getCurrentFamily();
    void setCurrentFamily(Family family);

    ServiceResponse addMember(Member member);
}
