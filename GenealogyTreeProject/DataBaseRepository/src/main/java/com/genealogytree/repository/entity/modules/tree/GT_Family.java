package com.genealogytree.repository.entity.modules.tree;

import com.genealogytree.domain.beans.FamilyBean;
import com.genealogytree.repository.entity.modules.administration.GT_User;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Family")
public class GT_Family extends FamilyBean implements Serializable {

    private static final long serialVersionUID = -3584168502436384150L;

    /**
     * This is an Entity Class represent  Family / Project in Database
     */


    private GT_User owner;

    public GT_Family() {
        super();
    }

    public GT_Family(String name, GT_User owner) {
        super(name);
        this.owner = owner;
    }

    /**
     * GETTERS
     */
    @Override
    @Version
    public Long getVersion() {
        return version;
    }

    @Override
    @Id
    @GeneratedValue(generator = "InvSeqFam")
    @SequenceGenerator(name = "InvSeqFam", sequenceName = "INV_SEQF", allocationSize = 5)
    public Long getId() {
        return this.id;
    }

    @Override
    @Column(nullable = false)
    public String getName() {
        return this.name;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "ownerID", nullable = false)
    // @Lob
    public GT_User getOwner() {
        return owner;
    }

    /**
     * SETTERS
     */

    @Override
    @Version
    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public void setOwner(GT_User owner) {
        this.owner = owner;
    }


}
