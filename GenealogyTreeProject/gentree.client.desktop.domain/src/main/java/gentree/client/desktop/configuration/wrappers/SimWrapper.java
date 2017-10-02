package gentree.client.desktop.configuration.wrappers;

import gentree.client.desktop.domain.Member;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by Martyna SZYMKOWIAK on 16/07/2017.
 */
@XmlType
public class SimWrapper {

    @XmlIDREF
    @XmlAttribute(name = "ref")
    private Member member;

    public SimWrapper() {
    }

    ;

    public SimWrapper(Member member) {
        this.member = member;
    }

    public Member getMember() {
        return member;
    }
}
