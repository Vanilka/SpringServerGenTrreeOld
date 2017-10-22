package gentree.server.domain.entity;

import gentree.common.configuration.enums.Age;
import gentree.common.configuration.enums.DeathCauses;
import gentree.common.configuration.enums.Gender;
import gentree.common.configuration.enums.Race;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Martyna SZYMKOWIAK on 18/10/2017.
 */
@Entity
@Table(name = "member")
@Setter
public class MemberEntity implements Serializable {

    private static final long serialVersionUID = -2908410045473436618L;

    private Long version;
    private Long id;
    private FamilyEntity family;
    private String name;
    private String surname;
    private String bornname;
    private boolean alive;
    private DeathCauses deathCauses;
    private Age age;
    private Race race;
    private Gender gender;

    /*
     *  GETTERS
     */

    @Version
    public Long getVersion() {
        return version;
    }

    @Id
    @GeneratedValue(generator = "InvSeqMember")
    @SequenceGenerator(name = "InvSeqMember", sequenceName = "INV_SEQ_MEMBER", allocationSize = 5)
    public Long getId() {
        return id;
    }

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "family_id", nullable = false)
    public FamilyEntity getFamily() {
        return family;
    }

    @Column(nullable = false)
    public String getName() {
        return name;
    }

    @Column(nullable = false)
    public String getSurname() {
        return surname;
    }

    @Column
    public String getBornname() {
        return bornname;
    }



    @Column(nullable = false)
    public boolean isAlive() {
        return alive;
    }

    @Column
    public DeathCauses getDeathCauses() {
        return deathCauses;
    }

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    public Age getAge() {
        return age == null ? Age.YOUNG_ADULT : age;
    }

    public void setAge(Age age) {
        this.age = (age == null ? Age.YOUNG_ADULT : age);
    }

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    public Race getRace() {
        return race == null ? Race.HUMAIN : race;
    }

    public void setRace(Race race) {
        this.race = (race == null ? Race.HUMAIN : race);
    }

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    public Gender getGender() {
        return gender == null ? Gender.M : gender;
    }

    public void setGender(Gender gender) {
        this.gender = (gender == null ? Gender.M : gender);
    }
}
