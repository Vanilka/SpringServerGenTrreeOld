package genealogytree.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by Martyna SZYMKOWIAK on 18/03/2017.
 */
@Getter
@Setter
public class ImageDTO implements Serializable {

    private Long version;
    private Long id;
    private String name;
    private byte[] content;

    public ImageDTO() {
        this(null, null);
    }

    public ImageDTO(String name, byte[] content) {
        this.name = name;
        this.content = content;
    }

}
