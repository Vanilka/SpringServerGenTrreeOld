package gentree.server.configuration.properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * Created by Martyna SZYMKOWIAK on 07/11/2017.
 */
@Configuration
@PropertySource(value = "classpath:gentree.properties")
public class LoadConfiguration {

    @Autowired
    Environment env;

    private String value_filesystem_main;
    private String value_directory_photo;

    private static boolean checkDirectory(Path path) {
        return Files.exists(path) && Files.isDirectory(path);
    }

    @PostConstruct
    public void loadConfig() throws IOException {
        value_filesystem_main = env.getProperty(GenTreeProperties.PROP_FILESYSTEM_MAIN);
        value_directory_photo = env.getProperty(GenTreeProperties.PROP_DIRECTORY_IMG);
        checkFileSystem();
    }

    /**
     * Check File system storing images on server
     *
     * @throws IOException
     */
    private void checkFileSystem() throws IOException {

        Path main = Paths.get(value_filesystem_main);
        if (!checkDirectory(main)) {

        } else {
            checkOrCreate(main.resolve(value_directory_photo));
        }
    }

    Properties gentreeProperties() {
        Properties properties = new Properties();
        properties.setProperty(GenTreeProperties.PROP_FILESYSTEM_MAIN, value_filesystem_main);
        properties.setProperty(GenTreeProperties.PROP_DIRECTORY_IMG, value_directory_photo);
        return properties;
    }

    private void checkOrCreate(Path p) throws IOException {
        System.out.println("check or create " + p.toAbsolutePath());
        if (!checkDirectory(p)) {
            System.out.println("directory " + p.toAbsolutePath() + " not exist and will be created");
            Files.createDirectory(p);
        } else {
            System.out.println("check directory ok");
        }
    }
}
