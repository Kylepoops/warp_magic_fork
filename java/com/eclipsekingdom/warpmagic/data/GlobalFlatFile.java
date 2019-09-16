package com.eclipsekingdom.warpmagic.data;

import com.eclipsekingdom.warpmagic.warp.global.GlobalPoint;
import com.eclipsekingdom.warpmagic.util.ConsoleSender;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class GlobalFlatFile {

    private static String header = "Global Points";
    File file = new File("plugins/WarpMagic", "global.yml");
    FileConfiguration config = YamlConfiguration.loadConfiguration(file);

    public Map<GlobalPoint, Location> fetch(){
        Map<GlobalPoint, Location> pointToLocation = new HashMap<>();
        if(config.contains(header)){
            for(String pointString: config.getConfigurationSection(header).getKeys(false)){
                Location location = StorageString.convertToLocation(config.getString(header + "." + pointString));
                if(location != null){
                    GlobalPoint globalPoint = GlobalPoint.valueOf(pointString);
                    pointToLocation.put(globalPoint, location);
                }
            }
        }
        return pointToLocation;
    }

    public void store(Map<GlobalPoint, Location> pointToLocation){
        config.set(header, null);
        for(Map.Entry<GlobalPoint,Location> entry: pointToLocation.entrySet()){
            config.set(header +"."+ entry.getKey().toString(), StorageString.from(entry.getValue()));
        }
        save();
    }

    private void save(){
        try{
            config.save(file);
        } catch (Exception e){
            ConsoleSender.sendMessage("Error saving "+file.getName());
        }
    }

}