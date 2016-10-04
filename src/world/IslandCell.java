package world;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Paint;
import units.Location;
import units.actors.Actor;
import units.items.AbstractItem;
import util.Updatable;
import world.terrains.AbstractTerrain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tim on 04/10/16.
 */
public class IslandCell implements Updatable {

    private Location location;
    private AbstractTerrain terrain;
    private List<Actor> actorList;
    private List<AbstractItem> itemList;

    private Group group;
    private Node terrainNode;
    private Node actorNode;

    public IslandCell(Location location, AbstractTerrain terrain){
        this.group = new Group();
        this.location = location;
        this.terrain = terrain;

        this.actorList = new ArrayList<>();
        this.itemList = new ArrayList<>();
    }

    public void addActor(Actor actor){
        if(!actorList.contains(actor)){
            this.actorList.add(actor);
        }
    }

    public void addItem(AbstractItem item){
        if(!itemList.contains(item)){
            this.itemList.add(item);
        }
    }

    public List<Actor> getActorList(){
        return actorList;
    }

    public List<AbstractItem> getItemList(){
        return itemList;
    }

    public void setTerrain(AbstractTerrain newTerrain){
        this.terrain = newTerrain;
    }

    public void replaceItem(AbstractItem oldItem, AbstractItem newItem){
        if(itemList.contains(oldItem)){
            itemList.remove(oldItem);
        }

        if(!itemList.contains(newItem)){
            itemList.add(newItem);
        }
    }

    @Override
    public void update() {
        for(Actor actor: actorList){
            actor.update();
        }
    }

    public Paint getTerrainColor(){
        return terrain.color;
    }

}

