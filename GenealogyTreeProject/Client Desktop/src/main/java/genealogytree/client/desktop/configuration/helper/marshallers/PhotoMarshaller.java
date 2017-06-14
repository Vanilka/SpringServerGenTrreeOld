package genealogytree.client.desktop.configuration.helper.marshallers;

import genealogytree.client.desktop.configuration.enums.ImageFiles;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Created by Martyna SZYMKOWIAK on 04/04/2017.
 */
public class PhotoMarshaller extends XmlAdapter<String, String> {

    @Override
    public String unmarshal(String v) throws Exception {
        if(v == null || v.equals("")) {
            return null;
        }
        return v.replace("file:///", "");
    }

    @Override
    public String marshal(String v) throws Exception {
        if (v.equals(ImageFiles.GENERIC_MALE.toString()) || v.equals(ImageFiles.GENERIC_FEMALE.toString())) {
            return null;
        } else {
            return v;
        }
    }
}
