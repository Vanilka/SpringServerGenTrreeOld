package gentree.client.desktop.service;

import gentree.client.desktop.configuration.common.messages.AppTitles;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
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
public class GenTreeContext implements FamilyContext{

    public static final GenTreeContext INSTANCE = new GenTreeContext();


    private Locale locale;
    private ObjectProperty<ResourceBundle> bundle = new SimpleObjectProperty<>();
    private ObjectProperty<FamilyService> service = new SimpleObjectProperty<>();


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

    public ReadOnlyObjectProperty<FamilyService> serviceProperty() {
        return service;
    }

    public FamilyService getService() {
        return service.get();
    }
    /*
        SETTERS
     */

    public void setService(FamilyService service) {
        this.service.set(service);
    }

}
