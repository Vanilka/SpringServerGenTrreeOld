package gentree.client.desktop.configuration;

import gentree.client.desktop.configuration.enums.PropertiesKeys;
import gentree.client.desktop.configuration.messages.LogMessages;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.configuration2.Configuration;

import java.util.*;

/**
 * Created by Martyna SZYMKOWIAK on 15/07/2017.
 */
@Log4j2
public class GenTreeDefaultProperties {

    public static final GenTreeDefaultProperties INSTANCE = new GenTreeDefaultProperties();

    private static final String DEFAULT_DIR_IMAGE_NAME = "photo";
    private static final String DEFAULT_DIR_PROJECT_NAME = "projects";
    private static final String DEFAULT_DIR_ONLINE_TEMP = "onlinetemp";
    private static final String DEFAULT_DIR_LOG = "logs";
    private static final String DEFAULT_ALLOW_HOMO_VALUE = "false";


    private Map<String, String> defaults = new HashMap<>();


    private GenTreeDefaultProperties() {
        populateDefaultMap();
    }

    public static List<String> getFolderList() {
        return Arrays.asList(
                PropertiesKeys.PARAM_DIR_IMAGE_NAME,
                PropertiesKeys.PARAM_DIR_LOG,
                PropertiesKeys.PARAM_DIR_ONLINE_TEMP,
                PropertiesKeys.PARAM_DIR_PROJECT_NAME);
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

    public void getMissingProperties(Configuration configuration) {
        for (Map.Entry<String, String> param : defaults.entrySet()) {
            if (null == configuration.getProperty(param.getKey())) {
                log.warn(LogMessages.MSG_MISSING_PROPERTY, param.getKey());
                configuration.setProperty(param.getKey(), param.getValue());
            }
        }
    }

    private void populateDefaultMap() {
        defaults.put(PropertiesKeys.PARAM_DIR_IMAGE_NAME, DEFAULT_DIR_IMAGE_NAME);
        defaults.put(PropertiesKeys.PARAM_DIR_PROJECT_NAME, DEFAULT_DIR_PROJECT_NAME);
        defaults.put(PropertiesKeys.PARAM_DIR_ONLINE_TEMP, DEFAULT_DIR_ONLINE_TEMP);
        defaults.put(PropertiesKeys.PARAM_DIR_LOG, DEFAULT_DIR_LOG);
        defaults.put(PropertiesKeys.PARAM_DEFAULT_ALLOW_HOMO, DEFAULT_ALLOW_HOMO_VALUE);
    }

}
