package gentree.client.desktop.configuration.wrappers;


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
        if (v.equals("") || v.equals("")) {
            return null;
        } else {
            return v;
        }
    }
}
