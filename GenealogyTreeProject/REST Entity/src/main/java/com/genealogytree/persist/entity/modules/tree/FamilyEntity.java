package genealogytree.persist.entity.modules.tree;

import genealogytree.persist.entity.modules.administration.UserEntity;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Family")
@Setter
public class FamilyEntity implements Serializable {

    private static final long serialVersionUID = -3584168502436384150L;

    /**
     * This is an Entity Class represent  Family / Project in Database
     */
    private Long version;
    private Long id;
    private String name;
    private UserEntity owner;

    public FamilyEntity() {
        super();
    }

    public FamilyEntity(String name, UserEntity owner) {
        this.name = name;
        this.owner = owner;
    }

    /*
     * GETTERS
     */

    @Version
    public Long getVersion() {
        return version;
    }

    @Id
    @GeneratedValue(generator = "InvSeqFam")
    @SequenceGenerator(name = "InvSeqFam", sequenceName = "INV_SEQF", allocationSize = 5)
    public Long getId() {
        return this.id;
    }

    @Column(nullable = false)
    public String getName() {
        return this.name;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "ownerID", nullable = false)
    // @Lob
    public UserEntity getOwner() {
        return owner;
    }


}
