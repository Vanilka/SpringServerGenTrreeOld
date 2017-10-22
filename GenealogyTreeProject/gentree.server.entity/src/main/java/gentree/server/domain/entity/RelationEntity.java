package gentree.server.domain.entity;

import gentree.common.configuration.enums.RelationType;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martyna SZYMKOWIAK on 18/10/2017.
 */
@Entity
@Table(name = "relation")
@Setter
public class RelationEntity implements Serializable {

    private Long version;
    private Long id;
    private FamilyEntity family;

    private MemberEntity left;
    private MemberEntity right;

    private List<MemberEntity> children = new ArrayList<>();

    private boolean active;

    private RelationType type;

    public RelationEntity() {
    }

    public RelationEntity(MemberEntity left, MemberEntity right, MemberEntity memberEntity) {
        this.family = family;
        this.left = left;
        this.right = right;
        this.children.add(memberEntity);
    }

    /*
     *  GETTERS
     */

    @Version
    public Long getVersion() {
        return version;
    }

    @Id
    @GeneratedValue(generator = "InvSeqRelation")
    @SequenceGenerator(name = "InvSeqRelation", sequenceName = "INV_SEQ_RELATION", allocationSize = 5)
    public Long getId() {
        return id;
    }


    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "family_id", nullable = false)
    public FamilyEntity getFamily() {
        return family;
    }


    @ManyToOne
    @JoinColumn(name = "left")
    public MemberEntity getLeft() {
        return left;
    }

    @ManyToOne
    @JoinColumn(name = "right")
    public MemberEntity getRight() {
        return right;
    }

    @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "bornrelation_id")
    public List<MemberEntity> getChildren() {
        return children;
    }

    @Column(nullable = false)
    public boolean isActive() {
        return active;
    }

    @Column(nullable = false)
    public RelationType getType() {
        return type;
    }
}
