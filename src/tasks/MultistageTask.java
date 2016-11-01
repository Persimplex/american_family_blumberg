package tasks;

import gamestate.Main;
import units.Location;
import units.actors.Survivor;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Created by Tim on 11/10/16.
 */
public abstract class MultistageTask extends Task {

    private Queue<Subtask> taskQueue;

    public MultistageTask(Location start, Location end) {
        super(start, end);

        taskQueue = new ArrayDeque<>();
    }

    @Override
    protected void taskBody() {
        // Initialize stages
        setUpTask();

        // Execute first stage
        executeNextStage();
    }

    @Override
    public void executeTask(Survivor s) {
        this.survivor = s;

        try {
            call(); // Wrapper for calling taskBody
        } catch (Exception e){
            e.printStackTrace();
        }

        // Do not readd the survivor to the queue
    }

    public void executeNextStage(){
        if(!taskQueue.isEmpty()){
            taskQueue.poll().executeTask(survivor);
        } else {
            System.out.println("Adding the survivor back to pool");
            Main.gameState.addSurvivorToTaskEngine(survivor);
        }
    }

    public void addSubTask(Subtask t){
        taskQueue.add(t);

        // Set subtask callback
        t.setCallback(this::executeNextStage);
    }

    protected abstract void setUpTask();
}
