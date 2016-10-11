package ui;

import gamestate.GameState;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import units.Location;
import units.actors.Actor;
import util.IUpdatable;
import world.IslandCell;
import world.IslandMap;

import java.awt.*;

/**
 * Created by Tim on 04/10/16.
 */
public class GameCanvas extends Canvas implements IUpdatable {

    public static final int MOVEMENT_SENSITIVITY = 70;
    public static final int MAX_SPEED = 8;

    public static final Color BORDER_COLOR = Color.BLACK;
    public static final Color ITEM_BORDER = Color.DARKGRAY;

    public final int WIDTH;
    public final int HEIGHT;

    public final int X_SQUARES;
    public final int Y_SQUARES;

    public final int SQUARE_SIZE;

    private int xOffset;
    private int yOffset;

    private double xVelocity = 0.0;
    private double yVelocity = 0.0;


    private IslandMap gameMap;
    private GameState gameState;


    public GameCanvas(int xSize, int ySize, int displaySize, IslandMap map, GameState gs){
        super(xSize, ySize);
        this.WIDTH = xSize;
        this.HEIGHT = ySize;
        this.SQUARE_SIZE = displaySize;

        this.X_SQUARES = WIDTH / displaySize;
        this.Y_SQUARES = HEIGHT / displaySize;

        this.xOffset = 0;
        this.yOffset = 0;

        this.gameMap = map;
        this.gameState = gs;

        // Set on Click listener
        setUpMouseTracker();
    }

    private void setUpMouseTracker(){
        // Set up canvas scrolling
        setOnMouseMoved(event -> {
            // Handle X Axis motion
            if(0 <= event.getSceneX() && event.getSceneX() <= MOVEMENT_SENSITIVITY){
                // Move to the left
                xVelocity = event.getSceneX() - MOVEMENT_SENSITIVITY;
            } else if (WIDTH - MOVEMENT_SENSITIVITY <= event.getSceneX() && event.getSceneX() <= WIDTH){
                xVelocity = event.getSceneX() - (WIDTH - MOVEMENT_SENSITIVITY);
            } else {
                xVelocity = 0.0;
            }

            // Y Axis
            if(0 <= event.getSceneY() && event.getSceneY() <= MOVEMENT_SENSITIVITY){
                // Move to the left
                yVelocity = event.getSceneY() - MOVEMENT_SENSITIVITY;
            } else if (HEIGHT - MOVEMENT_SENSITIVITY <= event.getSceneY() && event.getSceneY() <= HEIGHT){
                yVelocity = event.getSceneY() - (HEIGHT - MOVEMENT_SENSITIVITY);
            } else {
                yVelocity = 0.0;
            }
        });

        // Set up item selection
        setOnMouseClicked(event -> {
            IslandCell curCell = getCellUnderMouse(event.getSceneX(), event.getSceneY());

            if(event.isControlDown()){
                gameState.addToSelected(curCell);
            } else {
                gameState.setSelected(curCell);
            }
        });
    }


    @Override
    public void update() {
        // Identify which blocks to draw
        int xStart = Math.max(xOffset / SQUARE_SIZE, 0);
        int yStart = yOffset / SQUARE_SIZE;

        int xEnd = Math.min(xStart + X_SQUARES, gameMap.xSize - 1) + 1;
        int yEnd = Math.min(yStart + Y_SQUARES, gameMap.ySize - 1) + 1;

        // Draw them
        GraphicsContext gc = getGraphicsContext2D();
        for(int x = xStart; x < xEnd; x++){
            for(int y = yStart; y < yEnd; y++){
                int curXStart = x * SQUARE_SIZE - xOffset;
                int curYStart = y * SQUARE_SIZE - yOffset;

                IslandCell curCell = gameMap.getCell(x, y);
                renderCell(gc, curCell, curXStart, curYStart);
            }
        }

        for(Actor a: gameState.getSurvivors()){
            Location actorLocation = a.getLocation();

            int actorXS = actorLocation.getX();
            int actorYS = actorLocation.getY();

            if(xStart <= actorXS && actorXS <= xEnd && yStart <= actorYS && actorYS <= yEnd){
                int curXStart = actorXS * SQUARE_SIZE - xOffset;
                int curYStart = actorYS * SQUARE_SIZE - yOffset;

                renderActor(gc, a, curXStart, curYStart);
            }
        }

        // Move offsets
        int xDisplacement = calculateDisplacement(xVelocity, MOVEMENT_SENSITIVITY);
        int yDisplacement = calculateDisplacement(yVelocity, MOVEMENT_SENSITIVITY);

        setOffset(xOffset + xDisplacement, yOffset + yDisplacement);

    }

    private void renderCell(GraphicsContext gc, IslandCell cell, int xStart, int yStart){
        Color finalCellColor = cell.getItemColor();

        if(finalCellColor == null){
            finalCellColor = cell.getTerrainColor();
        }

        if(cell.isSelected()){
            finalCellColor = finalCellColor.interpolate(Color.YELLOW, 0.3);
        }

        gc.setFill(finalCellColor);
        gc.setStroke(BORDER_COLOR);
        gc.fillRect(xStart, yStart, SQUARE_SIZE, SQUARE_SIZE);
        gc.strokeRect(xStart, yStart, SQUARE_SIZE, SQUARE_SIZE);
    }

    private void renderActor(GraphicsContext gc, Actor a, int xStart, int yStart){
        Color actorColor = a.getColor();
        if(actorColor != null){
            gc.setFill(actorColor);
            gc.fillRect(xStart + 4, yStart + 4, SQUARE_SIZE - 8, SQUARE_SIZE - 8);
        }
    }

    private void setOffset(int newXOffset, int newYOffset){
        xOffset = Math.min(newXOffset, (gameMap.xSize * SQUARE_SIZE) - WIDTH);
        xOffset = Math.max(0, xOffset);
        yOffset = Math.min(newYOffset, (gameMap.ySize * SQUARE_SIZE) - HEIGHT);
        yOffset = Math.max(0, yOffset);
    }

    private int calculateDisplacement(double velocity, int barrierSize){
        double ratio = velocity / barrierSize;
        return (int) (ratio * MAX_SPEED);
    }

    private IslandCell getCellUnderMouse(double x, double y){
        int globalX = xOffset + (int) x;
        int globalY = yOffset + (int) y;

        int xSquareCoord = globalX / SQUARE_SIZE;
        int ySquareCoord = globalY / SQUARE_SIZE;

        return gameMap.getCell(xSquareCoord, ySquareCoord);
    }
}
