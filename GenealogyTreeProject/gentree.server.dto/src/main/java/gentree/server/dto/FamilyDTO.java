package gentree.server.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
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
public class FamilyDTO implements Serializable {

    private static final long serialVersionUID = 8478895637289458800L;

    private Long version;
    private Long id;
    private String name;
    private List<MemberDTO> members;
    private List<RelationDTO> relations;

    private OwnerDTO owner;


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FamilyDTO{");
        sb.append("version=").append(version);
        sb.append(", id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", members=").append(members);
        sb.append(", relations=").append(relations);
        sb.append(", owner=").append(owner);
        sb.append('}');
        return sb.toString();
    }
}
