package gentree.server.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import gentree.common.configuration.enums.Age;
import gentree.common.configuration.enums.DeathCauses;
import gentree.common.configuration.enums.Gender;
import gentree.common.configuration.enums.Race;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by Martyna SZYMKOWIAK on 18/10/2017.
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemberDTO implements Serializable {

    private static final long serialVersionUID = 4679095765884538078L;

    private Long version;
    private Long id;
    private String name;
    private String surname;
    private String bornname;
    private boolean alive;
    private DeathCauses deathCauses;
    private Age age;
    private Race race;
    private Gender gender;


    /*
        Getters & Setters
     */
    public Age getAge() {
        return age == null ? Age.YOUNG_ADULT : age;
    }

    public void setAge(Age age) {
        this.age = (age == null ? Age.YOUNG_ADULT : age);
    }

    public Race getRace() {
        return race == null ? Race.HUMAIN : race;
    }

    public void setRace(Race race) {
        this.race = (race == null ? Race.HUMAIN : race);
    }

    public Gender getGender() {
        return gender == null ? Gender.M : gender;
    }

    public void setGender(Gender gender) {
        this.gender = (gender == null ? Gender.M : gender);
    }
}
