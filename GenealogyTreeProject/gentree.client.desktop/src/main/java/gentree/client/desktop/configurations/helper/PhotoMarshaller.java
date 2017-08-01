package gentree.client.desktop.configurations.helper;

import gentree.client.desktop.configurations.enums.ImageFiles;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Created by Martyna SZYMKOWIAK on 17/07/2017.
 */
public class PhotoMarshaller extends XmlAdapter<String, String> {

    @Override
    public String unmarshal(String v) throws Exception {
        if (v == null || v.equals("")) {
            return null;
        }
        return v;
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
