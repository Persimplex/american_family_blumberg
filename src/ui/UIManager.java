package ui;

import gamestate.GameState;
import util.Updatable;
import world.IslandMap;

/**
 * Created by Tim on 04/10/16.
 */
public class UIManager implements Updatable {

    private GameCanvas canvas;

    public UIManager(int xSize, int ySize, int displaySize, IslandMap map, GameState gameState){
        canvas = new GameCanvas(xSize, ySize, displaySize, map, gameState);
    }

    @Override
    public void update() {
        canvas.update();
    }
}
