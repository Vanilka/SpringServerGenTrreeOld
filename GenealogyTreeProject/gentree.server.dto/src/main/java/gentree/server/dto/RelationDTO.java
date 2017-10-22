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

}
