package units.actors;

import gamestate.Main;
import javafx.scene.paint.Color;
import units.Location;

/**
 * Created by Tim on 04/10/16.
 */
public class Survivor extends Actor {

    public static final int SURVIVOR_BASE_HEALTH = 100;
    public static final Color SURVIVOR_COLOR = Color.RED;
    public static int updateVelocity = 40;

    public Survivor(Location startingLocation, int displaySize) {
        super(SURVIVOR_BASE_HEALTH, displaySize, updateVelocity);
        this.color = SURVIVOR_COLOR;

        setLocation(startingLocation);
    }

    public void moveTo(Location newLocation){
        Main.gameState.move(this, newLocation);
        this.location = newLocation;
    }

}
