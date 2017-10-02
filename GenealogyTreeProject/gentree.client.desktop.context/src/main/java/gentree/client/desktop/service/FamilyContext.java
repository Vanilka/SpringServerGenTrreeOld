package gentree.client.desktop.service;

import javafx.beans.property.ReadOnlyObjectProperty;

import java.util.Locale;
import java.util.ResourceBundle;

public interface FamilyContext {
    public void setBundle(Locale locale);

    public ResourceBundle getBundleValue();

    /*
        GETTERS
     */

    public ReadOnlyObjectProperty<FamilyService> serviceProperty();

    public FamilyService getService();
    /*
        SETTERS
     */

    public void setService(FamilyService service);

}
