package gentree.client.desktop.configuration.wrappers;

import gentree.client.desktop.domain.Member;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Created by Martyna SZYMKOWIAK on 16/07/2017.
 */
public class SimMarshaller extends XmlAdapter<SimWrapper, Member> {

    @Override
    public Member unmarshal(SimWrapper v) throws Exception {
        return v.getMember();
    }

    @Override
    public SimWrapper marshal(Member v) throws Exception {
        return new SimWrapper(v);
    }
}
