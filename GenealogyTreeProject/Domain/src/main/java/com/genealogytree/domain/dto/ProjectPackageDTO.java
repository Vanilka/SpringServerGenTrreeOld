package genealogytree.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Martyna SZYMKOWIAK on 18/03/2017.
 */
@Getter
@Setter
public class ProjectPackageDTO implements Serializable {

    private UserDTO user;
    private FamilyDTO familyDTO;
    private List<MemberDTO> members;
    private List<RelationDTO> relations;

}
