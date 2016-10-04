package world.terrains;

import javafx.scene.paint.Color;
import units.actors.Actor;

/**
 * Created by Tim on 04/10/16.
 */
public abstract class AbstractTerrain {

    public final Color color;

    public AbstractTerrain(Color color){
        this.color = color;
    }

    public abstract boolean actorCanTraverse(Actor actor);

}
