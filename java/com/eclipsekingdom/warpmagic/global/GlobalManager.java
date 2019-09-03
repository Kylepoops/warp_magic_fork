package com.eclipsekingdom.warpmagic.global;

import com.eclipsekingdom.warpmagic.util.data.DataType;
import com.eclipsekingdom.warpmagic.util.data.Manager;
import com.eclipsekingdom.warpmagic.util.data.StorageString;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Collections;
import java.util.List;

public class GlobalManager extends Manager<GlobalPoint, Location> {

    /* --- constructors --- */

    protected GlobalManager() {
        super(new DataType<Location>(null) {
            @Override
            public void writeTo(String path, Location data, FileConfiguration config) {
                config.set(path, StorageString.from(data));
            }

            @Override
            public Location readFrom(String path, FileConfiguration config) {
                return StorageString.convertToLocation(config.getString(path));
            }
        }, "globalpoints", "");
    }


    private static final GlobalManager GLOBAL_MANAGER = new GlobalManager();

    public static final GlobalManager getInstance(){
        return GLOBAL_MANAGER;
    }


    /* --- interface --- */

    @Override
    public void load() {
        List<String> paths = database.getAllKeyPaths();
        for(String path: paths){
            GlobalPoint globalPoint = GlobalPoint.valueOf(path);
            cache(globalPoint);
        }
    }

    public void setSpawn(Location location){
        keyToData.put(GlobalPoint.SPAWN, location);
        trackUnsavedData(GlobalPoint.SPAWN);
    }

    public void setHub(Location location){
        keyToData.put(GlobalPoint.HUB, location);
        trackUnsavedData(GlobalPoint.HUB);
    }

    public void setNorth(Location location){
        keyToData.put(GlobalPoint.NORTH, location);
        trackUnsavedData(GlobalPoint.NORTH);
    }

    public void setSouth(Location location){
        keyToData.put(GlobalPoint.SOUTH, location);
        trackUnsavedData(GlobalPoint.SOUTH);
    }

    public void setEast(Location location){
        keyToData.put(GlobalPoint.EAST, location);
        trackUnsavedData(GlobalPoint.EAST);
    }

    public void setWest(Location location){
        keyToData.put(GlobalPoint.WEST, location);
        trackUnsavedData(GlobalPoint.WEST);
    }

    public Location getSpawn(){
        Location spawn = keyToData.get(GlobalPoint.SPAWN);
        if(spawn != null){
            return spawn.clone();
        }else{
            return null;
        }
    }

    public Location getHub(){
        Location hub = keyToData.get(GlobalPoint.HUB);
        if(hub != null){
            return hub.clone();
        }else{
            return null;
        }
    }


    public Location getNorth(){
        Location north = keyToData.get(GlobalPoint.NORTH);
        if(north != null){
            return north.clone();
        }else{
            return null;
        }
    }

    public Location getSouth(){
        Location south = keyToData.get(GlobalPoint.SOUTH);
        if(south != null){
            return south.clone();
        }else{
            return null;
        }
    }


    public Location getEast(){
        Location east = keyToData.get(GlobalPoint.EAST);
        if(east != null){
            return east.clone();
        }else{
            return null;
        }
    }

    public Location getWest(){
        Location west = keyToData.get(GlobalPoint.WEST);
        if(west != null){
            return west.clone();
        }else{
            return null;
        }
    }


    /* --- implementation --- */

    @Override
    protected boolean stillNeeded(GlobalPoint globalPoint, GlobalPoint leavingKey) {
        return false;
    }

    @Override
    protected List<GlobalPoint> getRequirements(GlobalPoint globalPoint) {
        return Collections.emptyList();
    }
}
