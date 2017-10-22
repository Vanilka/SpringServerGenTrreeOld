package gentree.server.dto;

import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martyna SZYMKOWIAK on 18/10/2017.
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FamilyDTO implements Serializable{

    private static final long serialVersionUID = 8478895637289458800L;

    private Long version;
    private Long id;
    private String name;
    private List<MemberDTO> members = new ArrayList<>();
    private List<RelationDTO> relations = new ArrayList<>();

}
