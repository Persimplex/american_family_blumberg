package units.actors;

import gamestate.Main;
import javafx.scene.paint.Color;
import path.Path;
import units.Location;
import units.items.AbstractItem;
import util.IUpdateReporter;
import util.UpdateVelocityManager;

import java.util.List;

/**
 * Created by Tim on 04/10/16.
 */
public abstract class Actor implements IUpdateReporter {

    public final int displaySize;

    private Path path;
    private int health;
    private UpdateVelocityManager uvm;

    private List<AbstractItem> inventory;
    protected Color color;
    private Location location;

    public Actor(int health, int displaySize, int updateVelocity){
        this.health = health;
        this.displaySize = displaySize;
        this.uvm = new UpdateVelocityManager(updateVelocity);

        this.path = new Path();
    }

    /**
     * Should be used only for initialization
     * @param location
     */
    public void setLocation(Location location){
        this.location = location;
    }

    public void moveTo(Location newLocation){
        Main.gameState.move(this, newLocation);
        this.location = newLocation;
    }

    public void addItemToInventory(AbstractItem item){
        this.inventory.add(item);
    }

    public List<AbstractItem> getInventory(){
        return this.inventory;
    }

    public Color getColor(){
        return this.color;
    }

    // TODO: Implement "I'm looking for a job" Interface
    @Override
    public boolean shouldUpdate() {
        boolean shouldUpdate = uvm.update();

        if(shouldUpdate && !path.isEmpty()) {
            moveTo(path.getNextTile().getLocation());
        }

        return shouldUpdate;
    }

    public void setPath(Path newPath){
        System.out.println("new path is empty: " + newPath.isEmpty());
        this.path = newPath;
    }

    public Location getLocation(){
        return location;

    }
}
