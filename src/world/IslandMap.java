package world;

import units.Location;
import util.Updatable;
import world.terrains.Forest;
import world.terrains.Sand;

/**
 * Created by Tim on 04/10/16.
 */
public class IslandMap implements Updatable {

    public final int SPACER_SIZE = 1;

    public final int xSize;
    public final int ySize;

    private int displaySize;

    // (x, y) for convenience
    private IslandCell[][] mapArray;

    public IslandMap(int xSize, int ySize, int displaySize){
        this.xSize = xSize;
        this.ySize = ySize;
        this.displaySize = displaySize;

        mapArray = new IslandCell[xSize][ySize];

        for(int x = 0; x < xSize; x++){
            for(int y = 0; y < ySize; y++){
                mapArray[x][y] = new IslandCell(new Location(x, y), new Sand());

                if(x  % 10 == 0) mapArray[x][y].setTerrain(new Forest());
            }
        }

        // Add test survivor
        mapArray[10][5].setTerrain(new Forest());
    }

    @Override
    public void update() {
        for(int x = 0; x < xSize; x++){
            for(int y = 0; y < ySize; y++){
                mapArray[x][y].update();
            }
        }
    }

    public IslandCell getCell(int x, int y){
        return mapArray[x][y];
    }

}
