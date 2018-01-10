package gentree.server.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by Martyna SZYMKOWIAK on 24/10/2017.
 */
@Getter
@Setter
public class NewMemberDTO implements Serializable {

    private MemberDTO memberDTO;
    private RelationDTO relationDTO;
}
