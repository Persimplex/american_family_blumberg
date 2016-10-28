package tasks;

import gamestate.Main;
import units.Location;
import units.actors.Survivor;

/**
 * Each task is to be done in another thread
 *
 * Created by Tim on 05/10/16.
 */
public abstract class Task extends javafx.concurrent.Task {

    private Location startLocation;
    private Location endLocation;
    private String status;

    protected Survivor survivor;

    protected int startingDelay;
    protected int duration;
    protected int endingDelay;

    public Task(Location start, Location end){
        this.startLocation = start;
        this.endLocation = end;
    }

    protected Location getStartLocation(){
        return this.startLocation;
    }

    protected Location getEndLocation(){
        return this.endLocation;
    }

    public String getStatus(){
        return this.status;
    }

    protected void setStatus(String newStatus){
        this.status = newStatus;
    }

    @Override
    protected Object call() throws Exception {
        // Opening delay

        // Execute (delay should be inside)
        taskBody();

        // Closing delay

        return null;
    }

    public void executeTask(Survivor s){
        this.survivor = s;

        try {
            call();
        } catch (Exception e){
            e.printStackTrace();
        }

        Main.gameState.addSurvivorToTaskEngine(s);
    }


    protected abstract void taskBody();
}
