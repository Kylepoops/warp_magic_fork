package com.eclipsekingdom.warpmagic.data.group;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.PluginManager;

import java.util.HashMap;
import java.util.Map;

public class Groups {

    private static Map<String, GroupData> groupToData = new HashMap<>();
    private static GroupFlatFile groupFlatFile = new GroupFlatFile();

    public Groups() {
        load();
    }

    public void load(){
        groupToData.putAll(groupFlatFile.fetch());
        PluginManager pluginManager = Bukkit.getServer().getPluginManager();
        for(String groupName: groupToData.keySet()){
            pluginManager.addPermission(new Permission("warp.group."+groupName, "grants bonus warps and vortexes defined by " + groupName + " user group"));
        }
    }


    public static int getBonusWarps(Player player) {
        for(String groupName: groupToData.keySet()){
            if(player.hasPermission("warp.group."+groupName)){
                return groupToData.get(groupName).getBonusWarps();
            }
        }
        return 0;
    }

    public static int getBonusVortexes(Player player) {
        for(String groupName: groupToData.keySet()){
            if(player.hasPermission("warp.group."+groupName)){
                return groupToData.get(groupName).getBonusVortexes();
            }
        }
        return 0;
    }

}
