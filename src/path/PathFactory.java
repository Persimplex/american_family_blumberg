package path;

import gamestate.GameState;
import units.Location;
import world.IslandCell;
import world.IslandMap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tim on 04/10/16.
 */
public class PathFactory {

    public static Path createPath(IslandMap gameMap, IslandCell start, IslandCell destination){
        List<IslandCell> tempList = new ArrayList<>();

        int curX = start.getX();
        int curY = start.getY();

        while(curX != destination.getX()){
            if(curX < destination.getX()){
                curX += 1;
            } else {
                curX -= 1;
            }

            tempList.add(gameMap.getCell(curX, curY));
        }

        while(curY != destination.getY()){
            if(curY < destination.getY()){
                curY += 1;
            } else {
                curY -= 1;
            }

            tempList.add(gameMap.getCell(curX, curY));
        }

        return new Path(tempList);
    }

    public static Path createPath(GameState gs, Location start, Location end){
        IslandCell startCell = gs.islandMap.getCell(start);
        IslandCell endCell = gs.islandMap.getCell(end);

        return createPath(gs.islandMap, startCell, endCell);
    }
}

