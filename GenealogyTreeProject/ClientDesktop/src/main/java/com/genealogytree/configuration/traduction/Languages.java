package com.genealogytree.configuration.traduction;

public enum Languages {
    PL("pl", "poland.png"),
    EN("en", "united-kingdom.png"),
    FR("fr", "france.png");

    final String location = "com/genealogytree/sources/icons/location/";
    private String language;
    private String badge;

    private Languages(String lang, String badge) {
        this.language = lang;
        this.badge = badge;
    }

    @Override
    public String toString() {
        return this.language;
    }

    public String getBadge() {
        return this.location.concat(this.badge);
    }
}
