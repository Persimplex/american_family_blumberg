package units.actors;

import javafx.scene.paint.Color;
import units.Location;

/**
 * Created by Tim on 04/10/16.
 */
public class Survivor extends Actor {

    public static final int SURVIVOR_BASE_HEALTH = 100;
    public static final Color SURVIVOR_COLOR = Color.RED;
    public static int updateVelocity = 5;

    public Survivor(Location startingLocation, int displaySize) {
        super(SURVIVOR_BASE_HEALTH, displaySize, updateVelocity);
        this.color = SURVIVOR_COLOR;
        setLocation(startingLocation);
    }

}
