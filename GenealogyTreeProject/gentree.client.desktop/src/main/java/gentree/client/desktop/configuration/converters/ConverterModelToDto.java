package gentree.client.desktop.configuration.converters;

import gentree.client.desktop.domain.Family;
import gentree.client.desktop.domain.Member;
import gentree.client.desktop.service.implementation.ProjectsOnlineFilesService;
import gentree.server.dto.FamilyDTO;
import gentree.server.dto.MemberDTO;
import gentree.server.dto.PhotoDTO;

/**
 * Created by Martyna SZYMKOWIAK on 24/10/2017.
 */
public class ConverterModelToDto {

    private ProjectsOnlineFilesService ps = ProjectsOnlineFilesService.INSTANCE;

    public FamilyDTO convertLazy(Family source) {
        FamilyDTO dto = new FamilyDTO();
        dto.setVersion(source.getVersion());
        dto.setId(source.getId());
        dto.setName(source.getName());
        return dto;
    }

    public MemberDTO convert(Member member) {
        MemberDTO dto = new MemberDTO();
        dto.setVersion(member.getVersion());
        dto.setId(member.getId());
        dto.setName(member.getName());
        dto.setSurname(member.getSurname());
        dto.setBornname(member.getBornname());
        dto.setAge(member.getAge());
        dto.setGender(member.getGender());
        dto.setRace(member.getRace());
        dto.setAlive(member.isAlive());
        dto.setDeathCauses(member.getDeathCause());

        if(ps.needCopy(member.getPhoto())) {
            dto.setPhotoDTO(new PhotoDTO(ps.encodePicture(member.getPhoto())));
        }
        return dto;
    }


}
