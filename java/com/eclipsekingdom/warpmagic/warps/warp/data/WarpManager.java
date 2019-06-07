package com.eclipsekingdom.warpmagic.warps.warp.data;

import com.eclipsekingdom.warpmagic.data.DataType;
import com.eclipsekingdom.warpmagic.data.Database;
import com.eclipsekingdom.warpmagic.data.Manager;
import com.eclipsekingdom.warpmagic.util.MapOperations;
import com.eclipsekingdom.warpmagic.warps.warp.Warp;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

public class WarpManager extends Manager<UUID, List<Warp>> {

    private WarpManager(DataType dataType, Database database) {
        super(dataType, database);
    }

    public static final WarpManager getInstance(){
        return WARP_MANAGER;
    }

    @Override
    public void load() {
        for(Player player: Bukkit.getOnlinePlayers()){
            cash(player.getUniqueId());
        }
    }

    public void registerWarp(Player player, Warp warp){
        UUID playerID = player.getUniqueId();
        if(!keyToData.containsKey(playerID)){
            registerNewDataName(playerID, player.getDisplayName());
        }
        MapOperations.addItemToList(keyToData, playerID, warp);
        trackUnsavedData(playerID);
    }

    public void removeWarp(Player player, Warp warp){
        UUID playerID = player.getUniqueId();
        MapOperations.removeItemFromList(keyToData, playerID, warp);
        trackUnsavedData(playerID);
    }

    public List<Warp> getWarps(Player player){
        UUID playerID = player.getUniqueId();
        if(keyToData.containsKey(playerID)){
            return keyToData.get(playerID);
        }else{
            return Collections.emptyList();
        }
    }

    public int getUsedWarpCount(Player player){
        UUID playerID = player.getUniqueId();
        if(keyToData.containsKey(playerID)){
            List<Warp> warps = keyToData.get(playerID);
            if(warps != null){
                return warps.size();
            }else{
                return 0;
            }
        }else{
            return 0;
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

    private static final WarpManager WARP_MANAGER = new WarpManager(WarpDataType.getInstance(), WarpDatabase.getInstance());
}
