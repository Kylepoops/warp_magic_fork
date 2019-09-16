package com.eclipsekingdom.warpmagic.data;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.Location;

public class StorageString {

    public static String from(Location location){
        String storageString = location.getWorld().getName();
        storageString += ("_"+location.getX());
        storageString += ("_"+location.getY());
        storageString += ("_"+location.getZ());
        storageString += ("_"+location.getYaw());
        storageString += ("_"+location.getPitch());
        return storageString;
    }

    public static Location convertToLocation(String string){
        try{
            String[] parts = string.split("_");
            int partsLength = parts.length;
            String worldName = parts[0];
            for(int i=1;i<partsLength-5;i++){
                worldName += ("_" + parts[i]);
            }
            World world = Bukkit.getWorld(worldName);
            double x = Double.parseDouble(parts[partsLength-5]);
            double y = Double.parseDouble(parts[partsLength-4]);
            double z = Double.parseDouble(parts[partsLength-3]);
            float yaw = Float.parseFloat(parts[partsLength-2]);
            float pitch = Float.parseFloat(parts[partsLength-1]);
            return new Location(world,x,y,z,yaw,pitch);
        }catch (Exception e){
            return null;
        }
    }

}
