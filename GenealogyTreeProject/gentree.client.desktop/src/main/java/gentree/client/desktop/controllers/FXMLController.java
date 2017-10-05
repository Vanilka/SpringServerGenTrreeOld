package gentree.client.desktop.controllers;

import gentree.client.desktop.service.GenTreeContext;
import gentree.client.desktop.service.ScreenManager;

/**
 * Created by Martyna SZYMKOWIAK on 01/07/2017.
 */
public interface FXMLController {

    static final ScreenManager sm = ScreenManager.INSTANCE;
    static final GenTreeContext context = GenTreeContext.INSTANCE;
}

