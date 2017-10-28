package mobile.client.gentree.gustave.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Martyna SZYMKOWIAK on 27/10/2017.
 */
@Getter
@Setter
public class Family implements Serializable{

    private Long version;
    private Long id;
    private String name;
    private List<Member> members;
    private List<Member> relations;

    private Owner owner;
}
