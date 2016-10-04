package gamestate;

import units.actors.Survivor;
import util.Updatable;
import world.IslandCell;
import world.IslandMap;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Tim on 04/10/16.
 */
public class GameState implements Updatable {

    public IslandMap islandMap;

    private List<IslandCell> selectedCells;
    private List<Survivor> survivors;

    public GameState(IslandMap islandMap){
        this.islandMap = islandMap;
    }

    @Override
    public void update() {
        islandMap.update();
    }

    public void setSelected(IslandCell...cells){
        selectedCells = Arrays.asList(cells);

        for(IslandCell cell: selectedCells){
            System.out.println(cell);
        }
        // TODO:
        // Find distinct cell types

        // Find distinct actor types on those cells

        // Generate action list that can be completed for these items / actors

        // Preserve this list until key input from user
    }
}
