package tasks.subtasks;

import gamestate.Main;
import tasks.Subtask;
import units.Location;
import units.actors.Survivor;
import units.items.AbstractItem;
import world.IslandCell;

/**
 * Created by Tim on 05/10/16.
 */
public class DropSubtask extends Subtask {

    AbstractItem item;

    public DropSubtask(Survivor s, Location dropDestination, AbstractItem item) {
        super(null, dropDestination, s);
        this.item = item;
    }

    protected void taskBody() {;

        // Exit if item not in inventory
        if(!survivor.getInventory().contains(item)){
            System.out.println("Not in inventory..");
            return;
        }

        IslandCell destinationCell = Main.gameState.islandMap.getCell(getEndLocation());

        // Pick up the item
        survivor.removeItemFromInventory(item);
        item.setCurOwner(null);
        item.moveBetweenCells(destinationCell.getLocation());
    }
}
