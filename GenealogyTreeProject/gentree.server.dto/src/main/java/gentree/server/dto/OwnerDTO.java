package gentree.server.dto;

import gentree.server.configuration.enums.RoleEnum;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by Martyna SZYMKOWIAK on 16/10/2017.
 */
@Getter
@Setter
public class OwnerDTO implements Serializable {

    private static final long serialVersionUID = 3648409478564178050L;

    private Long version;
    private Long id;
    private String login;
    private String password;
    private RoleEnum role;

}
