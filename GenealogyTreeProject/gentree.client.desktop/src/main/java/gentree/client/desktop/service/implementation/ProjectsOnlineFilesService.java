package gentree.client.desktop.service.implementation;

import gentree.client.desktop.configuration.GenTreeProperties;
import gentree.client.desktop.configuration.enums.PropertiesKeys;
import gentree.client.visualization.elements.configuration.ImageFiles;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.configuration2.Configuration;

/**
 * Service to gestion Files from Online
 * Created by vanilka on 05/12/2017.
 */
@Log4j2
public class ProjectsOnlineFilesService extends ProjectsFilesService {

    public final static ProjectsOnlineFilesService INSTANCE = new ProjectsOnlineFilesService();
    private final Configuration config = GenTreeProperties.INSTANCE.getConfiguration();


    private ProjectsOnlineFilesService() {
    }


    private String saveFile(String encoded) {
        String name = "";

        return name;
    }


    /**
     * Verify is photo generic
     *
     * @param s
     * @return
     */
    public boolean isGenericPhoto(String s) {
        return (s.equals(ImageFiles.GENERIC_FEMALE.toString()) || s.equals(ImageFiles.GENERIC_MALE.toString()));
    }

    /**
     * Verifiy necessity of copy photo
     *
     * @param s
     * @return
     */
    public boolean needCopy(String s) {
        boolean result = !isGenericPhoto(s)
                && (s.contains(PREFIX_FILE_RELATIVE)
                && !s.contains(config.getString(PropertiesKeys.PARAM_DIR_ONLINE_TEMP)))
                || s.contains(PREFIX_FILE_ABSOLUTE);
        return result;
    }


    public String decodePicture(String base64Image, String fileName) {
        String pathFile = config.getProperty(PropertiesKeys.PARAM_DIR_ONLINE_TEMP).toString().concat("/").concat(fileName);
        decoder(base64Image, pathFile);
        return PREFIX_FILE_RELATIVE.concat(pathFile);
    }


}
