package com.genealogytree.domain.dto;

import com.genealogytree.domain.enums.RelationType;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Martyna SZYMKOWIAK on 18/03/2017.
 */
@Getter
@Setter
public class RelationDTO implements Serializable {

    private Long version;
    private Long id;
    private RelationType relationType;
    private boolean active;
    private MemberDTO simLeft;
    private MemberDTO simRight;
    private FamilyDTO ownerF;
    private List<MemberDTO> children;

    {
        children = new ArrayList<>();
    }

    public RelationDTO() {

    }

    public RelationDTO(MemberDTO simLeft, MemberDTO simRight,
                       FamilyDTO owner, RelationType type, boolean active) {

        this.simLeft = simLeft;
        this.simRight = simRight;
        this.ownerF = owner;
        this.relationType = type;
        this.active = active;
    }


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("RelationDTO{");
        sb.append("version=").append(version);
        sb.append(", id=").append(id);
        sb.append(", relationType=").append(relationType);
        sb.append(", active=").append(active);
        sb.append(", simLeft=").append(simLeft);
        sb.append(", simRight=").append(simRight);
        sb.append(", ownerF=").append(ownerF);
        sb.append(", children=").append(children);
        sb.append('}');
        return sb.toString();
    }
}
