package gentree.client.desktop.service;

import gentree.client.desktop.domain.Familly;

/**
 * Created by Martyna SZYMKOWIAK on 01/07/2017.
 */
public interface FamilyService  {

    Familly getCurrentFamilly();
    void setCurrentFamilly(Familly familly);

}
