package gentree.server.domain.entity;

import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Martyna SZYMKOWIAK on 18/10/2017.
 */
@Entity
@Table(name="family")
@Setter
public class FamilyEntity implements Serializable {

    private static final long serialVersionUID = -8252895002186338434L;

    private Long version;
    private Long id;
    private String name;
    private OwnerEntity owner;
/*

    private List<MemberEntity> members;
    private List<RelationEntity> relations;
*/

    /*
        GETTERS AND SETTERS
     */

    @Version
    public Long getVersion() {
        return version;
    }

    @Id
    @GeneratedValue(generator = "InvSeqFamily")
    @SequenceGenerator(name = "InvSeqFamily", sequenceName = "INV_SEQ_FAMILY", allocationSize = 5)
    public Long getId() {
        return id;
    }

    @Column(nullable = false)
    public String getName() {
        return name;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "owner_id", nullable = false)
    public OwnerEntity getOwner() {
        return owner;
    }

/*    public List<MemberEntity> getMembers() {
        return members;
    }

    public List<RelationEntity> getRelations() {
        return relations;
    }*/
}

