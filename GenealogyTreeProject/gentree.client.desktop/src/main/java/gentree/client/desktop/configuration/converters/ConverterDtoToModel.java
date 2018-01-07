package gentree.client.desktop.configuration.converters;

import gentree.client.desktop.domain.Family;
import gentree.client.desktop.domain.Member;
import gentree.client.desktop.domain.Relation;
import gentree.client.desktop.service.implementation.ProjectsOnlineFilesService;
import gentree.server.dto.FamilyDTO;
import gentree.server.dto.MemberDTO;
import gentree.server.dto.RelationDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Martyna SZYMKOWIAK on 24/10/2017.
 * Class  to convert DTO objects from REST API to  objects used by this JavaFX Client
 */
@Getter
@Setter
public class ConverterDtoToModel {
    /*
         Family Convert
     */
    private ProjectsOnlineFilesService ps = ProjectsOnlineFilesService.INSTANCE;

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
        target.getRelations().addAll(convertRelationList(source.getRelations(), target));
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
        if (source.getPhotoDTO() != null) {
            String path = ps.decodePicture(source.getPhotoDTO().getEncodedPicture(), source.getPhotoDTO().getName());
            target.setPhoto(path);
        }
        return target;
    }

    public List<Member> convertMemberList(List<MemberDTO> sourceList, boolean convertIfNull, Family f_ref) throws Exception {
        List<Member> targetList = new ArrayList<>();
        System.out.println("source List " + sourceList);
        for (MemberDTO dto : sourceList) {
            Member candidate = findMemberInListById(dto.getId(), f_ref);

            if (candidate == null && !convertIfNull) throw new Exception("NIE MA POSZUKIWANEGO NA LISCIE");

            targetList.add(candidate == null ? convert(dto) : candidate);
        }
        return targetList;
    }


    /*
           Relation Convert
     */

    public Relation convertPoor(RelationDTO source) {
        Relation target = new Relation();
        target.setActive(source.isActive());
        target.setType(source.getType());
        target.setId(source.getId());
        target.setVersion(source.getVersion());
        return target;
    }

    public Relation convert(RelationDTO source, Family f_ref) throws Exception {
        Relation target = convertPoor(source);

        target.setRight(source.getRight() == null ? null : findMemberInListById(source.getRight().getId(), f_ref));
        target.setLeft(source.getLeft() == null ? null : findMemberInListById(source.getLeft().getId(), f_ref));
        if (source.getChildren() != null)
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

    public Member findMemberInListById(Long id, Family f_ref) throws Exception {
        if (f_ref == null) return null;

        List<Member> filtered = f_ref.getMembers().stream().filter(m -> m.getId() == id).collect(Collectors.toList());
        if (filtered.size() > 1) {
            //Should NEVER Happen ID is UNIQUE in DB.
            throw new Exception("NOT UNIQIE ID");

        }
        return filtered.stream().findFirst().orElse(null);
    }

    public void convertTo(Member target, MemberDTO source) {
        target.setId(source.getId());
        target.setName(source.getName());
        target.setSurname(source.getSurname());
        target.setBornname(source.getBornname());
        target.setAge(source.getAge());
        target.setGender(source.getGender());
        target.setAlive(source.isAlive());
        target.setDeathCause(source.getDeathCauses());
        target.setRace(source.getRace());

        if (source.getPhotoDTO() != null || source.getPhotoDTO().getName() != null) {
            target.setPhoto(ps.decodePicture(source.getPhotoDTO().getEncodedPicture(), source.getPhotoDTO().getName()));
        }
    }
}
