package gentree.client.desktop.service.implementation;

import gentree.client.desktop.configuration.GenTreeProperties;
import gentree.client.desktop.configuration.enums.PropertiesKeys;
import gentree.client.desktop.domain.Family;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.configuration2.Configuration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

/**
 * Created by vanilka on 05/12/2017.
 * <p>
 * Projects to gestion of Files in Local Service
 */
@Log4j2
public class ProjectsLocalFilesService extends ProjectsFilesService {

    private final Configuration config = GenTreeProperties.INSTANCE.getConfiguration();

    @Setter
    @Getter
    private String projectFilename;

    public String copyPhoto(String path) {
        return copyPhoto(config.getString(PropertiesKeys.PARAM_DIR_IMAGE_NAME), path);
    }

    /**
     * Copy Member photo to parentPane folder
     *
     * @param parent
     * @param path
     * @return
     */
    public String copyPhoto(String parent, String path) {
        path = path.replace(PREFIX_FILE_ABSOLUTE, "").replace(PREFIX_FILE_RELATIVE, "");
        try {
            Path result = Files.copy(Paths.get(path), Paths.get(parent, Long.toString((new Date()).getTime())));
            File file = new File(result.toString());
            path = Paths.get(file.getParent(), file.getName()).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return path;
    }

    /**
     * Move photo to parentPane folder
     *
     * @param parent
     * @param path
     * @return
     */
    public String movePhoto(String parent, String path) {
        path = path.replace(PREFIX_FILE_ABSOLUTE, "").replace(PREFIX_FILE_RELATIVE, "");
        try {
            Path source = Paths.get(path);
            Path target = Paths.get(parent, source.getFileName().toString());
            Path result = Files.move(source, target);
            File file = new File(result.toString());
            path = Paths.get(file.getParent(), file.getName()).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return path;
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
                && !s.contains(config.getString(PropertiesKeys.PARAM_DIR_PROJECT_NAME))
                && !s.contains(projectFilename.replace(PROJECT_FILE_EXTENSION, "")))
                || s.contains(PREFIX_FILE_ABSOLUTE);
        return result;
    }


    /**
     * While save file, copy / move photos to target project folder
     *
     * @param family
     */
    public void copyImagesToTargetProject(Family family) {
        family.getMembers().stream()
                .filter(m -> needCopy(m.getPhoto()))
                .forEach(member -> {
                    String newPath = movePhoto(Paths.get(
                            config.getString(PropertiesKeys.PARAM_DIR_PROJECT_NAME),
                            projectFilename.replace(PROJECT_FILE_EXTENSION, "")).toString(),
                            member.getPhoto());
                    member.setPhoto(newPath == null ? null : PREFIX_FILE_RELATIVE + newPath);

                });
    }


    public String generateNewPathForImage(String newPhotoPath) {
        return PREFIX_FILE_RELATIVE + newPhotoPath;
    }


    /**
     * Generete projectname for new Family.
     * If name already exist, increment number.
     *
     * @param name
     * @return
     */
    private String generateProjectName(String name) {
        name = name.replaceAll("[^a-zA-Z0-9]+", "");

        String baseDir = config.getString(PropertiesKeys.PARAM_DIR_PROJECT_NAME);
        String filename = name + PROJECT_FILE_EXTENSION;

        if (Files.exists(Paths.get(baseDir, filename))) {
            String template = "%setWebTarget%d.xml";
            int i = 0;
            do {
                i++;
                filename = String.format(template, name, i);
                ;
            } while (Files.exists(Paths.get(baseDir, String.format(template, name, i))));
        }
        return filename;
    }

    /**
     * Function generating Project Directories and necessaire files
     *
     * @param currentFamily
     * @throws IOException
     */
    public void generateLocalProjectFiles(Family currentFamily) throws IOException {
        String filename = generateProjectName(currentFamily.getName());
        String baseDir = config.getString(PropertiesKeys.PARAM_DIR_PROJECT_NAME);
        Files.createDirectory(Paths.get(baseDir, filename.replace(PROJECT_FILE_EXTENSION, "")));
        Files.createFile(Paths.get(baseDir, filename));
        projectFilename = filename;
    }
}
