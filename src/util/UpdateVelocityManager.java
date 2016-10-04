package util;

import gamestate.Main;

/**
 * Created by Tim on 18/04/16.
 */
public class UpdateVelocityManager {

    private double updateVelocity; // Actions that can be taken per second 8 / 1
    private double updatesBetweenActions; // How many updates this unit must wait between an action (FPS / updateVelocity)
    private double updatesSinceLastAction = 0;


    public UpdateVelocityManager(double updateVelocity){
        this.updateVelocity = updateVelocity;
        this.updatesBetweenActions = (Main.FPS / updateVelocity) - 1;
        this.updatesSinceLastAction = 0;
    }

    public boolean update() {
        if(updatesSinceLastAction++ >= updatesBetweenActions){
            updatesSinceLastAction = 0;
            return true;
        }

        return false;
    }

    public double getVelocity(){
        return updateVelocity;
    }
}
