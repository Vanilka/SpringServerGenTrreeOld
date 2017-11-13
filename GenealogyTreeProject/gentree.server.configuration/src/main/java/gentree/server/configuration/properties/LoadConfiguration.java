package gentree.server.configuration.properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.util.Properties;

/**
 * Created by Martyna SZYMKOWIAK on 07/11/2017.
 */
@Configuration
@PropertySource(value="classpath:gentree.properties")
public class LoadConfiguration {

    @Autowired
    Environment env;

    private String value_filesystem_photo;

    @PostConstruct
    public  void loadConfig() {
        value_filesystem_photo = env.getProperty(GenTreeProperties.PROP_FILESYSTEM_PHOTO);

        checkFileSystem();
    }


    private void checkFileSystem() {
        File directory = new File(value_filesystem_photo);
        if(directory.exists() && directory.isDirectory()) {
            System.out.println("IS oK");
        } else  {
            System.out.println("IS NOT OK");
        }
    }


    Properties gentreeProperties() {
        Properties properties = new Properties();
        properties.setProperty(GenTreeProperties.PROP_FILESYSTEM_PHOTO, value_filesystem_photo);
        return properties;
    }

}
