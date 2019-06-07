package com.eclipsekingdom.warpmagic.warps.warp;

import com.eclipsekingdom.warpmagic.PluginConfig;
import com.eclipsekingdom.warpmagic.data.DataType;
import com.eclipsekingdom.warpmagic.data.Manager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class WarpNumManager extends Manager<UUID,Integer> {

    /* --- constructors --- */

    private WarpNumManager() {
        super(new DataType<Integer>(PluginConfig.getInstance().getStartingWarpNum()) {
            @Override
            public void writeTo(String path, Integer data, FileConfiguration config) {
                config.set(path, data);
            }
            @Override
            public Integer readFrom(String path, FileConfiguration config) {
                return config.getInt(path);
            }
        }, "warpNum", "/data/warp");
    }


    private static final WarpNumManager WARP_NUM_MANAGER_INSTANCE = new WarpNumManager();

    public static final WarpNumManager getInstance(){
        return WARP_NUM_MANAGER_INSTANCE;
    }


    /* --- interface --- */

    @Override
    public void load() {
        for(Player player: Bukkit.getOnlinePlayers()){
            cash(player.getUniqueId());
        }
    }

    public int getUnlockedWarpNum(Player player){
        UUID playerID =  player.getUniqueId();
        if(keyToData.containsKey(playerID)){
            return keyToData.get(playerID);
        }else{
            return defaultData;
        }
    }

    public void incrementWarpCapacity(Player player){
        UUID playerID = player.getUniqueId();

        int currentWarpCap = defaultData;

        if(keyToData.containsKey(playerID)){
            currentWarpCap = keyToData.get(playerID);
        }else{
            registerNewDataName(playerID, player.getDisplayName());
        }

        keyToData.put(playerID, currentWarpCap + 1);

        trackUnsavedData(playerID);
    }


    /* --- implementation --- */

    @Override
    protected boolean stillNeeded(UUID uuid) {
        return false;
    }

    @Override
    protected List<UUID> getRequirements(UUID uuid) {
        return Collections.emptyList();
    }

}