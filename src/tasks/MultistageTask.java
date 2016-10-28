package tasks;

import units.Location;

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
        setUpTask();

        executeNextStage();
    }

    public void executeNextStage(){
        if(!taskQueue.isEmpty()){
            taskQueue.poll().executeTask(survivor);
        }
    }

    public void addSubTask(Subtask t){
        taskQueue.add(t);

        // Set subtask callback
        t.setCallback(this::executeNextStage);
    }

    protected abstract void setUpTask();
}
