package units.items;

import gamestate.Main;
import units.Location;
import units.Unit;
import units.actors.Actor;
import units.actors.Survivor;

/**
 * Created by Tim on 10/10/16.
 */
public class AbstractItem extends Unit {

    protected Survivor curOwner;

    public AbstractItem(int displaySize, int updateVeloctiy) {
        super(displaySize, updateVeloctiy);
    }

    /**
     * Only to be called when not in the possession of an Actor
     * @param newLocation
     */
    public void moveTo(Location newLocation){
        Main.gameState.move(this, newLocation);
        this.location = newLocation;
    }

    public void setCurOwner(Survivor survivor){
        curOwner = survivor;
    }

    public boolean isOwned(){
        return curOwner != null;
    }

    public Actor getCurOwner(){
        return curOwner;
    }

}
