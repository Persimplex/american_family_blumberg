package ui;

import gamestate.GameState;
import gamestate.Main;
import javafx.concurrent.Task;
import javafx.scene.Group;
import util.IUpdatable;
import world.IslandCell;
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

    public void setSecondSelection(boolean selection){
        canvas.setSecondSelecting(selection);
    }


//    public void getSecondSelection(IslandCell firstCell, Runnable uponCompletion){
//        // Create background thread to retrieve secondSelection
//        Task<IslandCell> secondSelectionTask = new Task<IslandCell>() {
//            @Override
//            protected IslandCell call() throws Exception {
//                canvas.setSecondSelecting(true);
//
//                // Wait until secondSelect is true
//                while(Main.gameState.getSecondSelection() == )
//                return null;
//            }
//
//            private IslandCell doAfterSecondSelected(){
//
//            }
//        };
//    }
}
