package com.genealogytree.services.implementation;

import com.genealogytree.application.GenealogyTreeContext;
import com.genealogytree.domain.GTX_Family;
import com.genealogytree.domain.GTX_Member;
import com.genealogytree.domain.GTX_Relation;
import com.genealogytree.domain.beans.FamilyBean;
import com.genealogytree.domain.beans.ImageBean;
import com.genealogytree.domain.beans.MemberBean;
import com.genealogytree.domain.beans.RelationBean;
import com.genealogytree.exception.ExceptionBean;
import com.genealogytree.services.GTFamilyService;
import com.genealogytree.services.responses.*;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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


    /*
    *   SERVICE METHODS POST
    */
    @Override
    public ServerResponse addNewProject(GTX_Family familyBean) {
        return requestAddNewProject(familyBean);
    }

    @Override
    public ServerResponse updateFamily(GTX_Family family) {
        return requestUpdateFamily(family);
    }

    @Override
    public ServerResponse updateFamilyName(String newName) {
        return requestUpdateFamilyName(newName);
    }

    @Override
    public ServerResponse addNewMember(GTX_Member member) {
        return requestAddNewMember(member);
    }

    @Override
    public ServerResponse addNewRelation(GTX_Relation relation) {
        return requestAddNewRelation(relation);
    }

    @Override
    public ServerResponse updateRelation(GTX_Relation relation) {
        return null;
    }


    /*
    *   SERBICE METHODS GET
     */

    @Override
    public ServerResponse getProjects() {
        return requestGetProjects();
    }

    /*
    *   REST REQUESTS POST
    */

    public ServerResponse requestAddNewProject(GTX_Family familyBean) {
        setInfoLog("requestAddNewProject : parameter ->" + familyBean.toString());
        setInfoLog("requestAddNewProject : getFamilyBeanFromGXT -> " + familyBean.getFamilyBean().toString());

        ServerResponse result = null;
        try {
            String token = this.context.getConnectedUser().getLogin() + ":" + this.context.getConnectedUser().getPassword();
            Response response = this.context.getMainTarget()
                    .path("project")
                    .request(MediaType.APPLICATION_JSON)
                    .header("Authorization", "Basic " + java.util.Base64.getEncoder().encodeToString(token.getBytes()))
                    .post(Entity.json(familyBean.getFamilyBean()));

            setInfoLog("requestAddNewProject : response -> " + response.toString());

            if (response.getStatus() != 200) {
                result = new ExceptionResponse((response.readEntity(ExceptionBean.class)));

            } else {
                familyBean = new GTX_Family(response.readEntity(FamilyBean.class));
                result = new FamilyResponse(familyBean);
            }

        } catch (Exception e) {
            setErrorLog("requestAddNewProject : Exception -> " + e.getMessage());
            setErrorLog("requestAddNewProject : Exception Cause -> " + e.getCause());
            setErrorLog("requestAddNewProject : Exception Trace -> " + getExceptionTrace(e));
            result = new ExceptionResponse(new ExceptionBean());
        }

        setInfoLog("requestAddNewProject: result -> " + result.toString());
        return result;
    }

    public ServerResponse requestUpdateFamily(GTX_Family family) {
        return null;
    }

    public ServerResponse requestUpdateFamilyName(String newName) {
        setInfoLog("requestUpdateFamilyName :  parameter -> " + newName);
        ServerResponse result = null;

        FamilyBean familyBean = this.currentFamily.getValue().getFamilyBean();
        setInfoLog("requestUpdateFamilyName : familyBean [old value] -> " + familyBean);
        familyBean.setName(newName);
        setInfoLog("requestUpdateFamilyName : familyBean [new value] -> " + familyBean);
        try {
            String token = this.context.getConnectedUser().getLogin() + ":" + this.context.getConnectedUser().getPassword();
            Response response = this.context.getMainTarget()
                    .path("project")
                    .path("update")
                    .request(MediaType.APPLICATION_JSON)
                    .header("Authorization", "Basic " + java.util.Base64.getEncoder().encodeToString(token.getBytes()))
                    .put(Entity.json(familyBean));

            setInfoLog("requestUpdateFamilyName : response -> " + response.toString());

            if (response.getStatus() != 200) {
                result = new ExceptionResponse((response.readEntity(ExceptionBean.class)));
            } else {
                familyBean = response.readEntity(FamilyBean.class);
                this.currentFamily.getValue().updateFromFamilyBean(familyBean);
                result = new FamilyResponse(this.currentFamily.getValue());
            }

        } catch (Exception e) {
            setErrorLog("requestUpdateFamilyName : Exception -> " + e.getMessage());
            setErrorLog("requestUpdateFamilyName : Exception Cause -> " + e.getCause());
            setErrorLog("requestUpdateFamilyName : Exception Trace -> " + getExceptionTrace(e));
            result = new ExceptionResponse(new ExceptionBean());
        }

        setInfoLog("requestUpdateFamilyName: result -> " + result.toString());
        return result;
    }

    public ServerResponse requestAddNewMember(GTX_Member member) {

        setInfoLog("requestAddNewMember : parameter -> " + member.toString());

        ServerResponse result = null;
        try {
            MemberBean bean = getMemberBeanFromGTX(member);
            bean.setOwnerF(this.currentFamily.getValue().getFamilyBean());
            setInfoLog("requestAddNewMember : MemberBean -> " + member.toString());

            String token = this.context.getConnectedUser().getLogin() + ":" + this.context.getConnectedUser().getPassword();
            Response response = this.context.getMainTarget()
                    .path("member")
                    .request(MediaType.APPLICATION_JSON)
                    .header("Authorization", "Basic " + java.util.Base64.getEncoder().encodeToString(token.getBytes()))
                    .post(Entity.json(bean));

            setInfoLog("requestAddNewMember : response -> " + response.toString());

            if (response.getStatus() != 200) {
                result = new ExceptionResponse((response.readEntity(ExceptionBean.class)));
            } else {
                addMemberWrapper wrapper = response.readEntity(addMemberWrapper.class);
                member = updateGTXFromBean(member, wrapper.getMember());
                this.currentFamily.getValue().addMember(member);
                GTX_Relation temp = getGTXFromRelationBean(wrapper.getRelation());
                getCurrentFamily().getGtx_relations().add(temp);
                result = new MemberResponse(member);
            }
        } catch (Exception e) {
            setErrorLog("requestAddNewMember : Exception -> " + e.getMessage());
            setErrorLog("requestAddNewMember : ExceptionCause -> " + e.getCause());
            setErrorLog("requestAddNewMember : Exception Trace -> " + getExceptionTrace(e));
            result = new ExceptionResponse(new ExceptionBean());
        }
        setInfoLog("requestAddNewMember: result -> " + result.toString());
        return result;
    }

    public ServerResponse requestAddNewRelation(GTX_Relation relation) {
        setInfoLog("requestAddNewRelation : parameter -> " + relation.toString());
        ServerResponse result = null;
        try {
            RelationBean bean = getRelationBeanFromGTX(relation);
            bean.setOwnerF(this.currentFamily.getValue().getFamilyBean());
            setInfoLog("requestAddNewRelation : RelationBean -> " + bean.toString());

            String token = this.context.getConnectedUser().getLogin() + ":" + this.context.getConnectedUser().getPassword();
            Response response = this.context.getMainTarget()
                    .path("relations")
                    .request(MediaType.APPLICATION_JSON)
                    .header("Authorization", "Basic " + java.util.Base64.getEncoder().encodeToString(token.getBytes()))
                    .post(Entity.json(bean));

            setInfoLog("requestAddNewRelation : response -> " + response.toString());

            if (response.getStatus() != 200) {
                result = new ExceptionResponse((response.readEntity(ExceptionBean.class)));
            } else {
                bean = response.readEntity(RelationBean.class);
                setInfoLog("requestAddNewRelation : RelationBean from response -> " + bean.toString());
                updateRelationFromBean(relation, bean);
                this.currentFamily.getValue().addRelation(relation);
                result = new RelationResponse(relation);
            }
        } catch (Exception e) {
            setErrorLog("requestAddNewRelation : Exception -> " + e.getMessage());
            setErrorLog("requestAddNewRelation : Exception Cause -> " + e.getCause());
            setErrorLog("requestAddNewRelation : Exception Trace -> " + getExceptionTrace(e));
            result = new ExceptionResponse(new ExceptionBean());
        }
        setInfoLog("requestAddNewRelation: result -> " + result.toString());
        return result;
    }


    /*
    *   REST REQUEST GET
     */

    public ServerResponse requestGetProjects() {
        ServerResponse result = null;
        try {
            String token = this.context.getConnectedUser().getLogin() + ":" + this.context.getConnectedUser().getPassword();
            Response response = this.context.getMainTarget()
                    .path("project")
                    .request(MediaType.APPLICATION_JSON)
                    .header("Authorization", "Basic " + java.util.Base64.getEncoder().encodeToString(token.getBytes()))
                    .get();

            setInfoLog("requestGetProjects : response -> " + response.toString());

            if (response.getStatus() != 200) {

                result = new ExceptionResponse((response.readEntity(ExceptionBean.class)));

            } else {
                List<FamilyBean> tempList = response.readEntity(new GenericType<List<FamilyBean>>() {
                });
                List<GTX_Family> famList = convertFamilyList(tempList);
                result = new ListFamilyResponse(famList);
            }
        } catch (Exception e) {
            setErrorLog("requestGetProjects : Exception -> " + e.getMessage());
            setErrorLog("requestGetProjects : Exception Cause -> " + e.getCause());
            setErrorLog("requestGetProjects : Exception Trace -> " + getExceptionTrace(e));
            result = new ExceptionResponse(new ExceptionBean());
        }
        setInfoLog("requestGetProjects : result -> " + result.toString());
        return result;
    }

    private ServerResponse loadMembersList(GTX_Family family) {

        ServerResponse result = null;
        try {
            String token = this.context.getConnectedUser().getLogin() + ":" + this.context.getConnectedUser().getPassword();
            Response response = this.context.getMainTarget()
                    .path("member")
                    .path("list")
                    .path(family.getId().toString())
                    .request(MediaType.APPLICATION_JSON)
                    .header("Authorization", "Basic " + java.util.Base64.getEncoder().encodeToString(token.getBytes()))
                    .get();

            setInfoLog("loadMembersList : response -> " + response.toString());

            if (response.getStatus() != 200) {
                result = new ExceptionResponse((response.readEntity(ExceptionBean.class)));
            } else {
                List<MemberBean> tempList = response.readEntity(new GenericType<List<MemberBean>>() {
                });
                List<GTX_Member> memberList = convertMemberList(tempList);
                result = new ListMemberResponse(memberList);
            }
        } catch (Exception e) {
            setErrorLog("loadMembersList : Exception -> " + e.getMessage());
            setErrorLog("loadMembersList : ExceptionCause -> " + e.getCause());
            setErrorLog("loadMembersList : Exception Trace -> " + getExceptionTrace(e));
            result = new ExceptionResponse(new ExceptionBean());
        }
        setInfoLog("loadMembersList : result -> " + result.toString());
        return result;
    }

    private ServerResponse loadRelationsList(GTX_Family family) {
        ServerResponse result = null;
        try {
            String token = this.context.getConnectedUser().getLogin() + ":" + this.context.getConnectedUser().getPassword();
            Response response = this.context.getMainTarget()
                    .path("relations")
                    .path("list")
                    .path(family.getId().toString())
                    .request(MediaType.APPLICATION_JSON)
                    .header("Authorization", "Basic " + java.util.Base64.getEncoder().encodeToString(token.getBytes()))
                    .get();

            setInfoLog("loadRelationsList : response -> " + response.toString());

            if (response.getStatus() != 200) {
                result = new ExceptionResponse((response.readEntity(ExceptionBean.class)));
            } else {
                List<RelationBean> tempList = response.readEntity(new GenericType<List<RelationBean>>() {
                });
                List<GTX_Relation> relationList = convertRelationsList(tempList, family);
                result = new ListRelationsResponse(relationList);
            }
        } catch (Exception e) {
            setErrorLog("loadRelationsList : Exception -> " + e.getMessage());
            setErrorLog("loadRelationsList : ExceptionCause -> " + e.getCause());
            setErrorLog("loadRelationsList : Exception Trace -> " + getExceptionTrace(e));
            result = new ExceptionResponse(new ExceptionBean());
        }
        setInfoLog("loadRelationsList : result -> " + result.toString());
        return result;
    }

        /*
    *   CONVERSIONS
    */

    private List<GTX_Family> convertFamilyList(List<FamilyBean> sourceList) {

        setInfoLog("convertFamilyList : parameter -> " + sourceList.toString());

        List<GTX_Family> targetList = new ArrayList<>();
        for (FamilyBean bean : sourceList) {
            targetList.add(new GTX_Family(bean));
        }
        return targetList;
    }

    private List<GTX_Member> convertMemberList(List<MemberBean> sourceList) {

        setInfoLog("convertMemberList : parameter -> " + sourceList.toString());

        List<GTX_Member> targetList = new ArrayList<>();
        for (MemberBean bean : sourceList) {
            targetList.add(getGTXFromMemberBean(bean));
        }

        setInfoLog("convertMemberList : return value -> " + targetList.toString());

        return targetList;
    }

    private List<GTX_Relation> convertRelationsList(List<RelationBean> sourceList) {

        return convertRelationsList(sourceList, this.getCurrentFamily());
    }

    private List<GTX_Relation> convertRelationsList(List<RelationBean> sourceList, GTX_Family family) {

        setInfoLog("convertRelationsList : parameter -> " + sourceList.toString());

        List<GTX_Relation> targetList = new ArrayList<>();
        for (RelationBean bean : sourceList) {
            targetList.add(getGTXFromRelationBean(bean, family));
        }

        setInfoLog("convertRelationsList : return value -> " + targetList.toString());

        return targetList;
    }

    private GTX_Member findMemberInList(GTX_Member member) {

        return findMemberInList(member, this.getCurrentFamily().getGtx_membersList());
    }

    private GTX_Member findMemberInList(GTX_Member member, ObservableList<GTX_Member> list) {

        if (member == null || list == null || list.size() == 0) {
            return null;
        }

        setInfoLog("findMemberInList : parameter -> " + member.toString());
        list = list.filtered(p -> p.equals(member));
        if (list.size() == 0) {
            System.out.println("nie ma !");
            return null;
        } else {

            return list.get(0);
        }
    }

    private MemberBean getMemberBeanFromGTX(GTX_Member source) {
        if (source != null) {
            setInfoLog("getMemberBeanFromGTX : parameter -> " + source.toString());
            MemberBean target = new MemberBean();
            target.setId(source.getId());
            target.setVersion(source.getVersion());
            target.setName(source.getName());
            target.setSurname(source.getSurname());
            target.setAge(source.getAge());
            target.setSex(source.getSex());

            if (source.getPhoto() != null) {
                if (Files.exists(Paths.get(source.getPhoto().replace("file:///", "")))) {
                    Path path = Paths.get(source.getPhoto().replace("file:///", ""));
                    try {
                        target.setImage(new ImageBean(convertFileToArray(path)));
                    } catch (Exception e) {
                        setErrorLog("getMemberBeanFromGTX : Exception  -> " + e.getMessage());
                        setErrorLog("getMemberBeanFromGTX : Exception  Cause -> " + e.getCause());
                        setErrorLog("getMemberBeanFromGTX : Exception  Trace -> " + getExceptionTrace(e));
                    }
                }
            }
            setInfoLog("getMemberBeanFromGTX : return value  -> " + target.toString());
            return target;
        } else {
            return null;
        }
    }

    private GTX_Member getGTXFromMemberBean(MemberBean source) {

        if (source != null) {
            setInfoLog("getGTXFromMemberBean : parameter -> " + source.toString());
            return updateGTXFromBean(new GTX_Member(), source);
        } else {
            return null;
        }

    }

    private GTX_Member updateGTXFromBean(GTX_Member target, MemberBean source) {
        setInfoLog("updateGTXFromBean : parameter target -> " + target.toString());
        setInfoLog("updateGTXFromBean : parameter source -> " + source.toString());

        if (source != null) {
            target.setVersion(source.getVersion());
            target.setId(source.getId());
            target.setName(source.getName());
            target.setSurname(source.getSurname());
            target.setAge(source.getAge());
            target.setSex(source.getSex());
            if (source.getImage() != null) {
                try {
                    Path file = Paths.get(GenealogyTreeContext.IMAGES_DIR + "/" + source.getImage().getName());
                    Files.write(file, source.getImage().getContent());
                    target.setPhoto(file.toAbsolutePath().toString());
                } catch (Exception e) {
                    setErrorLog("updateGTXFromBean : Exception  -> " + e.getMessage());
                    setErrorLog("updateGTXFromBean : Exception Cause -> " + e.getCause());
                    setErrorLog("updateGTXFromBean : Exception Trace -> " + getExceptionTrace(e));

                }
            }
        }
        setInfoLog("updateGTXFromBean : return value -> " + target.toString());

        return target;
    }

    private RelationBean getRelationBeanFromGTX(GTX_Relation source) {

        setInfoLog("getRelationBeanFromGTX : parameter -> " + source.toString());

        RelationBean target = new RelationBean();
        target.setId(source.getId());
        target.setVersion(source.getVersion());
        target.setRelationType(source.getType());
        target.setSimLeft(source.getSimLeft() != null ? getMemberBeanFromGTX(source.getSimLeft()) : null);
        target.setSimRight(source.getSimRight() != null ? getMemberBeanFromGTX(source.getSimRight()) : null);
        List<MemberBean> l = source.getChildrenList().stream().map(this::getMemberBeanFromGTX).collect(Collectors.toList());
        target.setchildren(l);
        target.setRelationType(source.getType());
        target.setActive(source.isIsActive());

        setInfoLog("getRelationBeanFromGTX : return value -> " + target.toString());

        return target;
    }

    private GTX_Relation getGTXFromRelationBean(RelationBean source) {
        return getGTXFromRelationBean(source, this.getCurrentFamily());
    }

    private GTX_Relation getGTXFromRelationBean(RelationBean source, GTX_Family family) {

        setInfoLog("getGTXFromRelationBean : parameter -> " + source.toString());

        return updateRelationFromBean(new GTX_Relation(), source, family);
    }

    private GTX_Relation updateRelationFromBean(GTX_Relation target, RelationBean source) {
        return updateRelationFromBean(target, source, this.getCurrentFamily());
    }

    private GTX_Relation updateRelationFromBean(GTX_Relation target, RelationBean source, GTX_Family family) {

        setInfoLog("updateRelationFromBean : parameter -> " + source.toString());

        if (source != null) {
            target.updateFromBean(source);
            target.setSimLeft(findMemberInList(getGTXFromMemberBean(source.getSimLeft()), family.getGtx_membersList()));
            target.setSimRight(findMemberInList(getGTXFromMemberBean(source.getSimRight()), family.getGtx_membersList()));

            List<GTX_Member> list = new ArrayList<>();
            for (MemberBean b : source.getchildren()) {
                GTX_Member m = findMemberInList(getGTXFromMemberBean(b), family.getGtx_membersList());
                if (m != null) {
                    list.add(m);
                }
            }

            target.setChildrenList(list);

        } else {
            target = null;
        }

        setInfoLog("updateRelationFromBean : return value -> " + target.toString());

        return target;
    }

    private byte[] convertFileToArray(Path file) throws IOException {
        setInfoLog("convertFileToArray : file value -> " + file.toString());
        byte[] byteFile;
        byteFile = Files.readAllBytes(file);
        return byteFile;
    }


    /*
    *  GETTERS
    */

    @Override
    public GTX_Family getCurrentFamily() {
        return this.currentFamily.getValue();
    }

    @Override
    public void setCurrentFamily(GTX_Family family) {

        if (family != null) {

            ServerResponse listMemberResponse = loadMembersList(family);
            if (listMemberResponse instanceof ListMemberResponse) {
                family.setGtx_membersList(((ListMemberResponse) listMemberResponse).getListMember());
            } else {
                setErrorLog("setCurrentFamily : Error :  Exception while set MemberList");
            }

            ServerResponse listRelationResponse = loadRelationsList(family);
            if (listRelationResponse instanceof ListRelationsResponse) {
                family.setGtx_relations(((ListRelationsResponse) listRelationResponse).getListRelations());
            } else {
                setErrorLog("setCurrentFamily : Error :  Exception while set RelationList");
            }
        }
        this.currentFamily.setValue(family);

    }

    @Override
    public ObjectProperty<GTX_Family> currentFamilyProperty() {
        return currentFamily;
    }

    /*
    * SETTERS
     */

    private String getExceptionTrace(Throwable t) {
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }

    public void setContext(GenealogyTreeContext context) {
        this.context = context;
    }

    private void setInfoLog(String msg) {
        msg = this.getClass().getSimpleName() + ": " + msg;
        LOG.info(msg);
        System.out.println("INFO:  " + msg);
    }

    private void setErrorLog(String msg) {
        msg = this.getClass().getSimpleName() + ": " + msg;
        LOG.error(msg);
        System.out.println("ERROR:  " + msg);
    }
}
