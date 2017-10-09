package gentree.client.visualization.controls.behavior;

import com.sun.javafx.scene.control.behavior.BehaviorBase;
import javafx.scene.control.Control;

import java.util.List;

/**
 * Created by vanilka on 08/10/2017.
 */
public class HeaderPaneBehavior extends BehaviorBase {


    /**
     * Create a new BehaviorBase for the given control. The Control must not
     * be null.
     *
     * @param control The control. Must not be null.
     * @param list    The key bindings that should be used with this behavior.
     */
    public HeaderPaneBehavior(Control control, List list) {
        super(control, list);
    }
}
