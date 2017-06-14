package genealogytree.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by Martyna SZYMKOWIAK on 18/03/2017.
 */
@Getter
@Setter
public class UserDTO implements Serializable {

    private static final long serialVersionUID = -5522754339726016232L;

    private Long version;
    private Long id;
    private String login;
    private String email;
    private String password;
}
