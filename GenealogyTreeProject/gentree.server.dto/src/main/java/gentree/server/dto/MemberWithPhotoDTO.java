package gentree.server.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Martyna SZYMKOWIAK on 20/10/2017.
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemberWithPhotoDTO extends MemberDTO {

    private PhotoDTO photoDTO;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MemberWithPhotoDTO{");
        sb.append("photoDTO=").append(photoDTO);
        sb.append(", bornname='").append(getBornname()).append('\'');
        sb.append(", deathCauses=").append(getDeathCauses());
        sb.append(", family=").append(getFamily());
        sb.append(", id=").append(getId());
        sb.append(", name='").append(getName()).append('\'');
        sb.append(", surname='").append(getSurname()).append('\'');
        sb.append(", version=").append(getVersion());
        sb.append(", alive=").append(isAlive());
        sb.append(", age=").append(getAge());
        sb.append(", race=").append(getRace());
        sb.append(", gender=").append(getGender());
        sb.append('}');
        return sb.toString();
    }
}
