package tasks.task_types;

import tasks.MultistageTask;
import tasks.subtasks.DropSubtask;
import tasks.subtasks.GoHereSubTask;
import tasks.subtasks.PickupSubtask;
import units.Location;
import units.items.AbstractItem;

/**
 * Created by Tim on 05/10/16.
 */
public class GoPickupCarryTask extends MultistageTask {

    private AbstractItem item;

    public GoPickupCarryTask(Location itemLocation, Location endLocation, AbstractItem item){
        super(itemLocation, endLocation);
        this.item = item;
    }

    @Override
    protected void setUpTask() {
        // Path to item
        GoHereSubTask goToItem = new GoHereSubTask(getStartLocation(), survivor);
        addSubTask(goToItem);

        // Pickup Item
        PickupSubtask pickupSubtask = new PickupSubtask(survivor, item);
        addSubTask(pickupSubtask);

        // path to end
        GoHereSubTask goToEnd = new GoHereSubTask(getEndLocation(), survivor);
        addSubTask(goToEnd);

        // TODO: dropoff
        DropSubtask dropOff = new DropSubtask(survivor, item);
        addSubTask(dropOff);
    }
}
