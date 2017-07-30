package gentree.client.desktop.configurations;

import gentree.client.desktop.configurations.messages.LogMessages;
import lombok.extern.log4j.Log4j2;

import java.util.*;

/**
 * Created by Martyna SZYMKOWIAK on 15/07/2017.
 */
@Log4j2
public class GenTreeDefaultProperties {

    public static final GenTreeDefaultProperties INSTANCE = new GenTreeDefaultProperties();

    public static final String PARAM_DIR_IMAGE_NAME = "dir.image";
    public static final String PARAM_DIR_PROJECT_NAME = "dir.projects";
    public static final String PARAM_DIR_LOG = "dir.logs";
    public static final String PARAM_DEFAULT_ALLOW_HOMO= "default.allow.homo";

    private static final String DEFAULT_DIR_IMAGE_NAME = "photo";
    private static final String DEFAULT_DIR_PROJECT_NAME = "projects";
    private static final String DEFAULT_DIR_LOG = "logs";
    private static final String DEFAULT_ALLOW_HOMO_VALUE="false";


    private Map<String, String> defaults = new HashMap<>();


    private GenTreeDefaultProperties() {
        populateDefaultMap();
    }

    public static List<String> getFolderList() {
        return Arrays.asList(PARAM_DIR_IMAGE_NAME, PARAM_DIR_LOG, PARAM_DIR_PROJECT_NAME);
    }

    public Properties getMissingProperties(final Properties properties) {
        for (Map.Entry<String, String> param : defaults.entrySet()) {
            if (null == properties.getProperty(param.getKey())) {
                log.warn(LogMessages.MSG_MISSING_PROPERTY, param.getKey());
                properties.putIfAbsent(param.getKey(), param.getValue());
            }
        }
        return properties;
    }

    private void populateDefaultMap() {
        defaults.put(PARAM_DIR_IMAGE_NAME, DEFAULT_DIR_IMAGE_NAME);
        defaults.put(PARAM_DIR_PROJECT_NAME, DEFAULT_DIR_PROJECT_NAME);
        defaults.put(PARAM_DIR_LOG, DEFAULT_DIR_LOG);
        defaults.put(PARAM_DEFAULT_ALLOW_HOMO, DEFAULT_ALLOW_HOMO_VALUE);
    }

}
