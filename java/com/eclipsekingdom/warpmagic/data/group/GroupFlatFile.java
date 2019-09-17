package com.eclipsekingdom.warpmagic.data.group;

import com.eclipsekingdom.warpmagic.util.ConsoleSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class GroupFlatFile {

    private static File file = new File("plugins/WarpMagic", "group.yml");
    private static FileConfiguration config = YamlConfiguration.loadConfiguration(file);

    public GroupFlatFile(){
        initialize();
    }

    private void initialize(){
        if(!file.exists()){
            config.set("example.bonusWarps", 2);
            config.set("example.bonusVortexes", 1);
            save();
        }
    }

    public static Map<String, GroupData> fetch(){
        Map<String, GroupData> groupToData = new HashMap<>();
        for(String groupName: config.getRoot().getKeys(false)){
            try {
                int warpBonus = config.contains(groupName+".bonusWarps")? config.getInt(groupName+".bonusWarps"): 0;
                int vortexBonus = config.contains(groupName+".bonusVortexes")? config.getInt(groupName+".bonusVortexes"): 0;
                groupToData.put(groupName, new GroupData(warpBonus, vortexBonus));
            }catch (Exception e){
                //do nothing
            }
        }
        return groupToData;
    }

    private static void save(){
        try{
            config.save(file);
        } catch (Exception e){
            ConsoleSender.sendMessage("Error saving "+file.getName());
        }
    }

}
