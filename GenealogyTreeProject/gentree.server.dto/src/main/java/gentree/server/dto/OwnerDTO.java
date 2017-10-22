package gentree.server.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import gentree.common.configuration.enums.RoleEnum;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martyna SZYMKOWIAK on 16/10/2017.
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OwnerDTO implements Serializable {

    private static final long serialVersionUID = 3648409478564178050L;

    private Long version;
    private Long id;
    private String login;
    private String password;
    private RoleEnum role;
    private List<FamilyDTO> familyList = new ArrayList<>();

}
