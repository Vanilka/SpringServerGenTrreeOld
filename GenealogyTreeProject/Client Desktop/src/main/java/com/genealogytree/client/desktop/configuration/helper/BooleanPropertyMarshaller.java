package com.genealogytree.client.desktop.configuration.helper;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import org.xml.sax.helpers.XMLReaderAdapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Created by Martyna SZYMKOWIAK on 05/04/2017.
 */
public class BooleanPropertyMarshaller extends XmlAdapter<String, BooleanProperty> {

    @Override
    public BooleanProperty unmarshal(String v) throws Exception {

        return new SimpleBooleanProperty(v != null && v.equals("true"));

    }

    @Override
    public String marshal(BooleanProperty v) throws Exception {
        return ""+v.get();
    }
}
