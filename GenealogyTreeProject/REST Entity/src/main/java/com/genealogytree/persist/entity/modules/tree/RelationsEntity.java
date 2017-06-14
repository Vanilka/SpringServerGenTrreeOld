package genealogytree.persist.entity.modules.tree;

import genealogytree.domain.enums.RelationType;
import lombok.Setter;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Relations")
@Setter
public class RelationsEntity implements Serializable {


    private static final long serialVersionUID = 3352336478125136159L;
    private Long version;
    private Long id;
    private RelationType relationType;
    private boolean active;
    private MemberEntity simLeft;
    private MemberEntity simRight;
    private FamilyEntity ownerF;
    private List<MemberEntity> children;

    {
        children = new ArrayList<>();
    }
    /*
    * CONSTRUCTORS
     */
    public RelationsEntity() {
    }

    public RelationsEntity(FamilyEntity ownerF, MemberEntity simLeft, MemberEntity simRight, MemberEntity child) {
        this(ownerF, simLeft, simRight, child, RelationType.NEUTRAL, true);
    }

    public RelationsEntity(FamilyEntity ownerF, MemberEntity simLeft, MemberEntity simRight, MemberEntity child, RelationType type, Boolean active) {
        this.relationType = type;
        this.active = active;
        this.simLeft = simLeft;
        this.simRight = simRight;
        this.ownerF = ownerF;
        this.children.add(child);
    }

    public static void merge(RelationsEntity r1, RelationsEntity r2) {
        r1.setSimLeft(r2.getSimLeft());
        r1.setSimRight(r2.getSimRight());
        r1.setRelationType(r2.getRelationType());
        r1.setActive(r2.geActive());
        r2.getChildren().forEach(p -> {
            if(! r1.getChildren().contains(p))  {
                r1.getChildren().add(p);
            }
        });
    }

    /*
    * GETTERS
     */

    @Version
    public Long getVersion() {
        return version;
    }

    @Id
    @GeneratedValue(generator = "InvSeqRelation")
    @SequenceGenerator(name = "InvSeqRelation", sequenceName = "INV_SEQRelation", allocationSize = 5)
    public Long getId() {
        return id;
    }

    @ManyToOne(optional = true)
    @JoinColumn(name = "simLeft_ID", nullable = true)
    public MemberEntity getSimLeft() {
        return simLeft;
    }


    @ManyToOne(optional = true)
    @JoinColumn(name = "simRight_ID", nullable = true)
    public MemberEntity getSimRight() {
        return simRight;
    }

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    public RelationType getRelationType() {
        return relationType;
    }


    @ManyToOne(optional = false)
    @JoinColumn(name = "ownerF_ID", nullable = false)
    public FamilyEntity getOwnerF() {
        return ownerF;
    }

    @Column(name = "active", nullable = false)
    public Boolean geActive() {
        return active;
    }

    @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "relation_ID")
    public List<MemberEntity> getChildren() {
        return children;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("RelationsEntity{");
        sb.append("version=").append(version);
        sb.append(", id=").append(id);
        sb.append(", relationType=").append(relationType);
        sb.append(", active=").append(active);
        sb.append(", simLeft=").append(simLeft);
        sb.append(", simRight=").append(simRight);
        sb.append(", ownerF=").append(ownerF);
        sb.append(", geActive=").append(geActive());
        sb.append('}');
        return sb.toString();
    }
}
