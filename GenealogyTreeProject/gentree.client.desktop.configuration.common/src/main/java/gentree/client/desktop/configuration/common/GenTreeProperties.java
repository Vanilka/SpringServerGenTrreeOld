package gentree.client.desktop.configuration.common;

import gentree.client.desktop.configuration.common.messages.LogMessages;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by Martyna SZYMKOWIAK on 15/07/2017.
 */
@Log4j2
public class GenTreeProperties {

    public static final GenTreeProperties INSTANCE = new GenTreeProperties();
    private static final String CONFIG_FILE = "config.config";
    private final GenTreeDefaultProperties defaultProperties = GenTreeDefaultProperties.INSTANCE;
    private final Configurations configs = new Configurations();
    private final Parameters params = new Parameters();
    private FileBasedConfigurationBuilder<FileBasedConfiguration> builder;

    @Getter
    private Configuration configuration;

    private GenTreeProperties() {
        initConfigurationBuilder();
        readConfigFile();
        checkApplicationFolder();
    }

    private void readConfigFile() {
        if (!Files.exists(Paths.get(CONFIG_FILE))) {
            createNewConfigFile();
        } else {
            log.info(LogMessages.MSG_READ_CONFIG_FILE);
            try {
                configuration = configs.properties(new File(CONFIG_FILE));

            } catch (ConfigurationException ex) {
                log.error(ex.getMessage());
            }

            defaultProperties.getMissingProperties(configuration);
        }
    }

    public void storeProperties() {
        Path path = Paths.get(CONFIG_FILE);
        try {
            configuration = builder.getConfiguration();
            builder.save();
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }

    private void createNewConfigFile() {
        try {

            Files.createFile(Paths.get(CONFIG_FILE));
            configuration = builder.getConfiguration();
            defaultProperties.getMissingProperties(configuration);
            builder.save();

        } catch (ConfigurationException | IOException e) {
            log.error(e.getMessage());
        }
    }

    private void initConfigurationBuilder() {
        builder = new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                .configure(params.properties()
                        .setFileName(CONFIG_FILE));
    }

    private void checkApplicationFolder() {
        List<String> dirParams = GenTreeDefaultProperties.getFolderList();
        try {
            for (String dir : dirParams) {
                String dirPath = configuration.getString(dir);
                File file = new File(dirPath);
                if (!file.exists() || !file.isDirectory()) {
                    log.warn(LogMessages.MSG_DIR_NOT_EXIST, dirPath);
                    Files.createDirectory(Paths.get(dirPath));
                }
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
