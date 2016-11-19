/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.genealogytree.application;

import com.genealogytree.configuration.Authenticator;
import com.genealogytree.domain.beans.UserBean;
import com.genealogytree.services.GTFamillyService;
import com.genealogytree.services.GTUserService;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * @author vanilka
 */
public class GenealogyTreeContext {

    private static String TRADUCTION_LOCATION = "com.genealogytree.configuration.traduction.gt_location";
    private ObjectProperty<UserBean> connectedUser = new SimpleObjectProperty<>();
    private Client client;
    private WebTarget mainTarget;
    private String BASE_URL;

    private String fileLocation = "gt.properties";
    private String ServerName;
    private String AppName;
    private String protocol;
    private int port;

    private GTUserService userService;
    private GTFamillyService familyService;

    private Locale locale;
    private ObjectProperty<ResourceBundle> bundle = new SimpleObjectProperty<>();

    {
        locale = new Locale("en");
        bundle.setValue(ResourceBundle.getBundle(TRADUCTION_LOCATION, locale));

        this.connectedUser.setValue(null);
        setDefaultProperties();
    }

    public GenealogyTreeContext() {
        readPropertyFile();
        this.BASE_URL = getURL();
        this.client = ClientBuilder.newClient();
        this.mainTarget = client.target(BASE_URL);
    }

    public void setNewClientLogin(String login, String password) {
        this.client = ClientBuilder.newClient().register(new Authenticator(login, password));
        this.mainTarget = client.target(BASE_URL);
    }

    public GenealogyTreeContext(ResourceBundle bundle) {
        this();
        this.bundle.setValue(bundle);
    }

    private void setDefaultProperties() {
        this.protocol = "http";
        this.ServerName = "localhost";
        this.port = -1;
        this.AppName = "AppName";
    }

    public void readPropertyFile() {
        try {
            Properties prop = new Properties();
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(this.fileLocation);
            if (inputStream != null) {
                prop.load(inputStream);
                this.ServerName = prop.getProperty("ServerAdress");
                this.port = Integer.parseInt(prop.getProperty("Port").trim());
                System.out.println("Port: " + port);
                this.AppName = prop.getProperty("ApplicationName");
            } else {
                System.out.println("cannot load properties file.");
            }
        } catch (Exception e) {

        }

    }

    public synchronized Client getClient() {
        return this.client;
    }

    public String getURL() {
        StringBuilder URL = new StringBuilder();
        URL.append(this.protocol);
        URL.append("://");
        URL.append(this.ServerName);

        if ((port > 0)) {
            URL.append(":");
            URL.append(this.port);
        }

        if (!(AppName == null) && !AppName.equals("")) {
            URL.append("/");
            URL.append(AppName);
        }
        System.out.println(URL.toString());
        return URL.toString();

    }

	/*
     * GETTERS AND SETTERS
	 */

    public String getServerName() {
        return ServerName;
    }

    public void setServerName(String serverName) {
        ServerName = serverName;
    }

    public String getAppName() {
        return AppName;
    }

    public void setAppName(String appName) {
        AppName = appName;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public WebTarget getMainTarget() {
        return mainTarget;
    }
    public WebTarget getMainTarger(String user, String password) {

        return getMainTarget().register(new Authenticator(user, password));
    }

    public void setMainTarget(WebTarget mainTarget) {
        this.mainTarget = mainTarget;
    }

    public String getBASE_URL() {
        return BASE_URL;
    }

    public void setBASE_URL(String url) {
        BASE_URL = url;
    }

    public UserBean getConnectedUser() {
        return connectedUser.getValue();
    }

    public void setConnectedUser(UserBean connectedUser) {
        this.connectedUser.setValue(connectedUser);
    }

    public ResourceBundle getBundle() {
        return bundle.getValue();
    }

    public void setBundle(Locale locale) {
        this.bundle.setValue(ResourceBundle.getBundle(TRADUCTION_LOCATION, locale));
    }

    public ObjectProperty<ResourceBundle> getBundleProperty() {
        return this.bundle;
    }

    public void setBundle(ResourceBundle bundle) {
        this.bundle.setValue(bundle);
    }

    public GTUserService getUserService() {
        if (this.userService == null) {
            this.userService = new GTUserService(this);
        }
        return userService;
    }

    public void setUserService(GTUserService userService) {
        this.userService = userService;
    }

    public GTFamillyService getFamilyService() {
        if (this.familyService == null) {
            this.familyService = new GTFamillyService(this);
        }
        return familyService;
    }

    public void setFamilyService(GTFamillyService familyService) {
        this.familyService = familyService;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(String language) {
        this.locale = new Locale(language);
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }
}
