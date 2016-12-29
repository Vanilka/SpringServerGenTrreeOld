package com.genealogytree.repository.entity.modules.tree;

import com.genealogytree.domain.beans.ImageBean;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by vanilka on 27/12/2016.
 */
@Entity
@Table(name = "Images")
public class GT_Images extends ImageBean {

    public GT_Images() {
        super();
    }

    public GT_Images(byte[] content) {
        super(content);
        this.name = generateName();
    }


    /*
    * GETTERS
     */

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


    /*
    * SETTERS
     */

    public void setVersion(Long version) {
        this.version = version;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public static String generateName() {
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateNow = df.format(date);
        return "GT"+dateNow;
    }
}
