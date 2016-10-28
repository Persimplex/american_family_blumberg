package ui;

import gamestate.GameState;
import gamestate.Main;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import units.Location;
import units.actors.Actor;
import units.actors.Survivor;
import util.ColorUtil;
import util.IUpdatable;
import world.IslandCell;
import world.IslandMap;

/**
 * Created by Tim on 04/10/16.
 */
public class GameCanvas extends Canvas implements IUpdatable {

    public static final int MOVEMENT_SENSITIVITY = 70;
    public static final int MAX_SPEED = 8;

    public static final Color BORDER_COLOR = Color.BLACK;
    public static final Color ITEM_BORDER = Color.DARKGRAY;

    public static final double SELECTION_PERCENTAGE = 0.65;
    public static final Color SELECTION_COLOR = ColorUtil.createTranslucentColor(Color.YELLOW,
                                                                                 SELECTION_PERCENTAGE);
    public static final Color SELECTION_BORDER = Color.ROYALBLUE;
    public static final double HOVER_SELECTION_PERCENTAGE = 0.55;
    public static final Color HOVER_SELECT_COLOR = ColorUtil.createTranslucentColor(Color.AQUAMARINE,
                                                                                    HOVER_SELECTION_PERCENTAGE);

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

    private boolean isSecondSelecting = false;
    private IslandCell mouseCell;
    private MouseEvent lastMouseEvent;


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

            lastMouseEvent = event;
        });

        // Set up item selection
        setOnMouseClicked(event -> {
            IslandCell curCell = getCellUnderMouse(event);

            if(isSecondSelecting){
                System.out.println("THIS ONE WILL BE A SECOND SELECTION");
                gameState.secondSelection(curCell);
            }

            if(event.isControlDown()){
                gameState.addToSelected(curCell);
            } else {
                gameState.setSelected(curCell);
            }
        });

        // Set up
    }


    @Override
    public void update() {
        // Handle second selecting
        if(isSecondSelecting){
//            System.out.println("Stopping the secondSelecting");
            mouseCell = getCellUnderMouse(lastMouseEvent);
//            setSecondSelecting(false);
        }

        // Identify which blocks to draw
        int xStart = Math.max(xOffset / SQUARE_SIZE, 0);
        int yStart = yOffset / SQUARE_SIZE;

        int xEnd = Math.min(xStart + X_SQUARES, gameMap.xSize - 1) + 1;
        int yEnd = Math.min(yStart + Y_SQUARES, gameMap.ySize - 1) + 1;

        // Draw cells on screen
        GraphicsContext gc = getGraphicsContext2D();
        for(int x = xStart; x < xEnd; x++){
            for(int y = yStart; y < yEnd; y++){
                IslandCell curCell = gameMap.getCell(x, y);
                // skip selected cells (for now)
                if(curCell.isSelected()) continue;

                renderCell(gc, curCell);
            }
        }

        // Draw selected cells
        for(int x = xStart; x < xEnd; x++){
            for(int y = yStart; y < yEnd; y++){
                IslandCell curCell = gameMap.getCell(x, y);
                if(curCell.isSelected()) renderCell(gc, curCell);
            }
        }

        // Render survivors
        for(Survivor s: gameState.getSurvivors()){
            Location actorLocation = s.getLocation();

            int actorXS = actorLocation.getX();
            int actorYS = actorLocation.getY();

            if(xStart <= actorXS && actorXS <= xEnd && yStart <= actorYS && actorYS <= yEnd){
                int curXStart = actorXS * SQUARE_SIZE - xOffset;
                int curYStart = actorYS * SQUARE_SIZE - yOffset;

                renderActor(gc, s, curXStart, curYStart);
            }
        }

        // Render selection square
        if(isSecondSelecting && mouseCell != null){
            renderMouseCell(gc);
        }

        // Move offsets
        int xDisplacement = calculateDisplacement(xVelocity, MOVEMENT_SENSITIVITY);
        int yDisplacement = calculateDisplacement(yVelocity, MOVEMENT_SENSITIVITY);

        setOffset(xOffset + xDisplacement, yOffset + yDisplacement);

    }

    public void setSecondSelecting(boolean isSecondSelecting){
        this.isSecondSelecting = isSecondSelecting;
    }


    // Private Methods

    private void renderCell(GraphicsContext gc, IslandCell cell){
        Location starts = calcPixelStarts(cell);

        // Draw terrain or item
        Color finalCellColor = cell.getItemColor();
        if(finalCellColor == null){
            finalCellColor = cell.getTerrainColor();
        }
        fillRect(gc, starts, finalCellColor);

        // Draw selection
        if(cell.isSelected()){
            fillRect(gc, starts, SELECTION_COLOR);
            gc.setStroke(SELECTION_BORDER);
        } else {
            gc.setStroke(BORDER_COLOR);
        }

        gc.strokeRect(starts.getX(), starts.getY(), SQUARE_SIZE, SQUARE_SIZE);
    }

    private void renderMouseCell(GraphicsContext gc){
        Location sts = calcPixelStarts(mouseCell);

        Color mouseCellColor = HOVER_SELECT_COLOR;

        fillRect(gc, sts, mouseCellColor);
        gc.setStroke(SELECTION_COLOR);
        gc.strokeRect(sts.getX(), sts.getY(), SQUARE_SIZE, SQUARE_SIZE);
    }

    private Location calcPixelStarts(IslandCell cell){
        int curXStart = cell.getX() * SQUARE_SIZE - xOffset;
        int curYStart = cell.getY() * SQUARE_SIZE - yOffset;

        return Location.at(curXStart, curYStart);
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

    private IslandCell getCellUnderMouse(MouseEvent event){
        return getCellUnderMouse(event.getSceneX(), event.getSceneY());
    }

    private IslandCell getCellUnderMouse(double x, double y){
        int globalX = xOffset + (int) x;
        int globalY = yOffset + (int) y;

        int xSquareCoord = globalX / SQUARE_SIZE;
        int ySquareCoord = globalY / SQUARE_SIZE;

        return gameMap.getCell(xSquareCoord, ySquareCoord);
    }

    private void fillRect(GraphicsContext gc, Location l, Color c){
        gc.setFill(c);
        gc.fillRect(l.getX(), l.getY(), SQUARE_SIZE, SQUARE_SIZE);
    }


}
