package gentree.client.desktop.configuration.enums;

/**
 * Created by Martyna SZYMKOWIAK on 19/03/2017.
 */
public enum AppLanguage {
    PL("pl", "PL", "poland.png"),
    EN("en", "EN", "united-kingdom.png"),
    FR("fr", "FR", "france.png");

    final String location = "/layout/images/icons/location/";
    private String language;
    private String country;
    private String badge;

    private AppLanguage(String lang, String country, String badge) {
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
