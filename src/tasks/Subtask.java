package tasks;

import gamestate.Main;
import path.Path;
import path.PathFactory;
import tasks.Task;
import units.Location;
import units.actors.Survivor;

/**
 * Created by Tim on 05/10/16.
 */
public abstract class Subtask extends Task {

    public Subtask(Location startLocation, Location endLocation, Survivor s){
        super(startLocation, endLocation);
        this.survivor = s;
    }


    @Override
    public void executeTask(Survivor s) {
        this.survivor = s;

        try {
            call();
        } catch (Exception e){
            e.printStackTrace();
        }

        // Do not readd the survivor to the queue, handled by Multistage task
    }


}
