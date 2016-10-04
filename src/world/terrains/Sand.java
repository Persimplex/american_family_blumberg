package world.terrains;

import javafx.scene.paint.Color;
import units.actors.Actor;


/**
 * Created by Tim on 04/10/16.
 */
public class Sand extends AbstractTerrain {

    public static final Color SAND_COLOR = Color.TAN;

    public Sand(){
        super(SAND_COLOR);
    }

    @Override
    public boolean actorCanTraverse(Actor actor) {
        return true;
    }
}
