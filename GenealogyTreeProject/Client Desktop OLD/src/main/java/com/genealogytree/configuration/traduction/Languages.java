package genealogytree.configuration.traduction;

public enum Languages {
    PL("pl", "PL", "poland.png"),
    EN("en", "EN", "united-kingdom.png"),
    FR("fr", "FR", "france.png");

    final String location = "images/icons/location/";
    private String language;
    private String country;
    private String badge;

    private Languages(String lang, String country, String badge) {
        this.language = lang;
        this.country = country;
        this.badge = badge;
    }

    @Override
    public String toString() {
        return this.language;
    }

    public String getBadge() {
        return this.location.concat(this.badge);
    }

    public String getCountry() {
        return this.country;
    }
}
