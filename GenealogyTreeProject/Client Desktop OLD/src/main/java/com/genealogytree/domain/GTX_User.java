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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GTX_User)) return false;

        GTX_User gtx_user = (GTX_User) o;

        return gtx_familiesList != null ? gtx_familiesList.equals(gtx_user.gtx_familiesList) : gtx_user.gtx_familiesList == null;

    }

    @Override
    public int hashCode() {
        return gtx_familiesList != null ? gtx_familiesList.hashCode() : 0;
    }
}
