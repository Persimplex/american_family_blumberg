package units.actors;

import javafx.scene.paint.Color;
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

    private int health;
    private UpdateVelocityManager uvm;

    private List<AbstractItem> inventory;
    protected Color color;
    private Location location;

    public Actor(int health, int displaySize, int updateVelocity){
        this.health = health;
        this.displaySize = displaySize;
        this.uvm = new UpdateVelocityManager(updateVelocity);
    }

    public void moveTo(Location newLocation){
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
        return uvm.update();
    }
}
