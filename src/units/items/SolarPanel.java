package units.items;

import gamestate.Main;
import javafx.scene.paint.Color;

/**
 * Created by Tim on 10/10/16.
 */
public class SolarPanel extends AbstractItem {

    public static final Color DEF_COLOR = Color.AQUAMARINE;
    public static final int UPDATE_VELOCITY = 1;

    public SolarPanel() {
        super(Main.SQUARE_SIZE, UPDATE_VELOCITY);
        color = DEF_COLOR;
    }
}
