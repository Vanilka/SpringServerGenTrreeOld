package genealogytree.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by Martyna SZYMKOWIAK on 18/03/2017.
 */
@Getter
@Setter
public class FamilyDTO implements Serializable {

    private static final long serialVersionUID = -4729604686231660281L;

    private Long version;
    private Long id;
    private String name;
    private UserDTO owner;


}
