package gentree.server.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by vanilka on 05/12/2017.
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PhotoDTO implements Serializable {

    private static final long serialVersionUID = 8288654579631354780L;

    private Long version;
    private Long id;
    private String name;
    private String encodedPicture;

    public PhotoDTO() {}
    public PhotoDTO(String encodedPicture) {
    this.encodedPicture = encodedPicture;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PhotoDTO{");
        sb.append("version=").append(version);
        sb.append(", id=").append(id);
        sb.append(", name='").append(name).append('\'');
        if(encodedPicture != null) {
            sb.append(", encodedPicture='").append("(..)").append('\'');
        }
        sb.append('}');
        return sb.toString();
    }
}
