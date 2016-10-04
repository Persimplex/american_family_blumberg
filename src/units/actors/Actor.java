package units.actors;

import javafx.scene.paint.Color;
import units.Location;
import units.items.AbstractItem;
import util.Updatable;

import java.util.List;

/**
 * Created by Tim on 04/10/16.
 */
public abstract class Actor implements Updatable {

    public final int displaySize;

    private int health;

    private List<AbstractItem> inventory;
    protected Color color;
    private Location location;

    public Actor(int health, int displaySize){
        this.health = health;
        this.displaySize = displaySize;
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
        
    }

    public Color getColor(){
        return this.color;
    }
}
