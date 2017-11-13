package gentree.client.desktop.configuration;

import gentree.client.desktop.configuration.messages.LogMessages;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
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
    private static final String CONFIG_FILE = "config.properties";
    private static final String REALM_FILE = "realms.xml";
    private final GenTreeDefaultProperties defaultProperties = GenTreeDefaultProperties.INSTANCE;
    private final Configurations configs = new Configurations();
    private final Parameters params = new Parameters();
    private FileBasedConfigurationBuilder<FileBasedConfiguration> builder;

    @Getter
    @Setter
    private RealmConfig realmConfig = new RealmConfig();

    @Getter
    private Configuration configuration;

    private GenTreeProperties() {
        initConfigurationBuilder();
        readConfigFile();
        checkApplicationFolder();
        readRealmFile();
    }

    private void readConfigFile() {
        if (!Files.exists(Paths.get(CONFIG_FILE))) {
            createNewConfigFile();
            storeRealms();
        } else {
            log.info(LogMessages.MSG_READ_CONFIG_FILE);
            try {
                configuration = configs.properties(new File(CONFIG_FILE));

            } catch (ConfigurationException ex) {
                ex.printStackTrace();
                log.error(ex);
            }

            defaultProperties.getMissingProperties(configuration);
        }
    }

    private void readRealmFile() {
        if (!Files.exists(Paths.get(REALM_FILE))) {
            createNewRealmFile();
            storeRealms();
        } else {
            try {
                File realmFile = new File(REALM_FILE);
                JAXBContext jaxbContext = JAXBContext.newInstance(RealmConfig.class);
                Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                realmConfig = (RealmConfig) jaxbUnmarshaller.unmarshal(realmFile);
            } catch (Exception e) {
                e.printStackTrace();
                log.error(e);
            }
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

    public void storeRealms() {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(RealmConfig.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(realmConfig, new File(REALM_FILE));
        } catch (Exception e) {
            log.error(e.getMessage());
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

    private void createNewRealmFile() {
        try {
            Files.createFile(Paths.get(REALM_FILE));
        } catch (IOException e) {
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
