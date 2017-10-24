package gentree.client.desktop.configuration.converters;

import gentree.client.desktop.domain.Family;
import gentree.client.desktop.domain.Member;
import gentree.client.desktop.domain.Relation;
import gentree.client.desktop.service.implementation.GenTreeOnlineService;
import gentree.server.dto.FamilyDTO;
import gentree.server.dto.MemberDTO;
import gentree.server.dto.RelationDTO;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Martyna SZYMKOWIAK on 24/10/2017.
 */
@Getter
@Setter
public class ConverterDtoToModel {

    private GenTreeOnlineService service;

    /*
         Family Convert
     */

    public Family convertLazy(FamilyDTO source) {
        Family target = new Family();
        target.setVersion(source.getVersion());
        target.setId(source.getId());
        target.setName(source.getName());
        return target;
    }

    public Family convertFull(FamilyDTO source) throws Exception {
        Family target = convertLazy(source);
        target.getMembers().addAll(convertMemberList(source.getMembers(), true, target));
        System.out.println(target.getMembers());
        target.getRelations().addAll(convertRelationList(source.getRelations(), target));
        System.out.println(target.getRelations());
        return target;
    }


    /*
           Member Convert
     */

    public Member convertLazy(MemberDTO source) {
        Member target = new Member();
        target.setVersion(source.getVersion());
        target.setId(source.getId());
        return target;
    }

    public Member convert(MemberDTO source) {
        Member target = convertLazy(source);
        target.setId(source.getId());
        target.setName(source.getName());
        target.setSurname(source.getSurname());
        target.setBornname(source.getBornname());
        target.setAge(source.getAge());
        target.setGender(source.getGender());
        target.setAlive(source.isAlive());
        target.setDeathCause(source.getDeathCauses());
        target.setRace(source.getRace());
        return target;
    }

    public List<Member> convertMemberList(List<MemberDTO> sourceList, boolean convertIfNull, Family family) throws Exception {
        List<Member> targetList = new ArrayList<>();
        for (MemberDTO dto : sourceList) {
            Member candidate = findMemberInListById(dto.getId(), family);

            if(candidate == null &&  !convertIfNull) throw new Exception("NIE MA POSZUKIWANEGO NA LISCIE");

            targetList.add(candidate == null ? convert(dto) : candidate);
        }
        return targetList;
    }


    /*
           Relation Convert
     */

    public Relation convert(RelationDTO source, Family f_ref) throws Exception {
        Relation target = new Relation();
        target.setRight(source.getRight() == null ? null : findMemberInListById(source.getRight().getId()));
        target.setLeft( source.getLeft() == null ? null : findMemberInListById(source.getLeft().getId()));
        target.getChildren().addAll(convertMemberList(source.getChildren(), false, f_ref));
        return target;
    }

    public List<Relation> convertRelationList(List<RelationDTO> sourceList, Family f_ref) throws Exception {
        List<Relation> targetList = new ArrayList<>();
        for (RelationDTO dto : sourceList) {
            targetList.add(convert(dto, f_ref));
        }
        return targetList;
    }

    public Member findMemberInListById(Long id) throws Exception {
        return findMemberInListById(id, getService().getCurrentFamily());
    }


    public Member findMemberInListById(Long id, Family f_ref) throws Exception {
        if (f_ref == null) return  null;

        List<Member> filtered = f_ref.getMembers().filtered(m -> m.getId() == id);
        if(filtered.size() > 1) {
            //Should NEVER Happen ID is UNIQUE in DB.
            throw new Exception("NOT UNIQIE ID");

        }
        return filtered.stream().findFirst().orElse(null);
    }
}
