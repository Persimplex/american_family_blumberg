package ui;

import gamestate.GameState;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import util.IUpdatable;
import world.IslandMap;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Tim on 04/10/16.
 */
public class UIManager implements IUpdatable, Initializable {

    private AnchorPane mainPane;

    private GameCanvas canvas;

    public UIManager(int xSize, int ySize, int displaySize, IslandMap map, GameState gameState){
        try{
            mainPane = (AnchorPane) FXMLLoader.load(getClass().getResource("/ui/layouts/MainLayout.fxml"));

        } catch (IOException e){
            e.printStackTrace();
            System.exit(1);
        }

        canvas = new GameCanvas(xSize, ySize, displaySize, map, gameState);

        mainPane.getChildren().add(canvas);
    }

    @Override
    public void update() {
        canvas.update();
    }

    public Node getNode(){
        return mainPane;
    }

    public void setSecondSelection(boolean selection){
        canvas.setSecondSelecting(selection);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
