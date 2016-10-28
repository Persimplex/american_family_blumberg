package tasks.subtasks;

import gamestate.Main;
import path.Path;
import path.PathFactory;
import tasks.Subtask;
import units.Location;
import units.actors.Survivor;

/**
 * Created by Tim on 11/10/16.
 */
public class GoHereSubTask extends Subtask {

    public GoHereSubTask(Location endLocation, Survivor s) {
        super(null, endLocation, s);
    }

    @Override
    protected void taskBody() {
        Path pathToLocation = PathFactory.createPath(Main.gameState, survivor.getLocation(), getEndLocation());
        survivor.setPath(pathToLocation);
    }
}
