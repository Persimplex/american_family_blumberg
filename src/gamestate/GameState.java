package gamestate;

import util.Updatable;
import world.IslandMap;

/**
 * Created by Tim on 04/10/16.
 */
public class GameState implements Updatable {

    public IslandMap islandMap;

    public GameState(IslandMap islandMap){
        this.islandMap = islandMap;
    }

    @Override
    public void update() {
        islandMap.update();
    }
}
