package gentree.client.desktop.controllers;

import gentree.client.desktop.domain.Member;

import java.util.List;

/**
 * Created by Martyna SZYMKOWIAK on 23/07/2017.
 */
public interface FXMLDialogReturningMember extends FXMLDialogController {

    void setMember(Member m);

    void setMemberList(List<Member> list);

    Member getResult();
}
