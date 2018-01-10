package gentree.server.service.wrappers;

import gentree.server.domain.entity.MemberEntity;
import gentree.server.domain.entity.RelationEntity;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by Martyna SZYMKOWIAK on 24/10/2017.
 */
@Getter
@Setter
public class NewMemberWrapper implements Serializable {

    private static final long serialVersionUID = -8745114705223544055L;

    MemberEntity member;
    RelationEntity bornRelation;

}
