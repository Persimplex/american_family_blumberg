package util;

import gamestate.Main;

/**
 * Created by Tim on 18/04/16.
 */
public class UpdateVelocityManager implements Updatable {

    private double updateVelocity; // Actions that can be taken per second 8 / 1
    private double updatesBetweenActions; // How many updates this unit must wait between an action (FPS / updateVelocity)
    private double updatesSinceLastAction = 0;

    private Runnable actionOnUpdate;

    public UpdateVelocityManager(double updateVelocity, Runnable action){
        this.updateVelocity = updateVelocity;
        this.updatesBetweenActions = (Main.FPS / updateVelocity) - 1;
        this.updatesSinceLastAction = 0;

        this.actionOnUpdate = action;
    }


    @Override
    public void update() {
        if(updatesSinceLastAction++ >= updatesBetweenActions){
            // Actually update
            actionOnUpdate.run();

            updatesSinceLastAction = 0;
        }
    }

    public double getVelocity(){
        return updateVelocity;
    }
}
