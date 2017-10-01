package gentree.client.visualization.configuration;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by vanilka on 27/12/2016.
 */
public enum ImageFiles {
    NO_NAME_MALE("NoNameMale.png"),
    NO_NAME_FEMALE("NoNameFemale.png"),
    GENERIC_MALE("GenericMale.png"),
    GENERIC_FEMALE("GenericFemale.png"),
    EQUAL_TRIANGLE("equalTrangle.png"),
    RELATION_NEUTRAL("neutral.png"),
    RELATION_MARRIED("maried.png"),
    RELATION_FIANCE("fiance.png"),
    NEW_ADDITION("new.png"),
    RELATION_LOVE("love.png");

    private final String path = "layout/images/";
    private String file;

    private ImageFiles(String file) {
        this.file = path.concat(file);
    }

    @Override
    public String toString() {
        return file;
    }

    public File toFile() {
        System.out.println("File exist : ?" + Files.exists(Paths.get(file)));
        return new File(file);
    }
}

