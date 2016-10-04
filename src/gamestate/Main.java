package gamestate;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import ui.GameCanvas;
import world.IslandMap;

public class Main extends Application {

    public static final int WIDTH = 600;
    public static final int HEIGHT = 480;

    public static final int FPS = 60;
    public static final int FRAME_DUR = 1000/FPS; // in millis

    public static final int SQUARE_SIZE = 20;
    public static final int X_SQUARES = 50;
    public static final int Y_SQUARES = 50;

    private Group gameGroup;
    private Stage gameStage;
    private Scene gameScene;


    private Timeline gameLoop;
    private IslandMap map;
    private GameState gameState;
    private GameCanvas canvas;

    private long lastTime = 0;
    private int updates = 0;


    @Override
    public void start(Stage primaryStage) throws Exception{
        initializeWindow(primaryStage);

        initializeState();

        // Initialize game canvas
        canvas = new GameCanvas(WIDTH, HEIGHT, SQUARE_SIZE, map);
        gameGroup.getChildren().add(canvas);

        // Canvas mouse monitors
        canvas.setUpMouseTracker();

        // Init and start gameLoop
        KeyFrame frame = new KeyFrame(Duration.millis(FRAME_DUR), new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                // update
                update();

                // Framerate reporting
                updates += 1;
                if(updates >= FPS){
                    long curTime = System.currentTimeMillis();
                    System.out.println(1000 / (int) ((curTime - lastTime) / (float) FPS) + "fps");
                    lastTime = curTime;
                    updates = 0;
                }
            }
        });
        gameLoop = new Timeline(FPS, frame);
        gameLoop.setCycleCount(Animation.INDEFINITE);


        gameLoop.play();
        primaryStage.show();
    }

    private void initializeState(){
        // Initialize Map
        map = new IslandMap(X_SQUARES, Y_SQUARES, SQUARE_SIZE);
        gameState = new GameState(map);
    }

    private void initializeWindow(Stage primaryStage){
        //        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("American Family Blumberg");

        gameGroup = new Group();
        gameScene = new Scene(gameGroup, WIDTH, HEIGHT);

        primaryStage.setScene(gameScene);
    }

    private void update(){
        // Update Game World
        gameState.update();

        // Render game world
        canvas.update();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
