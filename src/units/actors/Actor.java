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

    private Path path;
    private int health;

    protected List<AbstractItem> inventory;

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
        this.inventory.add(item);
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

        return shouldUpdate;
    }

    public void setPath(Path newPath){
        System.out.println("new path is empty: " + newPath.isEmpty());
        this.path = newPath;
    }

}
