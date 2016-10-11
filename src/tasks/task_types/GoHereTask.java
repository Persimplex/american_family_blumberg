package tasks.task_types;

import gamestate.Main;
import path.Path;
import path.PathFactory;
import tasks.Task;
import units.Location;

/**
 * Created by Tim on 05/10/16.
 */
public class GoHereTask extends Task {

    public GoHereTask(Location endLocation){
        super(null, endLocation);
    }

    @Override
    protected void taskBody() {
        Path pathToLocation = PathFactory.createPath(Main.gameState, survivor.getLocation(), getEndLocation());
        survivor.setPath(pathToLocation);
    }
}
