package gamestate;

import gamestate.input.Keyboard;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import tasks.TaskEngine;
import tasks.task_types.GoHereTask;
import tasks.task_types.GoPickupCarryTask;
import units.Location;
import units.actors.Survivor;
import units.items.AbstractItem;
import units.items.SolarPanel;
import util.IUpdatable;
import world.IslandCell;
import world.IslandMap;

import java.util.ArrayList;
import java.util.List;

import static javafx.scene.input.KeyCode.M;
import static javafx.scene.input.KeyCode.R;

/**
 * Created by Tim on 04/10/16.
 */
public class GameState implements IUpdatable, IGameState {

    public static final KeyCode[] KEYS = {KeyCode.M, KeyCode.R, KeyCode.LEFT, KeyCode.RIGHT};

    public IslandMap islandMap;
    public TaskEngine taskEngine;
    private Keyboard keyboard;

    private List<IslandCell> selectedCells;
    private List<Survivor> survivors;

    private IslandCell secondSelection;
    private Task<IslandCell> uponSecondSelection;

    // Item pickup
    private IslandCell pickupFirstLocation = null;

    public GameState(IslandMap islandMap, Scene gameScene){
        this.survivors = new ArrayList<>();
        this.islandMap = islandMap;
        this.selectedCells = new ArrayList<>();
        this.taskEngine = new TaskEngine();
        this.keyboard = new Keyboard(gameScene, KEYS);
    }

    @Override
    public void update() {
        for(Survivor s: survivors){
            if(s.shouldUpdate()){
                // Use this guy to perform an action
            }
        }

        taskEngine.update();

        // Handle keyboard input
        handleInput();

        // Probably not going to update every map cell every frame
//        islandMap.update();
    }

    public void createTestData(){
        // Add some actors
        Survivor newSurvivor = new Survivor(new Location(0, 0), Main.SQUARE_SIZE);
        Survivor newSurvivor2 = new Survivor(new Location(1, 1), Main.SQUARE_SIZE);
        this.addSurvivor(newSurvivor);
        this.addSurvivor(newSurvivor2);

        SolarPanel solarPanel = new SolarPanel();
        addItem(solarPanel, Location.at(5, 5));
    }

    public void addToSelected(IslandCell...cells){
        // Handle remove cell case
        if(cells.length == 1 && selectedCells.size() != 0 && selectedCells.contains(cells[0])){
            removeFromSelected(cells[0]);
            return;
        }

        // Handle second stage

        for(IslandCell cell: cells){
            if(!selectedCells.contains(cell)){
                selectedCells.add(cell);
                cell.setIsSelected(true);
            }
        }
    }

    public void removeFromSelected(IslandCell cell){
        selectedCells.remove(cell);
        cell.setIsSelected(false);
    }


    public void setSelected(IslandCell...cells){
        System.out.println("curCell: " + selectedCells.size());

        clearSelectedCells();

        addToSelected(cells);
        if(cells.length != 0){
            System.out.println("Selection inventory size: " + cells[0].getItemList().size());

            if(cells[0].getItemList().size() != 0){
                AbstractItem i =  cells[0].getItemList().get(0);
                System.out.println("(x, y): (" + i.getLocation().getX() + ", " + i.getLocation().getY() + ")");
            }
        }
        System.out.println("(" + selectedCells.get(0).getX() + ", "  + selectedCells.get(0).getY() + ")");


        // TODO:
        // Find distinct cell types

        // Find distinct actor types on those cells

        // Generate action list that can be completed for these items / actors

        // Preserve this list until key input from user
    }

    private void clearSelectedCells(){
        for(IslandCell cell: selectedCells){
            cell.setIsSelected(false);
        }

        selectedCells.clear();
    }

    public void secondSelection(IslandCell cell){
        if(pickupFirstLocation == null){
            // TODO: We have an error, do something here?
        }

        handlePickup(cell, false);
    }

    public List<Survivor> getSurvivors(){
        return survivors;
    }

    public void addSurvivor(Survivor s){
        survivors.add(s);
        islandMap.getCell(s.getLocation().getX(), s.getLocation().getY()).addSurvivor(s);
        taskEngine.addSurvivor(s);
    }

    public void addSurvivorToTaskEngine(Survivor s){
        s.setAfterCurActionCallback(null);
        taskEngine.addSurvivor(s);
    }

    public void addItem(AbstractItem item, Location l){
//        islandMap.getCell(l).addItem(item);
        item.moveBetweenCells(l);
    }

    public List<AbstractItem> getCellItems(Location l){
        return islandMap.getCell(l).getItemList();
    }

    public void removeSurvivor(Survivor s){
        if(survivors.contains(s)){
            survivors.remove(s);
            List<Survivor> actorList = islandMap.getCell(s.getLocation().getX(), s.getLocation().getY()).getSurvivorList();
            if(actorList.contains(s)){
                actorList.remove(s);
            }
        }
    }

    public void move(Survivor survivor, Location l){
        IslandCell oldCell = islandMap.getCell(survivor.getLocation());
        oldCell.getSurvivorList().remove(survivor);
        islandMap.getCell(l).addSurvivor(survivor);
    }

    /**
     * Manages IslandCell inventory management
     * @param item
     * @param l
     */
    public void moveBetweenCellInventories(AbstractItem item, Location l){
        IslandCell oldCell = islandMap.getCell(item.getLocation());
        oldCell.getItemList().remove(item);
        islandMap.getCell(l).addItem(item);
    }

    public void handleInput(){
        if(keyboard.getAndConsumeKey(M) && selectedCells.size() == 1){
            IslandCell destination = selectedCells.get(0);

            System.out.println("New go here task");

            // Create a GoHereTask
            taskEngine.addTask(new GoHereTask(destination.getLocation()));
        } else if(keyboard.getAndConsumeKey(R) && selectedCells.size() == 1){
            // Get item location
            System.out.println("P");
            if(selectedCells.get(0).getItemList().size() <= 1){
                IslandCell itemLocation = selectedCells.get(0);
                handlePickup(itemLocation, true);
            }

            // Figure out if selecting first position or second position
//            GoPickupCarryTask pickupCarryTask = new GoPickupCarryTask();
        }
    }

    public void handlePickup(IslandCell givenCell, boolean isFirst){
        if(isFirst){
            // Turn on second selecting
            Main.uiManager.setSecondSelection(true);
            pickupFirstLocation = givenCell;
        } else {
            // Check that this call wasn't made erroneously
            if(pickupFirstLocation == null) return;

            Main.uiManager.setSecondSelection(false);

            // Actually create the Pickup task
            System.out.println("New Pickup task");

            // Check that this cell actually has items, pick one
            if(pickupFirstLocation.getItemList().size() == 0){
                return;
            }

            AbstractItem itemCarry = pickupFirstLocation.getItemList().get(0);

            // Create a GoPickupCarryTask
            taskEngine.addTask(new GoPickupCarryTask(pickupFirstLocation.getLocation(),
                                                     givenCell.getLocation(),
                                                     itemCarry));

            pickupFirstLocation = null;
        }
    }



    // IGameState methods
    @Override
    public int getSurvivorCount() {
        return survivors.size();
    }

    @Override
    public int getResourceAmount(Resource r) {
        return 0;
    }

}
