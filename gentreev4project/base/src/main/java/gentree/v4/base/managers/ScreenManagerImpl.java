package gentree.v4.base.managers;

import gentree.v4.common.interfaces.ScreenManager;
import javafx.stage.Stage;

/**
 * Created by vanilka on 10/04/2018.
 */
public class ScreenManagerImpl implements ScreenManager {

    public static final ScreenManagerImpl INSTANCE = new ScreenManagerImpl();

    private ScreenManagerImpl() {}


    @Override
    public void init(Stage stage) {

    }
}
