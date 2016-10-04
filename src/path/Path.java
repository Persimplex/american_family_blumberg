package path;

import world.IslandCell;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Data structure and convenience class that will designate the current movement of units on the map.
 *
 * Created by Tim on 06/04/16.
 * Modified for this project on 04/10/16
 */
public class Path {

    private Queue<IslandCell> pathData;
    private IslandCell destination;

    public Path(List<IslandCell> pd){
        pathData = new LinkedList<>();
        addToPath(pd.toArray(new IslandCell[pd.size()]));
    }

    public Path(){
        pathData = new LinkedList<>();
    }

    public void addToPath(IslandCell...islandCells){
        if(islandCells.length == 0) return;

        for (IslandCell islandCell: islandCells){
            pathData.add(islandCell);
        }

        destination = islandCells[islandCells.length - 1];
    }

    public IslandCell getNextTile(){
        if(!pathData.isEmpty()){

            if(pathData.size()-1 == 1){
                destination = null;
            }
            return pathData.poll();
        }

        return null;
    }

    public IslandCell peekNextCell(){
        if(!pathData.isEmpty()){
            return pathData.element();
        }

        return null;
    }

    public IslandCell peekDestination(){
        return destination;
    }

    public boolean isEmpty(){
        return pathData.isEmpty();
    }

    public int size(){
        return pathData.size();
    }
}