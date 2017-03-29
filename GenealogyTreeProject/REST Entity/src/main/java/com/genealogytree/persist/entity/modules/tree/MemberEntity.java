package com.genealogytree.persist.entity.modules.tree;

import com.genealogytree.domain.enums.Age;
import com.genealogytree.domain.enums.Sex;
import lombok.Setter;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Member")
@Setter
public class MemberEntity implements Serializable {

    /**
     * This is an Entity Class represent  Member in Database
     */
    private Long version;
    private Long id;
    private String name;
    private String surname;
    private Age age;
    private Sex sex;

    private FamilyEntity ownerF;
    private ImagesEntity image;

    /**
     * Constructor
     */
    public MemberEntity() {
        super();
    }

    public MemberEntity(String name, String surname, Age age, Sex sex, FamilyEntity ownerF) {
        this(name, surname, age, sex, ownerF, null, null, null);
    }

    public MemberEntity(String name, String surname, Age age, Sex sex,
                        FamilyEntity ownerF, ImagesEntity image, MemberEntity mother, MemberEntity father) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.sex = sex;
        this.ownerF = ownerF;
        this.image = image;
    }

    /*
    *  GETTERS
    */

    @Version
    public Long getVersion() {
        return version;
    }

    @Id
    @GeneratedValue(generator = "InvSeqMember")
    @SequenceGenerator(name = "InvSeqMember", sequenceName = "INV_SEQMember", allocationSize = 5)
    public Long getId() {
        return id;
    }


    @Column(nullable = false)
    public String getName() {
        return name;
    }


    @Column(nullable = false)
    public String getSurname() {
        return surname;
    }


    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    public Age getAge() {
        return age;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "ownerF_ID", nullable = false)
    public FamilyEntity getOwnerF() {
        return ownerF;
    }

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    public Sex getSex() {
        return sex;
    }


    @OneToOne(optional = true, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "Image_ID", nullable = true)
    public ImagesEntity getImage() {
        return image;
    }


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("MemberEntity{");
        sb.append("version=").append(version);
        sb.append(", id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", surname='").append(surname).append('\'');
        sb.append(", age=").append(age);
        sb.append(", sex=").append(sex);
        sb.append(", ownerF=").append(ownerF);
        sb.append(", image=").append(image);
        sb.append('}');
        return sb.toString();
    }
}
