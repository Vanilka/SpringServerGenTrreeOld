package mobile.client.gentree.gustave.domain;

import gentree.common.configuration.enums.RelationType;
import gentree.server.dto.FamilyDTO;
import gentree.server.dto.MemberDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by Martyna SZYMKOWIAK on 27/10/2017.
 */
@Getter
@Setter
public class Relation {

    private Long version;
    private Long id;
    private Member left;
    private Member right;
    private List<MemberDTO> children;
    private boolean active;
    private RelationType type;
}
