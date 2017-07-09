package gentree.client.desktop.service;

import gentree.client.desktop.configurations.messages.AppTitles;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by Martyna SZYMKOWIAK on 01/07/2017.
 */
@Getter
@Setter
@Log4j2
public class GenTreeContext {

    public static final GenTreeContext INSTANCE = new GenTreeContext();


    private Locale locale;
    private ObjectProperty<ResourceBundle> bundle = new SimpleObjectProperty<>();
    private FamilyService service;

    {
        locale = new Locale("pl", "PL");
        bundle.setValue(ResourceBundle.getBundle(AppTitles.PARAM_TRADUCTION_LOCATION, locale));
    }

    private GenTreeContext() {

    }


    public void setBundle(Locale locale) {
        this.bundle.setValue(ResourceBundle.getBundle(AppTitles.PARAM_TRADUCTION_LOCATION, locale));
    }

    public ResourceBundle getBundleValue() {
        return bundle.getValue();
    }

    /*
        GETTERS
     */

    /*
        SETTERS
     */

}
