package mobile.client.gentree.gentreemobile.configuration.utilities;

/**
 * Created by vanilka on 13/12/2017.
 */
public class ServerUrl {

    private static final String URL_MAIN = "http://192.168.0.241:8080/gentree/";
    public static final String URL_LOGIN = URL_MAIN.concat("owner/login");
    public static final String URL_GET_FAMILIES = URL_MAIN.concat("family");
    public static final String URL_GET_FAMILY = URL_MAIN.concat("family/%d");
}
