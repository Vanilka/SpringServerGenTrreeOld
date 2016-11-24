package com.genealogytree.services.implementation;

import com.genealogytree.application.GenealogyTreeContext;
import com.genealogytree.domain.GTX_Family;
import com.genealogytree.exception.ExceptionBean;
import com.genealogytree.services.GTFamilyService;
import com.genealogytree.services.responses.ExceptionResponse;
import com.genealogytree.services.responses.FamilyResponse;
import com.genealogytree.services.responses.ListFamilyResponse;
import com.genealogytree.services.responses.ServerResponse;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

public class GTFamillyServiceOnline implements GTFamilyService{

    private GenealogyTreeContext context;

    private ObjectProperty<GTX_Family> currentFamily;


    public GTFamillyServiceOnline() {
        this(null);
    }

    public GTFamillyServiceOnline(GenealogyTreeContext context) {
        currentFamily = new SimpleObjectProperty<>();
        this.context = context;
    }

    @Override
    public ServerResponse getProjects() {
        ServerResponse result = null;
        try {
            String token = this.context.getConnectedUser().getLogin() + ":" + this.context.getConnectedUser().getPassword();
            ObservableList<GTX_Family> List = FXCollections.observableArrayList();
            Response response = this.context.getMainTarget()
                    .path("project")
                    .request(MediaType.APPLICATION_JSON)
                    .header("Authorization", "Basic " + java.util.Base64.getEncoder().encodeToString(token.getBytes()))
                    .get();
            if (response.getStatus() != 200) {
                result = new ExceptionResponse((response.readEntity(ExceptionBean.class)));
            } else {
                List<GTX_Family> tempList = response.readEntity(new GenericType<List<GTX_Family>>() {
                });
                System.out.println("templist : "+ tempList.toString());
                result = new ListFamilyResponse(tempList);

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
                        .post(Entity.json(familyBean));

                if (response.getStatus() != 200) {
                    result = new ExceptionResponse((response.readEntity(ExceptionBean.class)));
                } else {
                    familyBean = response.readEntity(GTX_Family.class);
                    result = new FamilyResponse(familyBean);
                }

            } catch (Exception e) {
                System.out.println("exception");
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
        this.currentFamily.setValue(family);
    }

    @Override
    public GTX_Family getCurrentFamily() {
        return this.currentFamily.getValue();
    }
}
