package gentree.server.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import gentree.common.configuration.enums.RoleEnum;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martyna SZYMKOWIAK on 23/10/2017.
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OwnerExtendedDTO extends OwnerDTO implements Serializable {

    private static final long serialVersionUID = 5902360958959840384L;

    private List<FamilyDTO> familyList = new ArrayList<>();

    public OwnerExtendedDTO() {
        super();
    }
}
