package mobile.client.gentree.gustave.domain;

import gentree.common.configuration.enums.RoleEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Martyna SZYMKOWIAK on 27/10/2017.
 */
@Getter
@Setter
public class Owner {

    protected Long version;
    protected Long id;
    protected String login;
    protected String password;
    protected RoleEnum role;
}
