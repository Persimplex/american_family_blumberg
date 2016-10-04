package world.terrains;

import javafx.scene.paint.Color;
import units.actors.Actor;


/**
 * Created by Tim on 04/10/16.
 */
public class Forest extends AbstractTerrain {

    public static final Color FOREST_COLOR = Color.DARKGREEN;

    public Forest(){
        super(FOREST_COLOR);
    }

    @Override
    public boolean actorCanTraverse(Actor actor) {
        return true;
    }
}
