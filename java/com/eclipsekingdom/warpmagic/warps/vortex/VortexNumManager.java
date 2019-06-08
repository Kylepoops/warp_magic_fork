package com.eclipsekingdom.warpmagic.warps.vortex;

import com.eclipsekingdom.warpmagic.PluginConfig;
import com.eclipsekingdom.warpmagic.util.data.DataType;
import com.eclipsekingdom.warpmagic.util.data.Manager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class VortexNumManager extends Manager<UUID,Integer> {

    /* --- constructors --- */

    private VortexNumManager() {
        super(new DataType<Integer>(PluginConfig.getInstance().getStartingVortexNum()) {
            @Override
            public void writeTo(String path, Integer data, FileConfiguration config) {
                config.set(path, data);
            }
            @Override
            public Integer readFrom(String path, FileConfiguration config) {
                return config.getInt(path);
            }
        }, "vortexNum", "/data/vortex");
    }


    private static final VortexNumManager VORTEX_NUM_MANAGER = new VortexNumManager();

    public static final VortexNumManager getInstance(){
        return VORTEX_NUM_MANAGER;
    }


    /* --- interface --- */

    @Override
    public void load() {
        for(Player player: Bukkit.getOnlinePlayers()){
            cache(player.getUniqueId());
        }
    }

    public int getUnlockedVortexNum(Player player){
        UUID playerID =  player.getUniqueId();
        if(keyToData.containsKey(playerID)){
            return keyToData.get(playerID);
        }else{
            return defaultData;
        }
    }

    public void incrementVortexCapacity(Player player){
        UUID playerID = player.getUniqueId();

        int currentVortexCap = defaultData;

        if(keyToData.containsKey(playerID)){
            currentVortexCap = keyToData.get(playerID);
        }

        keyToData.put(playerID, currentVortexCap + 1);

        trackUnsavedData(playerID);
    }


    /* --- implementation --- */

    @Override
    protected boolean stillNeeded(UUID uuid, UUID leavingUUID) {
        return false;
    }

    @Override
    protected List<UUID> getRequirements(UUID uuid) {
        return Collections.emptyList();
    }

}