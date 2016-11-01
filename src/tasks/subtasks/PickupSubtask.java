package tasks.subtasks;

import gamestate.Main;
import tasks.Subtask;
import units.actors.Survivor;
import units.items.AbstractItem;
import world.IslandCell;

import java.util.List;

/**
 * Created by Tim on 05/10/16.
 */
public class PickupSubtask extends Subtask {

    AbstractItem item;

    public PickupSubtask(Survivor s, AbstractItem item) {
        super(null, item.getLocation(), s);
        this.item = item;
    }

    protected void taskBody() {
        // Exit if there are no items in destination
        IslandCell thisCell = Main.gameState.islandMap.getCell(getEndLocation());
        List<AbstractItem> itemList = thisCell.getItemList();
        if(itemList.size() == 0) return;

        // Exit if item not in destination
        if(!itemList.contains(item)) return;

        // Pick up the item
        survivor.addItemToInventory(item);
        item.setCurOwner(survivor);
        thisCell.removeItem(item);
    }
}
