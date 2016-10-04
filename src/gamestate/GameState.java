package gamestate;

import path.Path;
import path.PathFactory;
import units.Location;
import units.actors.Actor;
import units.actors.Survivor;
import util.IUpdatable;
import world.IslandCell;
import world.IslandMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Tim on 04/10/16.
 */
public class GameState implements IUpdatable {

    public IslandMap islandMap;

    private List<IslandCell> selectedCells;
    private List<Survivor> survivors;

    public GameState(IslandMap islandMap){
        this.survivors = new ArrayList<>();
        this.islandMap = islandMap;
        this.selectedCells = new ArrayList<>();

        // Add some actors

        Survivor newSurvivor = new Survivor(new Location(0, 0), Main.SQUARE_SIZE);
        this.addSurvivor(newSurvivor);
    }

    @Override
    public void update() {
        for(Survivor s: survivors){
            if(s.shouldUpdate()){
                // Use this guy to perform an action
            }
        }

        // Probably not going to update every map cell every frame
//        islandMap.update();
    }


    public void setSelected(IslandCell...cells){
        System.out.println("curCell: " + selectedCells.size());
        if(selectedCells.size() != 0) {
            // Generate test path
            IslandCell start = selectedCells.get(0);
            IslandCell destination = cells[0];

            for (IslandCell cell : selectedCells) {
                System.out.println(cell.getX() + ", " + cell.getY());
                for (Actor a : cell.getActorList()) {
                    System.out.println("Has an actor");
                    Path p = PathFactory.createPath(islandMap, start, destination);

                    a.setPath(p);
                }
            }
        }

        selectedCells = Arrays.asList(cells);
        System.out.println("Final size: " + selectedCells.size());
        System.out.println("(" + selectedCells.get(0).getX() + ", "  + selectedCells.get(0).getY() + ")");


        // TODO:
        // Find distinct cell types

        // Find distinct actor types on those cells

        // Generate action list that can be completed for these items / actors

        // Preserve this list until key input from user
    }

    public List<Survivor> getSurvivors(){
        return survivors;
    }

    public void addSurvivor(Survivor s){
        survivors.add(s);
        islandMap.getCell(s.getLocation().getX(), s.getLocation().getY()).addActor(s);
    }

    public void removeSurvivor(Survivor s){
        if(survivors.contains(s)){
            survivors.remove(s);
            List<Actor> actorList = islandMap.getCell(s.getLocation().getX(), s.getLocation().getY()).getActorList();
            if(actorList.contains(s)){
                actorList.remove(s);
            }
        }
    }

    public void move(Actor actor, Location l){
        IslandCell oldCell = islandMap.getCell(actor.getLocation());
        oldCell.getActorList().remove(actor);
        islandMap.getCell(l).addActor(actor);
    }
}
