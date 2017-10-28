package mobile.client.gentree.gustave.domain;

import gentree.common.configuration.enums.Age;
import gentree.common.configuration.enums.DeathCauses;
import gentree.common.configuration.enums.Gender;
import gentree.common.configuration.enums.Race;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Martyna SZYMKOWIAK on 27/10/2017.
 */
@Getter
@Setter
public class Member {

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


}
