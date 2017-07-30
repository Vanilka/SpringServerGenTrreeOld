package gentree.client.desktop.configurations;

import gentree.client.desktop.configurations.messages.LogMessages;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;

/**
 * Created by Martyna SZYMKOWIAK on 15/07/2017.
 */
@Log4j2
public class GenTreeProperties {

    public final static GenTreeProperties INSTANCE = new GenTreeProperties();
    private final  GenTreeDefaultProperties defaultProperties = GenTreeDefaultProperties.INSTANCE;
    private static final String CONFIG_FILE = "config.properties";

    @Getter
    private final Properties appProperties = new Properties();

    private GenTreeProperties() {
        readConfigFile();
        checkApplicationFolder();
    }

    private void readConfigFile() {
        Path path = Paths.get(CONFIG_FILE);
        if (!Files.exists(path)) {
            createNewConfigFile();
        } else {
            log.info(LogMessages.MSG_READ_CONFIG_FILE);
            defaultProperties.getMissingProperties(appProperties);
        }
    }

    private void createNewConfigFile() {
        try {
          Path config = Files.createFile(Paths.get(CONFIG_FILE));
            OutputStream output = new FileOutputStream(config.toString());
            defaultProperties.getMissingProperties(appProperties);
            appProperties.store(output, null);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }


    private void checkApplicationFolder() {
        List<String> dirParams = GenTreeDefaultProperties.getFolderList();
        try {
            for (String dir : dirParams) {
                String dirPath = appProperties.getProperty(dir);
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
