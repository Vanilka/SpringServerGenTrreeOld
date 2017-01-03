package com.genealogytree.domain.beans;

import java.util.Arrays;

/**
 * Created by vanilka on 27/12/2016.
 */
public class ImageBean {
    protected Long version;
    protected Long id;
    protected String name;
    protected byte[] content;

    public ImageBean() {
        this(null, null);
    }

    public ImageBean(byte[] content) {
        this.content = content;
    }

    public ImageBean(String name, byte[] content) {
        this.name = name;
        this.content = content;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "ImageBean{" +
                "version=" + version +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", content=" + Arrays.toString(content) +
                '}';
    }
}
