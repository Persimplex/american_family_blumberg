package units;

import javafx.scene.paint.Color;
import util.IUpdateReporter;
import util.UpdateVelocityManager;

/**
 * Created by Tim on 10/10/16.
 */
public abstract class Unit implements IUpdateReporter {

    public final int displaySize;

    protected Color color;
    protected Location location;
    protected UpdateVelocityManager uvm;

    public Unit(int displaySize, int updateVeloctiy){
        this.displaySize = displaySize;
        this.uvm = new UpdateVelocityManager(updateVeloctiy);
    }

    @Override
    public boolean shouldUpdate() {
        return uvm.update();
    }

    public Color getColor(){
        return this.color;
    }

    public abstract void moveTo(Location newLocation);

    public Location getLocation(){
        return location;
    }

}
