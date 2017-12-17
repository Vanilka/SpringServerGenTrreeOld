package gentree.server.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import gentree.common.configuration.enums.RelationType;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Martyna SZYMKOWIAK on 18/10/2017.
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RelationDTO implements Serializable {

    private static final long serialVersionUID = 6259752712327969219L;

    private Long version;
    private Long id;
    private MemberDTO left;
    private MemberDTO right;
    private List<MemberDTO> children;
    private boolean active;
    private RelationType type;
    private FamilyDTO family;


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("RelationDTO{");
        sb.append("version=").append(version);
        sb.append(", id=").append(id);
        sb.append(", left=").append(left);
        sb.append(", right=").append(right);
        sb.append(", children=").append(children);
        sb.append(", active=").append(active);
        sb.append(", type=").append(type);
        sb.append(", family=").append(family);
        sb.append('}');
        return sb.toString();
    }
}
