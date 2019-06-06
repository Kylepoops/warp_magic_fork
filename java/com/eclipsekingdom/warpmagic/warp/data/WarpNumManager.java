package com.eclipsekingdom.warpmagic.warp.data;

import com.eclipsekingdom.warpmagic.data.DataType;
import com.eclipsekingdom.warpmagic.data.Database;
import com.eclipsekingdom.warpmagic.data.Manager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class WarpNumManager extends Manager<UUID,Integer> {

    protected WarpNumManager(DataType dataType, Database database) {
        super(dataType, database);
    }

    public static final WarpNumManager getInstance(){
        return WARP_NUM_MANAGER_INSTANCE;
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

    @Override
    public void load() {
        for(Player player: Bukkit.getOnlinePlayers()){
            cash(player.getUniqueId());
        }
    }

    @Override
    protected boolean stillNeeded(UUID uuid) {
        return false;
    }

    @Override
    protected List<UUID> getRequirements(UUID uuid) {
        return Collections.emptyList();
    }


    private static final WarpNumManager WARP_NUM_MANAGER_INSTANCE = new WarpNumManager(WarpNumDataType.getInstance(), WarpNumDatabase.getInstance());
}
