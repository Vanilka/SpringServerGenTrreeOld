package gentree.client.desktop.configuration.converters;

import gentree.client.desktop.domain.Family;
import gentree.client.desktop.domain.Member;
import gentree.server.dto.FamilyDTO;
import gentree.server.dto.MemberDTO;

import javax.xml.transform.Source;

/**
 * Created by Martyna SZYMKOWIAK on 24/10/2017.
 */
public enum ConverterModelToDto {
    INSTANCE;

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
        return dto;
    }


}
