package gentree.server.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by Martyna SZYMKOWIAK on 18/10/2017.
 */
@Getter
@Setter
public class FamilyDTO implements Serializable{

    private static final long serialVersionUID = 8478895637289458800L;

    private Long version;
    private Long id;
    private String name;
    private OwnerDTO owner;

}
