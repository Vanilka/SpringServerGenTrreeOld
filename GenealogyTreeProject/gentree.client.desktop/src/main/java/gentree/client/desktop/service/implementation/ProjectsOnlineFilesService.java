package gentree.client.desktop.service.implementation;

import gentree.client.desktop.configuration.GenTreeProperties;
import gentree.client.desktop.configuration.enums.PropertiesKeys;
import gentree.client.visualization.elements.configuration.ImageFiles;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.configuration2.Configuration;
import org.springframework.security.access.method.P;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

/**
 * Service to gestion Files from Online
 * Created by vanilka on 05/12/2017.
 */
@Log4j2
public class ProjectsOnlineFilesService {

    public final static ProjectsOnlineFilesService INSTANCE = new ProjectsOnlineFilesService();
    private final static String PROJECT_FILE_EXTENSION = ".xml";
    private static final String PREFIX_FILE_ABSOLUTE = "file://";
    private static final String PREFIX_FILE_RELATIVE = "file:";
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
        String pathFile =  config.getProperty(PropertiesKeys.PARAM_DIR_ONLINE_TEMP).toString().concat("/").concat(fileName);
        decoder(base64Image, pathFile);
        return  PREFIX_FILE_RELATIVE.concat(pathFile);
    }

    /**
     * Encode Picture to Base64
     * @param imagePath
     * @return
     */

    public String encodePicture(String imagePath) {
        String base64Image = "";
        File file = new File(imagePath.replace(PREFIX_FILE_ABSOLUTE,"").replace(PREFIX_FILE_RELATIVE,""));
        try (FileInputStream imageInFile = new FileInputStream(file)) {
            // Reading a Image file from file system
            byte imageData[] = new byte[(int) file.length()];
            imageInFile.read(imageData);
            base64Image = Base64.getEncoder().encodeToString(imageData);
        } catch (FileNotFoundException e) {
            System.out.println("Image not found" + e);
        } catch (IOException ioe) {
            System.out.println("Exception while reading the Image " + ioe);
        }
        return base64Image;
    }



    private static void decoder(String base64Image, String pathFile) {
        try (FileOutputStream imageOutFile = new FileOutputStream(pathFile)) {
            // Converting a Base64 String into Image byte array
            byte[] imageByteArray = Base64.getDecoder().decode(base64Image);
            imageOutFile.write(imageByteArray);
        } catch (FileNotFoundException e) {
            System.out.println("Image not found" + e);
        } catch (IOException ioe) {
            System.out.println("Exception while reading the Image " + ioe);
        }
    }

}
