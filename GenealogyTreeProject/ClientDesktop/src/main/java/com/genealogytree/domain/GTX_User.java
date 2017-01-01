package com.genealogytree.domain;

import com.genealogytree.domain.beans.UserBean;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Created by vanilka on 22/11/2016.
 */
public class GTX_User extends UserBean {

    private ObservableList<GTX_Family> gtx_familiesList;

    {
        gtx_familiesList = FXCollections.observableArrayList();
    }





}
