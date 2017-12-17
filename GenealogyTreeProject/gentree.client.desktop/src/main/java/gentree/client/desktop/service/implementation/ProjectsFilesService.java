package gentree.client.desktop.service.implementation;

import gentree.client.visualization.elements.configuration.ImageFiles;

import java.io.*;
import java.util.Base64;

/**
 * Created by vanilka on 17/12/2017.
 */
public abstract class ProjectsFilesService {

    protected final static String PROJECT_FILE_EXTENSION = ".xml";
    protected static final String PREFIX_FILE_ABSOLUTE = "file://";
    protected static final String PREFIX_FILE_RELATIVE = "file:";

    protected static void decoder(String base64Image, String pathFile) {
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
     * Encode Picture to Base64
     *
     * @param imagePath
     * @return
     */

    public String encodePicture(String imagePath) {
        String base64Image = "";
        File file = new File(imagePath.replace(PREFIX_FILE_ABSOLUTE, "").replace(PREFIX_FILE_RELATIVE, ""));
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
}
