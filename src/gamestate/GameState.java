package gamestate;

import tasks.TaskEngine;
import tasks.task_types.GoHereTask;
import units.Location;
import units.actors.Actor;
import units.actors.Survivor;
import units.items.AbstractItem;
import units.items.SolarPanel;
import util.IUpdatable;
import world.IslandCell;
import world.IslandMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Tim on 04/10/16.
 */
public class GameState implements IUpdatable, IGameState {

    public IslandMap islandMap;
    public TaskEngine taskEngine;

    private List<IslandCell> selectedCells;
    private List<Survivor> survivors;

    public GameState(IslandMap islandMap){
        this.survivors = new ArrayList<>();
        this.islandMap = islandMap;
        this.selectedCells = new ArrayList<>();

        taskEngine = new TaskEngine();

        // Add some actors
        Survivor newSurvivor = new Survivor(new Location(0, 0), Main.SQUARE_SIZE);
        Survivor newSurvivor2 = new Survivor(new Location(0, 0), Main.SQUARE_SIZE);
        this.addSurvivor(newSurvivor);
        this.addSurvivor(newSurvivor2);

        SolarPanel solarPanel = new SolarPanel();
        addItem(solarPanel, Location.at(5, 5));
    }

    @Override
    public void update() {
        for(Survivor s: survivors){
            if(s.shouldUpdate()){
                // Use this guy to perform an action
            }
        }

        taskEngine.update();

        // Probably not going to update every map cell every frame
//        islandMap.update();
    }

    public void addToSelected(IslandCell...cells){
        for(IslandCell cell: cells){
            if(!selectedCells.contains(cell)){
                selectedCells.add(cell);
                cell.setIsSelected(true);
            }
        }
        System.out.println("Final size: " + selectedCells.size());
    }


    public void setSelected(IslandCell...cells){
        System.out.println("curCell: " + selectedCells.size());
        if(cells.length == 1) {
            IslandCell destination = cells[0];

            System.out.println("New goheretask");

            // Create a GoHereTask
            taskEngine.addTask(new GoHereTask(destination.getLocation()));
        }

        clearSelectedCells();

        selectedCells.addAll(Arrays.asList(cells));
        System.out.println("Final size: " + selectedCells.size());
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

    public List<Survivor> getSurvivors(){
        return survivors;
    }

    public void addSurvivor(Survivor s){
        survivors.add(s);
        islandMap.getCell(s.getLocation().getX(), s.getLocation().getY()).addSurvivor(s);
        taskEngine.addSurvivor(s);
    }

    public void addSurvivorToTaskEngine(Survivor s){
        taskEngine.addSurvivor(s);
    }

    public void addItem(AbstractItem item, Location l){
        islandMap.getCell(l).addItem(item);
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

    public void move(AbstractItem item, Location l){
        IslandCell oldCell = islandMap.getCell(item.getLocation());
        oldCell.getItemList().remove(item);
        islandMap.getCell(l).addItem(item);
    }


    // IGameState methodsn
    @Override
    public int getSurvivorCount() {
        return 0;
    }

    @Override
    public int getResourceAmount(Resource r) {
        return 0;
    }
}
