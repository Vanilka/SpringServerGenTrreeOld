package com.genealogytree.repository.entity.modules.tree;

import com.genealogytree.domain.beans.RelationBean;
import com.genealogytree.domain.enums.RelationType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Relations")
public class GT_Relations extends RelationBean {


    private GT_Member simLeft;

    private GT_Member simRight;

    private GT_Family ownerF;

    private List<GT_Member> children;

    {
        children = new ArrayList<>();
    }
    /*
    * CONSTRUCTORS
     */
    public GT_Relations() {
        this(null, null, null, null);
    }
    public GT_Relations(GT_Member simLeft, GT_Member  simRight, RelationType type, Boolean active) {
        super(type, active);
        this.simLeft = simLeft;
        this.simRight = simRight;
    }


    public void addChild(GT_Member member) {
        if(! children.contains(member)) {
            children.add(member);
        }
    }

    /*
    * GETTERS
     */
    @Override
    @Version
    public Long getVersion() {
        return version;
    }

    @Override
    @Id
    @GeneratedValue(generator = "InvSeqRelation")
    @SequenceGenerator(name = "InvSeqRelation", sequenceName = "INV_SEQRelation", allocationSize = 5)
    public Long getId() {
        return id;
    }

    @Override
    @ManyToOne(optional = true)
    @JoinColumn(name = "simLeft_ID", nullable = true)
    public GT_Member getSimLeft() {
        return simLeft;
    }

    @Override
    @ManyToOne(optional = true)
    @JoinColumn(name = "simRight_ID", nullable = true)
    public GT_Member getSimRight() {
        return simRight;
    }

    @Override
    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    public RelationType getRelationType() {
        return relationType;
    }

    @Override
    @ManyToOne(optional = false)
    @JoinColumn(name = "ownerF_ID", nullable = false)
    public GT_Family getOwnerF() {
        return ownerF;
    }

    @Column(name="active", nullable = false)
    public Boolean geActive() {
        return active;
    }

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "relation_ID")
    public List<GT_Member> getChildren() {
        return children;
    }

    /*
    * SETTERS
     */

    public void setId(Long id) {
        this.id = id;
    }


    public void setSimLeft(GT_Member simLeft) {
        this.simLeft = simLeft;
    }

    public void setSimRight(GT_Member simRight) {
        this.simRight = simRight;
    }

    public void setOwnerF(GT_Family ownerF) {
        this.ownerF = ownerF;
    }

    public void setRelationType(RelationType relationType) {
        this.relationType = relationType;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public void setChildren(List<GT_Member> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "GT_Relations{" +
                "simLeft=" + simLeft +
                ", simRight=" + simRight +
                ", ownerF=" + ownerF +
                ", children=" + children +
                '}';
    }
}
