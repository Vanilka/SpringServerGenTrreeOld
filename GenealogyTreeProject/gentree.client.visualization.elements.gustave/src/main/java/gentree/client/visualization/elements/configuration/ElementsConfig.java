package gentree.client.visualization.elements.configuration;

import gentree.common.configuration.enums.Race;
import javafx.scene.paint.Color;

import java.util.HashMap;

/**
 * Created by Martyna SZYMKOWIAK on 15/11/2017.
 */
public class ElementsConfig {
    public static final ElementsConfig INSTANCE = new ElementsConfig();


    private final HashMap<Race, Color> raceConfigurator = new HashMap<>();

    private ElementsConfig() {
        initMap();
    }

    private void initMap() {
        raceConfigurator.put(Race.HUMAIN, Color.GREEN);
        raceConfigurator.put(Race.WEREWOLF, Color.BROWN);
        raceConfigurator.put(Race.VAMPIRE, Color.RED);
    }

    public Color findColor(Race race) {
        if(raceConfigurator.containsKey(race)) {
            return raceConfigurator.get(race);
        }
        return Color.GREEN;
    }

}
