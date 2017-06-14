/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genealogytree.application;

import genealogytree.configuration.Authenticator;
import genealogytree.domain.beans.UserBean;
import genealogytree.services.GTFamilyService;
import genealogytree.services.implementation.GTFamilyServiceOffline;
import genealogytree.services.implementation.GTUserServiceOnline;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * @author vanilka
 */
public class GenealogyTreeContext {

    public static String IMAGES_DIR = "SimPhotos";
    private static String TRADUCTION_LOCATION = "traduction.gt_location";
    private ObjectProperty<UserBean> connectedUser = new SimpleObjectProperty<>();
    private Client client;
    private WebTarget mainTarget;
    private String BASE_URL;

    private String fileLocation = "gt.properties";
    private String ServerName;
    private String AppName;
    private String protocol;
    private int port;

    private GTUserServiceOnline userService;
    private GTFamilyService familyService;

    private Locale locale;
    private ObjectProperty<ResourceBundle> bundle = new SimpleObjectProperty<>();

    {
        locale = new Locale("en", "EN");
        bundle.setValue(ResourceBundle.getBundle(TRADUCTION_LOCATION, locale));

        this.connectedUser.setValue(null);
        createDirectories();
        setDefaultProperties();
    }

    public GenealogyTreeContext() {
        readPropertyFile();
        this.BASE_URL = getURL();
        this.client = ClientBuilder.newClient();
        this.mainTarget = client.target(BASE_URL);
    }

    public GenealogyTreeContext(ResourceBundle bundle) {
        this();
        this.bundle.setValue(bundle);
    }

    public void setNewClientLogin(String login, String password) {
        this.client = ClientBuilder.newClient().register(new Authenticator(login, password));
        this.mainTarget = client.target(BASE_URL);
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

    public void reloadContext() {
        this.familyService.setCurrentFamily(null);
        this.connectedUser.setValue(null);
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

    public void setMainTarget(WebTarget mainTarget) {
        this.mainTarget = mainTarget;
    }

    public WebTarget getMainTarger(String user, String password) {

        return getMainTarget().register(new Authenticator(user, password));
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

    public void setBundle(ResourceBundle bundle) {
        this.bundle.setValue(bundle);
    }

    public void setBundle(Locale locale) {
        this.bundle.setValue(ResourceBundle.getBundle(TRADUCTION_LOCATION, locale));
    }

    public ObjectProperty<ResourceBundle> getBundleProperty() {
        return this.bundle;
    }

    public GTUserServiceOnline getUserService() {
        if (this.userService == null) {
            this.userService = new GTUserServiceOnline(this);
        }
        return userService;
    }

    public void setUserService(GTUserServiceOnline userService) {
        this.userService = userService;
    }

    public GTFamilyService getFamilyService() {
        if (this.familyService == null) {
            this.familyService = new GTFamilyServiceOffline(this);
        }
        return familyService;
    }

    public void setFamilyService(GTFamilyService familyService) {
        this.familyService = familyService;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public void setLocale(String language) {
        this.locale = new Locale(language);
    }

    private void createDirectories() {
        Path path = Paths.get(IMAGES_DIR);
        try {
            if (!Files.exists(path)) {
                Files.createDirectory(path);
            }
        } catch (Exception e) {

        }
    }
}
