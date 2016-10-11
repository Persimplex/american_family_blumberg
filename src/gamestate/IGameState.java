package gamestate;

import units.Location;
import units.items.AbstractItem;

import java.util.List;

/**
 * Created by Tim on 10/10/16.
 */
public interface IGameState {

    int getSurvivorCount();

    int getResourceAmount(Resource r);

    void addItem(AbstractItem item, Location l);

    List<AbstractItem> getCellItems(Location l);
}
