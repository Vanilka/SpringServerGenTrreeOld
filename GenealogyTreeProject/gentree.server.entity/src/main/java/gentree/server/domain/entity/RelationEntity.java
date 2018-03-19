package gentree.server.domain.entity;

import gentree.common.configuration.enums.RelationType;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Martyna SZYMKOWIAK on 18/10/2017.
 */
@Entity
@Table(name = "relations")
@Setter
public class RelationEntity implements Serializable {

    private static final long serialVersionUID = 8765417762976233971L;
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

    public RelationEntity(MemberEntity left, MemberEntity right, MemberEntity memberEntity, FamilyEntity family) {
        this.family = family;
        this.left = left;
        this.right = right;
        this.children.add(memberEntity);
    }

    public boolean compareLeft(Object o) {
        if (getLeft() == null && o == null) return true;
        if (getLeft() == null || o == null) return false;
        if (getLeft() == o) return true;
        MemberEntity other = (MemberEntity) o;
        return Objects.equals(getLeft().getId(), other.getId());
    }

    public boolean compareRight(Object o) {
        if (getRight() == null && o == null) return true;
        if (getRight() == null || o == null) return false;
        if (getRight() == o) return true;
        MemberEntity other = (MemberEntity) o;
        return Objects.equals(getRight().getId(), other.getId());
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


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "left_id")
    public MemberEntity getLeft() {
        return left;
    }

    public void setLeft(MemberEntity left) {
        if (compareLeft(left)) return;
        this.left = left;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "right_id")
    public MemberEntity getRight() {
        return right;
    }

    public void setRight(MemberEntity right) {
        if (compareRight(right)) return;
        this.right = right;
    }

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "bornrelation_id", insertable = true, updatable = true)
    public List<MemberEntity> getChildren() {
        return children;
    }


    @Column(nullable = false)
    public boolean isActive() {
        return active;
    }

    @Column(nullable = false)
    public RelationType getType() {
        return type == null ? RelationType.NEUTRAL : type;
    }

    public void setType(RelationType type) {
        this.type = type == null ? RelationType.NEUTRAL : type;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("RelationEntity{");
        sb.append("version=").append(version);
        sb.append(", id=").append(id);
        sb.append(", family=").append(family);
        sb.append(", left=").append(left);
        sb.append(", right=").append(right);
        sb.append(", children=").append(children);
        sb.append(", active=").append(active);
        sb.append(", type=").append(type);
        sb.append('}');
        return sb.toString();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RelationEntity)) return false;
        RelationEntity that = (RelationEntity) o;
        return Objects.equals(version, that.version) &&
                Objects.equals(id, that.id) &&
                Objects.equals(left, that.left) &&
                Objects.equals(right, that.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(version, id, left, right);
    }
}
