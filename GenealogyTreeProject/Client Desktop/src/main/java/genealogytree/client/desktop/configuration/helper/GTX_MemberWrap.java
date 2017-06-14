package genealogytree.client.desktop.configuration.helper;

import genealogytree.client.desktop.domain.GTX_Member;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by Martyna SZYMKOWIAK on 2017-06-14.
 */
@XmlType
public class GTX_MemberWrap {

    @XmlIDREF
    @XmlAttribute(name="ref")
    private GTX_Member member;

    public  GTX_MemberWrap() {}

    public  GTX_MemberWrap(GTX_Member member) {
        this.member = member;
    }

    public GTX_Member getMember() {
        return member;
    }
}
