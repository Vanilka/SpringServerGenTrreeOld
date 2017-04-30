package com.genealogytree.client.desktop.configuration.helper;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Created by Martyna SZYMKOWIAK on 05/04/2017.
 */
public class StringPropertyMarshaller extends XmlAdapter<String, StringProperty> {

    @Override
    public StringProperty unmarshal(String v) throws Exception {
        return new SimpleStringProperty(v);
    }

    @Override
    public String marshal(StringProperty v) throws Exception {
        return v.toString();
    }
}
