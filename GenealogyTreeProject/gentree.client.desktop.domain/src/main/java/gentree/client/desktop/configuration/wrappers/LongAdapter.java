package gentree.client.desktop.configuration.wrappers;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Created by Martyna SZYMKOWIAK on 16/07/2017.
 */
public class LongAdapter extends XmlAdapter<String, Long> {
    @Override
    public String marshal(Long id) throws Exception {
        if (id == null) return "";
        return id.toString();
    }

    @Override
    public Long unmarshal(String id) throws Exception {
        return Long.parseLong(id);
    }
}