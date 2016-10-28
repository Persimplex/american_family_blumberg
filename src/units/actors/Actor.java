package units.actors;

import gamestate.Main;
import javafx.scene.paint.Color;
import path.Path;
import units.Location;
import units.Unit;
import units.items.AbstractItem;
import util.IUpdateReporter;
import util.UpdateVelocityManager;

import java.util.List;

/**
 * Created by Tim on 04/10/16.
 */
public abstract class Actor extends Unit implements IUpdateReporter {

    protected List<AbstractItem> inventory;


    private Path path;
    private int health;
    private Runnable afterActionCallback = null;


    public Actor(int health, int displaySize, int updateVelocity){
        super(displaySize, updateVelocity);

        this.health = health;
        this.path = new Path();
    }

    /**
     * Should be used only for initialization
     * @param location
     */
    public void setLocation(Location location){
        this.location = location;
    }

    public void addItemToInventory(AbstractItem item){
        inventory.add(item);
    }

    public void removeItemFromInventory(AbstractItem item){
        inventory.remove(item);
    }

    public List<AbstractItem> getInventory(){
        return this.inventory;
    }


    // TODO: Implement "I'm looking for a job" Interface
    @Override
    public boolean shouldUpdate() {
        boolean shouldUpdate = super.shouldUpdate();

        if(shouldUpdate && !path.isEmpty()) {
            moveTo(path.getNextTile().getLocation());
        }

        if(path.isEmpty() && afterActionCallback != null){
            // Execute callback

            // TODO: Create mechanism for executing at most once
            afterActionCallback.run();
        }

        return shouldUpdate;
    }

    public void setPath(Path newPath){
        System.out.println("new path is empty: " + newPath.isEmpty());
        path = newPath;
    }


    public void setAfterCurActionCallback(Runnable callback){
        this.afterActionCallback = callback;
    }


}
