package com.genealogytree.services;

import com.genealogytree.application.GenealogyTreeContext;
import com.genealogytree.configuration.Authenticator;
import com.genealogytree.domain.beans.FamilyBean;
import com.genealogytree.exception.ExceptionBean;
import com.genealogytree.services.responses.ExceptionResponse;
import com.genealogytree.services.responses.ListFamilyResponse;
import com.genealogytree.services.responses.ServerResponse;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.DatatypeConverter;
import java.util.List;

public class GTFamillyService {

    private GenealogyTreeContext context;

    public GTFamillyService() {
        this(null);
    }

    public GTFamillyService(GenealogyTreeContext context) {
        this.context = context;
    }

    public ServerResponse getProjects() {
        ServerResponse result = null;
        try {
            String token = this.context.getConnectedUser().getLogin() + ":" + this.context.getConnectedUser().getPassword();
            ObservableList<FamilyBean> List = FXCollections.observableArrayList();
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


    public void setContext(GenealogyTreeContext context) {
        this.context = context;
    }
}
