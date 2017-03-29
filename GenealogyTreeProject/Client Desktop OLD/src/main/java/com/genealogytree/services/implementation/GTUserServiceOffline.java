package com.genealogytree.services.implementation;

import com.genealogytree.application.GenealogyTreeContext;
import com.genealogytree.domain.beans.UserBean;
import com.genealogytree.services.GTUserService;
import com.genealogytree.services.responses.ServerResponse;

/**
 * Created by vanilka on 22/11/2016.
 */
public class GTUserServiceOffline implements GTUserService {

    private static String LOCAL_EMAIL = "local@mail.com";
    private static String LOCAL_USERNAME = "LocalUser";
    private static String LOCAL_PASSWORD = "password";
    private final UserBean loggedUser;
    private GenealogyTreeContext context;

    {
        loggedUser = new UserBean(0L, 0L, LOCAL_EMAIL, LOCAL_USERNAME, LOCAL_PASSWORD);
    }

    @Override
    public ServerResponse registerUser(String email, String login, String password) {
        //NOTHING TO DO
        return null;
    }

    @Override
    public ServerResponse connect(String login, String password) {
        //NOTHING TO DO
        return null;
    }

    @Override
    public void setContext(GenealogyTreeContext context) {
        this.context = context;
        context.setConnectedUser(loggedUser);
    }
}
