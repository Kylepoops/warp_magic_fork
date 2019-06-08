package com.eclipsekingdom.warpmagic.util.data;

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
            World world = Bukkit.getWorld(parts[0]);
            double x = Double.parseDouble(parts[1]);
            double y = Double.parseDouble(parts[2]);
            double z = Double.parseDouble(parts[3]);
            float yaw = Float.parseFloat(parts[4]);
            float pitch = Float.parseFloat(parts[5]);
            return new Location(world,x,y,z,yaw,pitch);
        }catch (Exception e){
            return null;
        }
    }

}
