package com.eclipsekingdom.warpmagic.data;

import com.eclipsekingdom.warpmagic.warp.global.GlobalPoint;
import org.bukkit.Location;

import java.util.HashMap;
import java.util.Map;

public class GlobalCache {

    private static GlobalFlatFile globalFlatFile = new GlobalFlatFile();
    private static Map<GlobalPoint, Location> pointToLocation = new HashMap<>();

    public GlobalCache(){
        load();
    }

    private void load(){
        pointToLocation.putAll(globalFlatFile.fetch());
    }

    public static void save(){
        globalFlatFile.store(pointToLocation);
    }

    public static void set(GlobalPoint globalPoint, Location location){
        pointToLocation.put(globalPoint, location);
    }

    public static Location get(GlobalPoint globalPoint){
        Location location = pointToLocation.get(globalPoint);
        if(location != null){
            return location.clone();
        }else{
            return null;
        }
    }

    public static void delete(GlobalPoint globalPoint){
        pointToLocation.remove(globalPoint);
    }

}
