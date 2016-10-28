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
public class DropSubtask extends Subtask {

    AbstractItem item;

    public DropSubtask(Survivor s, AbstractItem item) {
        super(null, s.getLocation(), s);
        this.item = item;
    }

    protected void taskBody() {;
        // Exit if item not in inventory
        if(!survivor.getInventory().contains(item)) return;

        IslandCell destinationCell = Main.gameState.islandMap.getCell(survivor.getLocation());

        // Pick up the item
        survivor.addItemToInventory(item);
        destinationCell.addItem(item);
    }
}
