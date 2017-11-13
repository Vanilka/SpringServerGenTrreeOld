package gentree.server.dispatchers;

import gentree.server.configuration.properties.GenTreeProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;

/**
 * Created by Martyna SZYMKOWIAK on 06/11/2017.
 */
@Configuration
public class ImageSaveConfig {


    @Autowired
    Environment environment;

    @PostConstruct
    private void checkFileSystem() {
        System.out.println("check properties");
        System.out.println(" Check  filesystem" +environment.getProperty(GenTreeProperties.PROP_FILESYSTEM_PHOTO));


    }
}
