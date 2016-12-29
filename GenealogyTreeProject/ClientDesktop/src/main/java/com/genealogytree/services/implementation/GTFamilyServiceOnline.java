package com.genealogytree.services.implementation;

import com.genealogytree.application.GenealogyTreeContext;
import com.genealogytree.domain.GTX_Family;
import com.genealogytree.domain.GTX_Member;
import com.genealogytree.domain.beans.FamilyBean;
import com.genealogytree.domain.beans.MemberBean;
import com.genealogytree.exception.ExceptionBean;
import com.genealogytree.services.GTFamilyService;
import com.genealogytree.services.responses.*;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

public class GTFamilyServiceOnline implements GTFamilyService {

    private static final Logger LOG = LogManager.getLogger(GTFamilyServiceOnline.class);
    private GenealogyTreeContext context;
    private ObjectProperty<GTX_Family> currentFamily;

    public GTFamilyServiceOnline() {
        this(null);
    }

    public GTFamilyServiceOnline(GenealogyTreeContext context) {
        currentFamily = new SimpleObjectProperty<>();
        this.context = context;
    }

    @Override
    public ServerResponse getProjects() {
        ServerResponse result = null;
        try {
            String token = this.context.getConnectedUser().getLogin() + ":" + this.context.getConnectedUser().getPassword();
            Response response = this.context.getMainTarget()
                    .path("project")
                    .request(MediaType.APPLICATION_JSON)
                    .header("Authorization", "Basic " + java.util.Base64.getEncoder().encodeToString(token.getBytes()))
                    .get();
            if (response.getStatus() != 200) {

                result = new ExceptionResponse((response.readEntity(ExceptionBean.class)));

            } else {
                List<FamilyBean> tempList = response.readEntity(new GenericType<List<FamilyBean>>() {
                });
                List<GTX_Family> famList = convertFamilyList(tempList);
                result = new ListFamilyResponse(famList);
            }
        } catch (Exception e) {
            System.out.println("exception");
            e.printStackTrace();
            result = new ExceptionResponse(new ExceptionBean());
        }

        return result;
    }

    @Override
    public ServerResponse addNewProject(GTX_Family familyBean) {
        ServerResponse result = null;
        try {
            String token = this.context.getConnectedUser().getLogin() + ":" + this.context.getConnectedUser().getPassword();
            Response response = this.context.getMainTarget()
                    .path("project")
                    .request(MediaType.APPLICATION_JSON)
                    .header("Authorization", "Basic " + java.util.Base64.getEncoder().encodeToString(token.getBytes()))
                    .post(Entity.json(familyBean.getFamilyBean()));

            if (response.getStatus() != 200) {
                result = new ExceptionResponse((response.readEntity(ExceptionBean.class)));
            } else {
                familyBean = new GTX_Family(response.readEntity(FamilyBean.class));
                result = new FamilyResponse(familyBean);
            }

        } catch (Exception e) {
            result = new ExceptionResponse(new ExceptionBean());
        }
        return result;
    }

    @Override
    public ServerResponse updateFamily(GTX_Family family) {
        return null;
    }

    @Override
    public ServerResponse updateFamilyName(String newName) {
        ServerResponse result = null;
        FamilyBean familyBean = this.currentFamily.getValue().getFamilyBean();
        familyBean.setName(newName);

        try {
            String token = this.context.getConnectedUser().getLogin() + ":" + this.context.getConnectedUser().getPassword();
            Response response = this.context.getMainTarget()
                    .path("project")
                    .path("update")
                    .request(MediaType.APPLICATION_JSON)
                    .header("Authorization", "Basic " + java.util.Base64.getEncoder().encodeToString(token.getBytes()))
                    .put(Entity.json(familyBean));

            if (response.getStatus() != 200) {
                result = new ExceptionResponse((response.readEntity(ExceptionBean.class)));
            } else {
                familyBean = response.readEntity(FamilyBean.class);
                this.currentFamily.getValue().updateFromFamilyBean(familyBean);
                result = new FamilyResponse(this.currentFamily.getValue());
            }

        } catch (Exception e) {
            result = new ExceptionResponse(new ExceptionBean());
        }
        return result;
    }

    @Override
    public ServerResponse addNewMember(GTX_Member member) {
        ServerResponse result = null;
        try {
            MemberBean bean = member.getMemberBean();
            bean.setOwnerF(this.currentFamily.getValue().getFamilyBean());
            System.out.println("Bean : " +Entity.json(bean));
            System.out.println("bean image :" + bean.getImage());
            String token = this.context.getConnectedUser().getLogin() + ":" + this.context.getConnectedUser().getPassword();
            Response response = this.context.getMainTarget()
                    .path("member")
                    .request(MediaType.APPLICATION_JSON)
                    .header("Authorization", "Basic " + java.util.Base64.getEncoder().encodeToString(token.getBytes()))
                    .post(Entity.json(bean));


            if (response.getStatus() != 200) {
                result = new ExceptionResponse((response.readEntity(ExceptionBean.class)));
            } else {
                member.updateFromBean(response.readEntity(MemberBean.class));
                this.currentFamily.getValue().addMember(member);
                result = new MemberResponse(member);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = new ExceptionResponse(new ExceptionBean());
        }

        return result;
    }


    public void setContext(GenealogyTreeContext context) {
        this.context = context;
    }

    @Override
    public void setCurrentFamily(GTX_Family family) {
        ServerResponse response = loadMembersList(family.getId());
        if (response instanceof ListMemberResponse) {
            family.setGtx_membersList(((ListMemberResponse) response).getListMember());
        } else {

        }
        this.currentFamily.setValue(family);
    }


    private ServerResponse loadMembersList(Long familyID) {

        ServerResponse result = null;
        try {
            String token = this.context.getConnectedUser().getLogin() + ":" + this.context.getConnectedUser().getPassword();
            Response response = this.context.getMainTarget()
                    .path("member")
                    .path("list")
                    .path(familyID.toString())
                    .request(MediaType.APPLICATION_JSON)
                    .header("Authorization", "Basic " + java.util.Base64.getEncoder().encodeToString(token.getBytes()))
                    .get();
            if (response.getStatus() != 200) {
                result = new ExceptionResponse((response.readEntity(ExceptionBean.class)));
            } else {
                List<MemberBean> tempList = response.readEntity(new GenericType<List<MemberBean>>() {
                });
                List<GTX_Member> memberList = convertMemberList(tempList);
                result = new ListMemberResponse(memberList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = new ExceptionResponse(new ExceptionBean());
        }

        return result;
    }

    @Override
    public GTX_Family getCurrentFamily() {
        return this.currentFamily.getValue();
    }

    private List<GTX_Family> convertFamilyList(List<FamilyBean> sourceList) {
        List<GTX_Family> targetList = new ArrayList<>();
        for (FamilyBean bean : sourceList) {
            targetList.add(new GTX_Family(bean));
        }
        return targetList;
    }

    private List<GTX_Member> convertMemberList(List<MemberBean> sourceList) {
        List<GTX_Member> targetList = new ArrayList<>();
        for (MemberBean bean : sourceList) {
            targetList.add(new GTX_Member(bean));
        }
        return targetList;
    }


}
