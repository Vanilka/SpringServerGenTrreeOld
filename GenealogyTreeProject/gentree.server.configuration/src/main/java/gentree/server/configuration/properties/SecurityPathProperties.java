package gentree.server.configuration.properties;

/**
 * Created by Martyna SZYMKOWIAK on 17/10/2017.
 */
public class SecurityPathProperties {
    public static final String REALM = "GENTREE";

    public static final String HEADER_NAME_REALM = "WWW-Authenticate";
    public static final String HEADER_VALUE_REALM = "Basic realm=";

    public static final String PATH_PATTERN_LOGIN = "/login/";
    public static final String PATH_PATTERN_ROOT = "/";
    public static final String PATH_PATTERN_OWNER = "/owner/**";
    public static final String PATH_PATTERN_FAMILY = "/family/**";
    public static final String PATH_PATTERN_MEMBER = "/member/**";
    public static final String PATH_PATTERN_RELATION = "relation/**";

}
