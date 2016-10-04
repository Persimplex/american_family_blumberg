package world;

import units.Location;
import util.IUpdatable;
import world.terrains.AbstractTerrain;
import world.terrains.Forest;
import world.terrains.Sand;
import world.terrains.Water;

/**
 * Created by Tim on 04/10/16.
 */
public class IslandMap implements IUpdatable {

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
                if(y  % 15 == 0) mapArray[x][y].setTerrain(new Water());

                // Add border water
                if(0 <= x && x <= 5 || xSize - 5 <= x && x <= xSize) mapArray[x][y].setTerrain(new Water());
                if(0 <= y && y <= 5 || ySize - 5 <= y && y <= ySize) mapArray[x][y].setTerrain(new Water());
            }
        }
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

    public void setTerrain(int x, int y, AbstractTerrain terrain){
        getCell(x, y).setTerrain(terrain);
    }

}
