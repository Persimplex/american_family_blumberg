package tasks;

import units.actors.Survivor;
import util.IUpdatable;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Meant to be the Manager of created tasks, and divvying them up to the survivors that are available
 *
 * Created by Tim on 05/10/16.
 */
public class TaskEngine implements IUpdatable{

    Queue<Task> taskQueue;
    Queue<Survivor> survivorQueue;

    public TaskEngine(){
        taskQueue = new ArrayDeque<>();
        survivorQueue = new ArrayDeque<>();
    }

    public void addTask(Task t){
        taskQueue.add(t);
    }

    public void addSurvivor(Survivor s){
        survivorQueue.add(s);
    }

    @Override
    public void update() {
        if(!taskQueue.isEmpty() && !survivorQueue.isEmpty()){
            Task t = taskQueue.poll();
            Survivor s = survivorQueue.poll();

            System.out.println("Excuting task: " + t.getClass() + " with Survivor: " + s);

            t.executeTask(s);
        }
    }

    /*
        DEBUG METHODS
     */

    public boolean contains(Survivor s){
        return survivorQueue.contains(s);
    }
}
