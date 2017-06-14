package com.genealogytree.client.desktop.configuration.helper;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Created by Martyna SZYMKOWIAK on 2017-06-14.
 */
public class WSLongAdapter  extends XmlAdapter<String, Long> {
    @Override
    public String marshal(Long id) throws Exception {
        if(id==null) return "" ;
        return id.toString();
    }
    @Override
    public Long  unmarshal(String id) throws Exception {
        return  Long.parseLong(id);
    }
}
