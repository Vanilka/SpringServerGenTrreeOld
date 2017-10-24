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
public class OwnerDTO  implements Serializable {

    private static final long serialVersionUID = 3648409478564178050L;

    protected Long version;
    protected Long id;
    protected String login;
    protected String password;
    protected RoleEnum role;


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OwnerDTO{");
        sb.append("version=").append(version);
        sb.append(", id=").append(id);
        sb.append(", login='").append(login).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", role=").append(role);
        sb.append('}');
        return sb.toString();
    }
}
