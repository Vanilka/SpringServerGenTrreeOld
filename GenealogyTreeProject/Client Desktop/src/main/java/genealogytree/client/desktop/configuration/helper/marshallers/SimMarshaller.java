package genealogytree.client.desktop.configuration.helper.marshallers;

import genealogytree.client.desktop.domain.GTX_Member;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Created by Martyna SZYMKOWIAK on 2017-06-14.
 */
public class SimMarshaller extends XmlAdapter<GTX_MemberWrap, GTX_Member> {
    @Override
    public GTX_Member unmarshal(GTX_MemberWrap v) throws Exception {
        return  v.getMember();
    }

    @Override
    public GTX_MemberWrap marshal(GTX_Member v) throws Exception {
        return new GTX_MemberWrap(v);
    }
}
