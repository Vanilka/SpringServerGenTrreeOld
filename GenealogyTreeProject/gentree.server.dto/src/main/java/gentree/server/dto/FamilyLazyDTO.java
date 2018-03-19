package gentree.server.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by Martyna SZYMKOWIAK on 23/10/2017.
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FamilyLazyDTO implements Serializable {

    private static final long serialVersionUID = 3499668686409672259L;

    private Long version;
    private Long id;
    private String name;

    private OwnerDTO owner;


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FamilyLazyDTO{");
        sb.append("version=").append(version);
        sb.append(", id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", owner=").append(owner);
        sb.append('}');
        return sb.toString();
    }
}
