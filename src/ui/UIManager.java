package ui;

import gamestate.GameState;
import javafx.scene.Group;
import javafx.scene.Node;
import util.IUpdatable;
import world.IslandMap;

/**
 * Created by Tim on 04/10/16.
 */
public class UIManager implements IUpdatable {

    private Group group;

    private GameCanvas canvas;

    public UIManager(int xSize, int ySize, int displaySize, IslandMap map, GameState gameState){
        group = new Group();

        canvas = new GameCanvas(xSize, ySize, displaySize, map, gameState);

        group.getChildren().add(canvas);
    }

    @Override
    public void update() {
        canvas.update();
    }

    public Group getNode(){
        return group;
    }
}
