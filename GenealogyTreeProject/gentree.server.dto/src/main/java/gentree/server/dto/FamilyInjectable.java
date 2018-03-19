package gentree.server.dto;

import gentree.server.dto.FamilyDTO;

/**
 * Created by vanilka on 16/01/2018.
 */
public interface FamilyInjectable {

    void setFamily(FamilyDTO family);
    FamilyDTO getFamily();
}
