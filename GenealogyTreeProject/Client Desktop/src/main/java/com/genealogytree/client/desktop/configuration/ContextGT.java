package com.genealogytree.client.desktop.configuration;

import com.genealogytree.client.desktop.configuration.messages.AppTitles;
import com.genealogytree.client.desktop.configuration.messages.LogMessages;
import com.genealogytree.client.desktop.service.FamilyService;
import com.sun.org.apache.regexp.internal.RE;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by Martyna SZYMKOWIAK on 19/03/2017.
 */
@Getter
@Setter
@Log4j2
public class ContextGT {

    public static final ContextGT INSTANCE = new ContextGT();

    private Locale locale;
    private ObjectProperty<ResourceBundle> bundle = new SimpleObjectProperty<>();
    FamilyService service;

    {
        locale = new Locale("en", "EN");
        bundle.setValue(ResourceBundle.getBundle(AppTitles.PARAM_TRADUCTION_LOCATION, locale));
    }
    private ContextGT() {

    }

    public static ContextGT getInstance() {
        return INSTANCE;
    }

    public void setBundle(Locale locale) {
        this.bundle.setValue(ResourceBundle.getBundle(AppTitles.PARAM_TRADUCTION_LOCATION, locale));
    }

    public ResourceBundle getBundleValue() {
        return bundle.getValue();
    }
}
