package com.genealogytree.services;

import com.genealogytree.application.GenealogyTreeContext;
import com.genealogytree.services.responses.ServerResponse;

/**
 * Created by vanilka on 22/11/2016.
 */
public interface GTUserService {

    ServerResponse registerUser(String email, String login, String password);
    ServerResponse connect(String login, String password);
    void setContext(GenealogyTreeContext context);
}
