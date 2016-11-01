package world;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import units.Location;
import units.actors.Survivor;
import units.items.AbstractItem;
import util.IUpdatable;
import world.terrains.AbstractTerrain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tim on 04/10/16.
 */
public class IslandCell implements IUpdatable {

    private Location location;
    private AbstractTerrain terrain;
    private List<Survivor> survivorList;
    private List<AbstractItem> itemList;

    private Group group;
    private Node terrainNode;
    private Node actorNode;
    private boolean isSelected = false;

    public IslandCell(Location location, AbstractTerrain terrain){
        this.group = new Group();
        this.location = location;
        this.terrain = terrain;

        this.survivorList = new ArrayList<>();
        this.itemList = new ArrayList<>();
    }

    public void addSurvivor(Survivor survivor){
        if(!survivorList.contains(survivor)){
            survivorList.add(survivor);
        }
    }

    public void removeSurvivor(Survivor survivor){
        if(survivorList.contains(survivor)){
            survivorList.remove(survivor);
        }
    }

    public void addItem(AbstractItem item){
        if(!itemList.contains(item)){
            itemList.add(item);
            item.moveBetweenCells(this.location);
        }
    }

    public void removeItem(AbstractItem item){
        if(itemList.contains(item)){
            itemList.remove(item);
        }
    }

    public List<Survivor> getSurvivorList(){
        return survivorList;
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
        for(Survivor survivor: survivorList){
            survivor.shouldUpdate();
        }
    }

    public Color getTerrainColor(){
        return terrain.color;
    }

    public int getX(){
        return location.getX();
    }

    public int getY(){
        return location.getY();
    }

    public Location getLocation(){
        return location;
    }

    public Color getItemColor(){
        if(itemList.size() != 0){
            return itemList.get(0).getColor();
        }

        return null;
    }

    public boolean isSelected(){
        return this.isSelected;
    }

    public void setIsSelected(boolean newSelectionState){
        this.isSelected = newSelectionState;
    }
}

