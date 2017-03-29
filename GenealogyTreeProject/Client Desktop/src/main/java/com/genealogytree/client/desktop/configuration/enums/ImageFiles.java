package com.genealogytree.client.desktop.configuration.enums;

/**
 * Created by vanilka on 27/12/2016.
 */
public enum ImageFiles {
    NO_NAME_MALE("NoNameMale.png"),
    NO_NAME_FEMALE("NoNameFemale.png"),
    GENERIC_MALE("GenericMale.png"),
    GENERIC_FEMALE("GenericFemale.png"),
    TEST_BACKGROUND("TestBackground.png"),
    EQUAL_TRIANGLE("equalTrangle.png"),
    RELATION_NEUTRAL("neutral.png"),
    RELATION_MARRIED("maried.png"),
    RELATION_FIANCE("fiance.png"),
    RELATION_LOVE("love.png");

    private final String path = "/layout/images/backgrounds/";
    private String file;

    private ImageFiles(String file) {
        this.file = path.concat(file);
    }

    @Override
    public String toString() {
        return file;
    }
}

