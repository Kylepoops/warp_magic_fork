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

    public Location getSpawn(){
        return keyToData.get(GlobalPoint.SPAWN);
    }

    public Location getHub(){
        return keyToData.get(GlobalPoint.HUB);
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
