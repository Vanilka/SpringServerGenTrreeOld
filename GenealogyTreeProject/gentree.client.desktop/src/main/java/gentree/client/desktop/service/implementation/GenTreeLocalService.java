package gentree.client.desktop.service.implementation;

import gentree.client.desktop.domain.Familly;
import gentree.client.desktop.service.FamilyService;
import javafx.beans.property.ObjectProperty;

/**
 * Created by Martyna SZYMKOWIAK on 01/07/2017.
 */
public class GenTreeLocalService implements FamilyService {

    private ObjectProperty<Familly> currentFamilly;

    @Override
    public Familly getCurrentFamilly() {
        return currentFamilly.getValue();
    }

    @Override
    public void setCurrentFamilly(Familly familly) {

    }
}
