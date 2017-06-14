package genealogytree.client.desktop.domain;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by vanilka on 22/11/2016.
 */
@Getter
@Setter
public class GTX_User {

    private Long version;
    private Long id;
    private String login;
    private String password;
    private String email;
    private ObservableList<GTX_Family> gtx_familiesList;

    {
        gtx_familiesList = FXCollections.observableArrayList();
    }



    @Override
    public int hashCode() {
        return gtx_familiesList != null ? gtx_familiesList.hashCode() : 0;
    }
}
