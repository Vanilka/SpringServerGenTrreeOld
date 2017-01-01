package com.genealogytree.services.implementation;

import com.genealogytree.application.GenealogyTreeContext;
import com.genealogytree.domain.GTX_Family;
import com.genealogytree.domain.GTX_Member;
import com.genealogytree.domain.GTX_Relation;
import com.genealogytree.services.GTFamilyService;
import com.genealogytree.services.responses.ServerResponse;
import javafx.beans.property.ObjectProperty;

/**
 * Created by vanilka on 22/11/2016.
 */
public class GTFamilyServiceOffline implements GTFamilyService {

    private GenealogyTreeContext context;

    private GTX_Family currentFamily;

    public GTFamilyServiceOffline() {
        this(null);
    }

    public GTFamilyServiceOffline(GenealogyTreeContext context) {
        this.context = context;
    }

    @Override
    public ServerResponse getProjects() {
        ServerResponse result = null;
        try {

        } catch (Exception e) {
        }

        return result;
    }

    @Override
    public ServerResponse addNewProject(GTX_Family familyBean) {
        ServerResponse result = null;
        try {

        } catch (Exception e) {
        }
        return result;
    }

    @Override
    public ServerResponse updateFamily(GTX_Family family) {
        return null;
    }

    @Override
    public ServerResponse updateFamilyName(String newName) {
        return null;
    }

    @Override
    public ServerResponse addNewMember(GTX_Member member) {
        return null;
    }

    @Override
    public ServerResponse addNewRelation(GTX_Relation relation) {
        return null;
    }

    @Override
    public void setCurrentFamily(GTX_Family family) {
        this.currentFamily = family;
    }

    @Override
    public GTX_Family getCurrentFamily() {
        return this.currentFamily;
    }


    public void setContext(GenealogyTreeContext context) {
        this.context = context;
    }
}
