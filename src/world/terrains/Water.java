package world.terrains;

import javafx.scene.paint.Color;
import units.actors.Actor;


/**
 * Created by Tim on 04/10/16.
 */
public class Water extends AbstractTerrain {

    public static final Color FOREST_COLOR = Color.BLUE;

    public Water(){
        super(FOREST_COLOR);
    }

    @Override
    public boolean actorCanTraverse(Actor actor) {
        return true;
    }
}
