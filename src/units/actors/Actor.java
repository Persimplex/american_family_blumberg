package units.actors;

import javafx.scene.paint.Color;
import units.Location;
import units.items.AbstractItem;
import util.Updatable;
import util.UpdateVelocityManager;

import java.util.List;

/**
 * Created by Tim on 04/10/16.
 */
public abstract class Actor implements Updatable {

    public final int displaySize;

    private int health;
    private UpdateVelocityManager uvm;

    private List<AbstractItem> inventory;
    protected Color color;
    private Location location;

    public Actor(int health, int displaySize, int updateVelocity){
        this.health = health;
        this.displaySize = displaySize;
        this.uvm = new UpdateVelocityManager(updateVelocity, this::actualUpdate);
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

    @Override
    public void update() {
        uvm.update();
    }

    protected abstract void actualUpdate();

    public Color getColor(){
        return this.color;
    }
}
