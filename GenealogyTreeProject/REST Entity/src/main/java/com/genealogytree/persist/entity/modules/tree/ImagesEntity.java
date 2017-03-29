package com.genealogytree.persist.entity.modules.tree;

import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by vanilka on 27/12/2016.
 */
@Entity
@Table(name = "Images")
@Setter
public class ImagesEntity implements Serializable {

    private static final long serialVersionUID = -1276048332505795942L;
    private static final String IMG_NAME_PREFIX = "GT_";
    private static final String DATE_FORMAT = "yyyyMMddHHmmss";

    private Long version;
    private Long id;
    private String name;
    private byte[] content;


    public ImagesEntity() {
    }

    public ImagesEntity(byte[] content) {
        this.content = content;
        this.name = generateName();
    }


    /*
    * GETTERS
     */

    public static String generateName() {
        Date date = new Date();
        DateFormat df = new SimpleDateFormat(DATE_FORMAT);
        String dateNow = df.format(date);
        return IMG_NAME_PREFIX + dateNow;
    }

    @Version
    public Long getVersion() {
        return version;
    }

    @Id
    @GeneratedValue(generator = "InvSeqImage")
    @SequenceGenerator(name = "InvSeqImage", sequenceName = "INV_SEQImage", allocationSize = 5)
    public Long getId() {
        return id;
    }

    @Column(nullable = false)
    public String getName() {
        return name;
    }

    @Lob
    @Column(nullable = false)
    public byte[] getContent() {
        return content;
    }
}
