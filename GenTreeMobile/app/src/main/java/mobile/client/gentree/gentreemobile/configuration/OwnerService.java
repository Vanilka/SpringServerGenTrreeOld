package mobile.client.gentree.gentreemobile.configuration;

import android.widget.ListView;
import gentree.server.dto.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by vanilka on 22/12/2017.
 */
@Getter
public class OwnerService {

    public final static OwnerService INSTANCE  = new OwnerService();

    OwnerExtendedDTO owner;
    FamilyDTO family;


    private OwnerService() {

    }

    public void setOwner(OwnerExtendedDTO owner) {
        this.owner = owner;
    }

    public void setFamily(FamilyDTO family) {
        this.family = family;
        updateReferences();
    }

    public void updateReferences() {
        for (RelationDTO r:  family.getRelations()) {
            r.setLeft(updateMemberInList(r.getLeft()));
            r.setRight(updateMemberInList(r.getRight()));
            r.setChildren(updateChildrenList(r.getChildren()));

        }
    }

    private MemberDTO updateMemberInList(MemberDTO memberDTO) {
        if(memberDTO == null) return null;
        MemberDTO target = memberDTO;
        for(int i =0; i < family.getMembers().size(); i++) {

            System.out.println("From family : " +family.getMembers().get(i));
            System.out.println("Parameter : " +memberDTO);

           if(Objects.equals(family.getMembers().get(i).getId(), memberDTO.getId())) {
                 target = family.getMembers().get(i);
                 i = family.getMembers().size();
            }
        }
        return target;
    }

    private List<MemberDTO> updateChildrenList(List<MemberDTO> from) {
        List<MemberDTO> targetList = new ArrayList<>();

        if(from == null || from.isEmpty()) return targetList;

        for(MemberDTO m : from) {
            MemberDTO dto = updateMemberInList(m);
            if(!m.equals(dto)) {
                targetList.add(dto);
            }
        }
        return targetList;
    }
}
