package com.genealogytree.domain.dto;

import com.genealogytree.domain.enums.Age;
import com.genealogytree.domain.enums.Sex;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by Martyna SZYMKOWIAK on 18/03/2017.
 */
@Getter
@Setter
public class MemberDTO implements Serializable {

    private Long version;
    private Long id;
    private String name;
    private String surname;
    private Age age;
    private Sex sex;
    private FamilyDTO ownerF;
    private ImageDTO image;

    public MemberDTO() {

    }

}
